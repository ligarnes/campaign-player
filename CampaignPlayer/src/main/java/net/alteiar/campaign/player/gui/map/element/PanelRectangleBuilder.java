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
import net.alteiar.server.document.map.element.colored.rectangle.DocumentRectangleBuilder;
import net.alteiar.server.document.map.element.size.MapElementSize;

public class PanelRectangleBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final PanelSelectColor panelColor;
	private final PanelElementSize panelSize;

	public PanelRectangleBuilder() {
		super();

		panelColor = new PanelSelectColor();
		panelSize = new PanelElementSize(2);

		this.setLayout(new BorderLayout());
		this.add(new JLabel("Créer un rectangle"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.add(buildRow("Couleur: ", panelColor));
		panelCenter.add(buildRow("largueur et hauteur: ", panelSize));

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

	private MapElementSize getElementSizeWidth() {
		return panelSize.getMapElementSize(0);
	}

	private MapElementSize getElementSizeHeight() {
		return panelSize.getMapElementSize(1);
	}

	@Override
	public DocumentMapElementBuilder buildMapElement(MapClient<?> map,
			Point position) {
		return new DocumentRectangleBuilder(map, position, getSelectedColor(),
				getElementSizeWidth(), getElementSizeHeight());
	}

	@Override
	public Boolean isAvailable() {
		return true;
	}

	@Override
	public String getElementName() {
		return "Rectangle";
	}

	@Override
	public String getElementDescription() {
		return "Dessine un rectangle";
	}
}