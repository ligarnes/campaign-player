package pathfinder.gui.mapElement.editor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.map.element.utils.PanelElementSize;
import net.alteiar.shared.ImageUtil;
import pathfinder.gui.mapElement.PathfinderCharacterElement;

public class PanelCharacterEditor extends
		PanelMapElementEditor<PathfinderCharacterElement> {
	private static final long serialVersionUID = 1L;

	private final PanelElementSize panelWidth;
	private final PanelElementSize panelHeight;

	public PanelCharacterEditor(PathfinderCharacterElement mapElement) {
		super(mapElement);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblImg = new JLabel();
		BufferedImage img = getMapElement().getCharacter().getCharacterImage();
		img = ImageUtil.resizeImage(img, 50, 50);
		lblImg.setIcon(new ImageIcon(img));
		lblImg.setPreferredSize(new Dimension(50, 50));
		lblImg.setMaximumSize(new Dimension(50, 50));
		lblImg.setMinimumSize(new Dimension(50, 50));
		GridBagConstraints gbc_lblImg = new GridBagConstraints();
		gbc_lblImg.gridheight = 3;
		gbc_lblImg.insets = new Insets(0, 0, 0, 5);
		gbc_lblImg.gridx = 0;
		gbc_lblImg.gridy = 0;
		add(lblImg, gbc_lblImg);

		JLabel lblName = new JLabel(getMapElement().getCharacter().getName());
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.gridwidth = 3;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);

		JLabel lblWidth = new JLabel("Largueur");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 1;
		gbc_lblWidth.gridy = 1;
		add(lblWidth, gbc_lblWidth);

		panelWidth = new PanelElementSize(1);
		panelWidth.setElementSizeAt(0, getMapElement().getWidth());
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 1;
		add(panelWidth, gbc_panel);

		JLabel lblHeight = new JLabel("Longueur");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.insets = new Insets(0, 0, 0, 5);
		gbc_lblHeight.gridx = 1;
		gbc_lblHeight.gridy = 2;
		add(lblHeight, gbc_lblHeight);

		panelHeight = new PanelElementSize(1);
		panelHeight.setElementSizeAt(0, getMapElement().getHeight());
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 2;
		add(panelHeight, gbc_panel_1);
	}

	@Override
	public Boolean isDataValid() {
		return true;
	}

	@Override
	public String getInvalidMessage() {
		return "";
	}

	@Override
	public void applyModification() {
		PathfinderCharacterElement characterElement = getMapElement();
		characterElement.setWidth(panelWidth.getMapElementSize(0));
		characterElement.setHeight(panelHeight.getMapElementSize(0));

	}
}
