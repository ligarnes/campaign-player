package net.alteiar.campaign.player.gui.documents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.factory.PluginSystem;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.factory.MapElementFactory;
import net.alteiar.map.Map;
import net.alteiar.map.elements.MapElement;

public class PanelCreateDocument extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private static PanelCreateDocument documentBuilder = new PanelCreateDocument();

	public static void createMapElement(Map map, MapEvent event) {
		DialogOkCancel<PanelCreateDocument> dlg = new DialogOkCancel<PanelCreateDocument>(
				null, "Créer un document", true, documentBuilder);

		dlg.getMainPanel().refreshElements();
		dlg.setOkText("Créer");
		dlg.setCancelText("Annuler");
		dlg.setLocation(event.getMouseEvent().getLocationOnScreen());
		dlg.pack();
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			MapElement element = dlg.getMainPanel().buildElement(
					event.getMapPosition());
			MapElementFactory.buildMapElement(element, map);
		}
	}

	public static ArrayList<PanelDocumentBuilder> getBuilders() {
		return PluginSystem.getInstance().getGuiDocumentFactory();
	}

	private final JPanel panelWest;
	private final JPanel panelCenter;
	private PanelDocumentBuilder builder;

	public PanelCreateDocument() {
		super(new BorderLayout());

		panelWest = new JPanel(new GridLayout(getBuilders().size(), 1));

		this.add(panelWest, BorderLayout.WEST);

		builder = getBuilders().get(0);
		panelCenter = new JPanel();
		panelCenter.add(builder);
		this.add(panelCenter, BorderLayout.CENTER);
	}

	private void refreshElements() {
		panelWest.removeAll();
		int maxWidth = 50;
		int maxHeight = 50;
		for (final PanelDocumentBuilder panel : getBuilders()) {
			Dimension dim = panel.getPreferredSize();

			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxHeight, dim.height);

			JButton select = new JButton(panel.getElementName());
			select.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectBuilder(panel);
				}
			});
			// select.setEnabled(panel.isAvailable());
			panelWest.add(select);
		}

		this.setPreferredSize(new Dimension(maxWidth, maxHeight));
	}

	private void selectBuilder(PanelDocumentBuilder builder) {
		panelCenter.removeAll();
		this.builder = builder;
		panelCenter.add(this.builder);
		this.revalidate();
		this.repaint();
	}

	public MapElement buildElement(Point position) {
		return builder.buildMapElement(position);
	}

	@Override
	public Boolean isDataValid() {
		return true;
	}

	@Override
	public String getInvalidMessage() {
		return "";
	}
}
