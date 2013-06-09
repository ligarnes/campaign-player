package pathfinder.gui.mapElement.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.centerViews.map.element.utils.PanelElementSize;
import net.alteiar.map.elements.RectangleElement;
import net.alteiar.utils.map.Scale;
import net.alteiar.utils.map.element.MapElementSize;

public class PanelRectangleEditor extends
		PanelMapElementEditor<RectangleElement> {
	private static final long serialVersionUID = 1L;

	private final PanelElementSize panelWidth;
	private final PanelElementSize panelHeight;

	public PanelRectangleEditor(RectangleElement mapElement) {
		super(mapElement);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblWidth = new JLabel("Largueur");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 0;
		add(lblWidth, gbc_lblWidth);

		panelWidth = new PanelElementSize(1);
		panelWidth.setElementSizeAt(0, getMapElement().getWidth());
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		add(panelWidth, gbc_panel);

		JLabel lblHeight = new JLabel("Longueur");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 1;
		add(lblHeight, gbc_lblHeight);

		panelHeight = new PanelElementSize(1);
		panelHeight.setElementSizeAt(0, getMapElement().getHeight());
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		add(panelHeight, gbc_panel_1);
	}

	@Override
	public Boolean isDataValid() {
		Boolean isValid = true;
		try {
			MapElementSize size = panelWidth.getMapElementSize(0);
			size.getPixels(new Scale());
			size = panelHeight.getMapElementSize(0);
			size.getPixels(new Scale());
		} catch (Exception ex) {
			isValid = false;
		}

		return isValid;
	}

	@Override
	public String getInvalidMessage() {
		return "La hauteur ou la largueur est invalide";
	}

	@Override
	public void applyModification() {
		RectangleElement characterElement = getMapElement();
		characterElement.setWidth(panelWidth.getMapElementSize(0));
		characterElement.setHeight(panelHeight.getMapElementSize(0));
	}
}
