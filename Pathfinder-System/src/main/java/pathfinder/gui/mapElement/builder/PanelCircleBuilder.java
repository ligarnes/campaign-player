package pathfinder.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.utils.PanelElementSize;
import net.alteiar.campaign.player.gui.map.element.utils.PanelSelectColor;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.utils.map.element.MapElementSize;

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
	public CircleElement buildMapElement(Point position) {
		return new CircleElement(position, getSelectedColor(), getElementSize());
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
