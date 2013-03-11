package net.alteiar.campaign.player.gui.map.element;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.utils.PanelElementSize;
import net.alteiar.campaign.player.gui.map.element.utils.PanelSelectColor;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.DocumentMapElementBuilder;
import net.alteiar.server.document.map.element.colored.circle.DocumentCircleBuilder;
import net.alteiar.server.document.map.element.size.MapElementSize;

public class PanelCircleBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final PanelSelectColor panelColor;
	private final PanelElementSize panelSize;

	public PanelCircleBuilder() {
		panelColor = new PanelSelectColor();
		panelSize = new PanelElementSize(1);

		this.setLayout(new BorderLayout());
		this.add(new JLabel("Créer un cercle"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.add(buildRow("Couleur: ", panelColor));
		panelCenter.add(buildRow("Rayon: ", panelSize));

		this.add(panelCenter, BorderLayout.CENTER);
	}

	private static JPanel buildRow(String name, JPanel panel) {
		JPanel container = new JPanel(new FlowLayout());
		container.add(new JLabel(name));
		container.add(panel);
		return container;
	}

	private Color getSelectedColor() {
		return panelColor.getColor();
	}

	private MapElementSize getElementSize() {
		return panelSize.getMapElementSize(0);
	}

	@Override
	public DocumentMapElementBuilder buildMapElement(MapClient<?> map,
			Point position) {
		return new DocumentCircleBuilder(map, position, getSelectedColor(),
				getElementSize());
	}

	@Override
	public Boolean isAvailable() {
		return true;
	}

	@Override
	public String getElementName() {
		return "Cercle";
	}

	@Override
	public String getElementDescription() {
		return "Dessine un cercle";
	}
}
