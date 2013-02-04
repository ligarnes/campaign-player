package net.alteiar.campaign.player.gui.tools.transferable;

import java.awt.Color;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;

import net.alteiar.campaign.player.gui.tools.adapter.BasicAdapter;

public class ListSource extends JPanel {
	private static final long serialVersionUID = 1L;

	public static String EVENT_ADD = "ADD";
	public static String EVENT_REMOVE = "REMOVE";

	public static Color UNSELECTED = new Color(115, 53, 28);
	public static Color SELECTED = new Color(65, 33, 18);
	public static Color DISABLE = new Color(84, 65, 56);

	public static Color TEXT_COLOR = new Color(219, 213, 137);

	private ObjectLabel<BasicAdapter<?>> selected;

	private final MouseAdapter adapter;

	public ListSource() {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		selected = null;

		this.setBackground(UNSELECTED);

		this.setTransferHandler(new GeneralObjectTranfertHandler<BasicAdapter<?>>());
		new DropTarget(this, new DropListener(this));

		adapter = new ListSourceAdapter();

		this.addMouseListener(adapter);
		this.addMouseMotionListener(adapter);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.removeMouseListener(adapter);
		this.removeMouseMotionListener(adapter);

		this.setBackground(DISABLE);

		if (enabled) {
			this.addMouseListener(adapter);
			this.addMouseMotionListener(adapter);
			this.setBackground(UNSELECTED);
		}
	}

	public ObjectLabel<BasicAdapter<?>> getSelectedItem() {
		return selected;
	}

	public void removeSelected() {
		this.remove(selected);
		selected = null;
		this.revalidate();
		this.repaint();
		this.notifyAction(new ActionEvent(this, 0, EVENT_REMOVE));
	}

	public void addElement(BasicAdapter<?> obj) {
		this.add(new ObjectLabel<BasicAdapter<?>>(obj));
		this.revalidate();
		this.notifyAction(new ActionEvent(this, 0, EVENT_ADD));
	}

	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	public ActionListener[] getActionListeners() {
		return listenerList.getListeners(ActionListener.class);
	}

	private void notifyAction(ActionEvent event) {
		for (ActionListener listener : getActionListeners()) {
			listener.actionPerformed(event);
		}
	}

	private class ListSourceAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (selected != null) {
				JComponent comp = (JComponent) selected.getParent();
				TransferHandler handler = comp.getTransferHandler();
				handler.exportAsDrag(comp, e, TransferHandler.COPY);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			JComponent c = (JComponent) getComponentAt(e.getPoint());

			if (c != null && !(c instanceof ListSource)) {
				if (!c.equals(selected)) {
					if (selected != null) {
						selected.setOpaque(false);
						// need to change background so opaque work
						selected.setBackground(UNSELECTED);
					}
					selected = (ObjectLabel<BasicAdapter<?>>) c;
					selected.setOpaque(true);
					selected.setBackground(SELECTED);
				}
			}
			revalidate();

		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (selected != null) {
				selected.setOpaque(false);
				// need to change background so opaque work
				selected.setBackground(UNSELECTED);
			}
			selected = null;
		}
	}
}
