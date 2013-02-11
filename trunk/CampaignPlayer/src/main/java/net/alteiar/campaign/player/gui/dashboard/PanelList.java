package net.alteiar.campaign.player.gui.dashboard;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class PanelList extends JPanel {
	private static final long serialVersionUID = 1L;

	private final HashMap<Object, JPanel> panels;
	private final JPanel panelCreate;

	public PanelList(String title) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(layout);

		this.setBorder(BorderFactory.createTitledBorder(title));

		panels = new HashMap<Object, JPanel>();

		panelCreate = createPanelCreate();
		this.add(panelCreate);

		this.setOpaque(false);
	}

	protected abstract JPanel createPanelCreate();

	protected void addElement(Object obj, JPanel panel) {
		panels.put(obj, panel);
		this.remove(panelCreate);
		if (this.getComponentCount() > 0) {
			this.remove(this.getComponentCount() - 1);
		}

		Dimension dim = new Dimension(10, 2);
		this.add(new Box.Filler(dim, dim, dim));
		this.add(panel);
		this.add(new Box.Filler(dim, dim, dim));

		this.add(panelCreate);
		this.revalidate();
		this.repaint();
	}

	protected void removeElement(Object obj) {
		JPanel panel = panels.remove(obj);

		int i = this.getComponentIndex(panel);
		if (i > -1) {
			this.remove(i + 1);
			this.remove(panel);

			this.revalidate();
			this.repaint();
		}
	}

	public final int getComponentIndex(Component component) {
		if (component != null && component.getParent() != null) {
			Container c = component.getParent();
			for (int i = 0; i < c.getComponentCount(); i++) {
				if (c.getComponent(i) == component)
					return i;
			}
		}

		return -1;
	}
}
