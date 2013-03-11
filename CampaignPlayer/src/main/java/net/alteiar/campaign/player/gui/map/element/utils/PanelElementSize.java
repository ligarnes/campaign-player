package net.alteiar.campaign.player.gui.map.element.utils;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.server.document.map.element.size.MapElementSize;
import net.alteiar.server.document.map.element.size.MapElementSizeMeter;
import net.alteiar.server.document.map.element.size.MapElementSizePixel;
import net.alteiar.server.document.map.element.size.MapElementSizeSquare;

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
				m = "Mètre";
				break;
			}
			return m;
		}
	};

	private final JComboBox<UnityType> comboBoxUnite;
	private final JTextField[] textFields;

	public PanelElementSize(int sizeCount) {
		super(new FlowLayout());
		comboBoxUnite = new JComboBox<UnityType>(UnityType.values());

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
