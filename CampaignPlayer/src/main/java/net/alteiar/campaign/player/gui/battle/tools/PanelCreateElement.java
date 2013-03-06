package net.alteiar.campaign.player.gui.battle.tools;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.tools.PanelOkCancel;
import net.alteiar.server.document.map.element.size.MapElementSize;
import net.alteiar.server.document.map.element.size.MapElementSizeMeter;
import net.alteiar.server.document.map.element.size.MapElementSizePixel;
import net.alteiar.server.document.map.element.size.MapElementSizeSquare;

public class PanelCreateElement extends JPanel implements PanelOkCancel {
	private static PanelCreateElement INSTANCE = null;

	public static synchronized PanelCreateElement getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PanelCreateElement();
		}
		return INSTANCE;
	}

	public enum UnityType {
		CASES, PIXEL, METER
	};

	private final static String[] ALL_UNITE = { "cases", "pixel", "mètre" };

	private static final long serialVersionUID = 1L;
	private final JButton btnColorSelector;
	private final JTextField textFieldSize1;
	private final JTextField textFieldSize2;

	private Color currentColor;
	private final JComboBox comboBoxUnite;

	public PanelCreateElement() {
		currentColor = Color.BLUE;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		textFieldSize1 = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		add(textFieldSize1, gbc_textField);
		textFieldSize1.setColumns(10);

		textFieldSize2 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		add(textFieldSize2, gbc_textField_1);
		textFieldSize2.setColumns(10);

		comboBoxUnite = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		add(comboBoxUnite, gbc_comboBox);
		comboBoxUnite.setModel(new DefaultComboBoxModel(ALL_UNITE));

		btnColorSelector = new JButton();
		GridBagConstraints gbc_btnColorselector = new GridBagConstraints();
		gbc_btnColorselector.gridx = 3;
		gbc_btnColorselector.gridy = 1;
		add(btnColorSelector, gbc_btnColorselector);
		btnColorSelector.setIcon(Helpers.getIconColor(currentColor, 22, 22));
		btnColorSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectColor();
			}
		});
	}

	private MapElementSize getMapElementSize(String value)
			throws NumberFormatException {
		MapElementSize element = null;

		Double size = Double.valueOf(value);

		switch (UnityType.values()[comboBoxUnite.getSelectedIndex()]) {
		case PIXEL:
			element = new MapElementSizePixel(size);
			break;
		case METER:
			element = new MapElementSizeMeter(size);
			break;
		case CASES:
			element = new MapElementSizeSquare(size);
			break;
		}
		return element;
	}

	protected void selectColor() {
		Color temp = JColorChooser.showDialog(this, "Choisissez une couleur",
				currentColor);
		if (temp != null) {
			currentColor = temp;
			btnColorSelector
					.setIcon(Helpers.getIconColor(currentColor, 22, 22));
		}
	}

	public void setNeedSize2(boolean needSize2) {
		textFieldSize2.setEnabled(needSize2);
	}

	protected Boolean isSize2Needed() {
		return textFieldSize2.isEnabled();
	}

	public MapElementSize getSize1() {
		return getMapElementSize(textFieldSize1.getText());
	}

	public MapElementSize getSize2() {
		return getMapElementSize(textFieldSize2.getText());
	}

	public Color getColor() {
		return currentColor;
	}

	@Override
	public Boolean isDataValid() {
		Boolean isValid = true;
		try {
			getSize1();
			if (isSize2Needed()) {
				getSize2();
			}
		} catch (NumberFormatException ex) {
			isValid = false;
		}
		return isValid;
	}

	@Override
	public String getInvalidMessage() {
		return "Les dimensions doivent êtres des nombres";
	}
}
