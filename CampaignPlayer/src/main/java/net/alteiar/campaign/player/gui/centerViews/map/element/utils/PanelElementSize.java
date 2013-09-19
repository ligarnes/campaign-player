package net.alteiar.campaign.player.gui.centerViews.map.element.utils;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.beans.map.size.MapElementSize;
import net.alteiar.beans.map.size.MapElementSizeMeter;
import net.alteiar.beans.map.size.MapElementSizePixel;
import net.alteiar.beans.map.size.MapElementSizeSquare;
import net.alteiar.component.MyCombobox;

public class PanelElementSize extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum UnityType {
		CASES, PIXEL, METER;

		@Override
		public String toString() {
			String m = "Inconnu";
			switch (this) {
			case CASES:
				m = "Cases";
				break;
			case PIXEL:
				m = "Pixel";
				break;
			case METER:
				m = "M\u00E8tre";
				break;
			}
			return m;
		}
	};

	private final MyCombobox<UnityType> comboBoxUnite;
	private final JTextField[] textFields;

	public void setElementSizeAt(int i, MapElementSize elementSize) {
		if (elementSize instanceof MapElementSizeSquare) {
			comboBoxUnite.setSelectedItem(UnityType.CASES);
			textFields[i].setText(((MapElementSizeSquare) elementSize)
					.getSquareSize().toString());
		} else if (elementSize instanceof MapElementSizePixel) {
			comboBoxUnite.setSelectedItem(UnityType.CASES);
			textFields[i].setText(((MapElementSizePixel) elementSize)
					.getPixels().toString());
		} else if (elementSize instanceof MapElementSizeMeter) {
			comboBoxUnite.setSelectedItem(UnityType.CASES);
			textFields[i].setText(((MapElementSizeMeter) elementSize)
					.getMeter().toString());
		}
	}

	public PanelElementSize(int sizeCount) {
		super(new FlowLayout());
		comboBoxUnite = new MyCombobox<UnityType>(UnityType.values());

		textFields = new JTextField[sizeCount];
		for (int i = 0; i < sizeCount; i++) {
			textFields[i] = new JTextField(5);
			this.add(textFields[i]);
		}
		this.add(comboBoxUnite);
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

	public MapElementSize getMapElementSize(int i) {
		return getMapElementSize(textFields[i].getText());
	}
}
