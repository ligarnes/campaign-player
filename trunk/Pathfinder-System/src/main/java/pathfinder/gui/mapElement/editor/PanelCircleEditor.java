package pathfinder.gui.mapElement.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.map.element.utils.PanelElementSize;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.utils.map.Scale;
import net.alteiar.utils.map.element.MapElementSize;

public class PanelCircleEditor extends PanelMapElementEditor<CircleElement> {
	private static final long serialVersionUID = 1L;

	private final PanelElementSize panelRadius;

	public PanelCircleEditor(CircleElement mapElement) {
		super(mapElement);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblRadius = new JLabel("Rayon");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.gridx = 0;
		gbc_lblRadius.gridy = 0;
		add(lblRadius, gbc_lblRadius);

		panelRadius = new PanelElementSize(1);
		panelRadius.setElementSizeAt(0, getMapElement().getRadius());
		GridBagConstraints gbc_panelRadius = new GridBagConstraints();
		gbc_panelRadius.insets = new Insets(0, 0, 5, 5);
		gbc_panelRadius.fill = GridBagConstraints.BOTH;
		gbc_panelRadius.gridx = 1;
		gbc_panelRadius.gridy = 0;
		add(panelRadius, gbc_panelRadius);

	}

	@Override
	public Boolean isDataValid() {
		Boolean isValid = true;
		try {
			MapElementSize size = panelRadius.getMapElementSize(0);
			size.getPixels(new Scale());
		} catch (Exception ex) {
			isValid = false;
		}

		return isValid;
	}

	@Override
	public String getInvalidMessage() {
		return "le rayon est invalide";
	}

	@Override
	public void applyModification() {
		CircleElement circleElement = getMapElement();
		circleElement.setRadius(panelRadius.getMapElementSize(0));
	}
}
