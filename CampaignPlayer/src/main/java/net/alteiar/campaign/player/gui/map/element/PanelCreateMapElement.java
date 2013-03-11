package net.alteiar.campaign.player.gui.map.element;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.client.CampaignClient;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.DocumentMapElementBuilder;

public class PanelCreateMapElement extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private static PanelCreateMapElement mapElement = new PanelCreateMapElement();

	public static void createMapElement(MapClient<?> map, MapEvent event) {
		DialogOkCancel<PanelCreateMapElement> dlg = new DialogOkCancel<PanelCreateMapElement>(
				null, "Créer un element", true, mapElement);

		dlg.getMainPanel().refreshElements();
		dlg.setOkText("Créer");
		dlg.setCancelText("Annuler");
		dlg.setLocation(event.getMouseEvent().getLocationOnScreen());
		dlg.pack();
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			DocumentMapElementBuilder builder = dlg.getMainPanel()
					.buildElement(map, event.getMapPosition());
			CampaignClient.getInstance().createMapElement(map, builder);
		}
	}

	private final PanelMapElementBuilder[] panelBuilder = new PanelMapElementBuilder[] {
			new PanelCircleBuilder(), new PanelRectangleBuilder(),
			new PanelCharacterBuilder() };

	private PanelMapElementBuilder builder;

	private final JPanel panelWest;
	private final JPanel panelCenter;

	public PanelCreateMapElement() {
		super(new BorderLayout());

		panelWest = new JPanel(new GridLayout(panelBuilder.length, 1));

		this.add(panelWest, BorderLayout.WEST);

		builder = panelBuilder[0];
		panelCenter = new JPanel();
		panelCenter.add(builder);
		this.add(panelCenter, BorderLayout.CENTER);
	}

	private void refreshElements() {
		panelWest.removeAll();
		int maxWidth = 50;
		int maxHeight = 50;
		for (final PanelMapElementBuilder panel : panelBuilder) {
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
			select.setEnabled(panel.isAvailable());
			panelWest.add(select);
		}

		this.setPreferredSize(new Dimension(maxWidth, maxHeight));
	}

	private void selectBuilder(PanelMapElementBuilder builder) {
		panelCenter.removeAll();
		this.builder = builder;
		panelCenter.add(this.builder);
		this.revalidate();
		this.repaint();
	}

	public DocumentMapElementBuilder buildElement(MapClient<?> map,
			Point position) {
		return builder.buildMapElement(map, position);
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
