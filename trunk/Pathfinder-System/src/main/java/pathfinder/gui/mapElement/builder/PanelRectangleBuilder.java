package pathfinder.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.utils.PanelElementSize;
import net.alteiar.panel.PanelSelectColor;
import net.alteiar.map.elements.RectangleElement;
import net.alteiar.utils.map.element.MapElementSize;

public class PanelRectangleBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final PanelSelectColor panelColor;
	private final PanelElementSize panelSize;

	public PanelRectangleBuilder() {
		super();

		panelColor = new PanelSelectColor();
		panelSize = new PanelElementSize(2);

		this.setLayout(new BorderLayout());
		this.add(new JLabel("Cr\u00E9er un rectangle"), BorderLayout.NORTH);

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
	public RectangleElement buildMapElement(Point position) {
		return new RectangleElement(position, getSelectedColor(),
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
