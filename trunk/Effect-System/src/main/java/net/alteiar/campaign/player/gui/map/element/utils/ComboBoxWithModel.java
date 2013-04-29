package net.alteiar.campaign.player.gui.map.element.utils;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class ComboBoxWithModel<E> extends JComboBox<E> {
	private static final long serialVersionUID = 1L;

	private Object previousSelectedItem = null;
	private int previousSelectedIndex = -1;

	public Object getPreviousSelectedItem() {
		return previousSelectedItem;
	}

	public int getPreviousSelectedIndex() {
		return previousSelectedIndex;
	}

	ComboBoxWithModel(ComboBoxModel<E> aModel) {
		super(aModel);
	}

	@Override
	public void setSelectedIndex(int anIndex) {
		previousSelectedIndex = getSelectedIndex();
		previousSelectedItem = getSelectedItem();
		super.setSelectedIndex(anIndex);
	}

	@Override
	public void setSelectedItem(Object anObject) {
		previousSelectedIndex = getSelectedIndex();
		previousSelectedItem = getSelectedItem();
		super.setSelectedItem(anObject);
	}
}
