package net.alteiar.component;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MyCombobox<E> extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JComboBox<E> combobox;

	public MyCombobox() {
		combobox = new JComboBox<>();
		this.add(combobox);
	}

	public MyCombobox(E[] values) {
		combobox = new JComboBox<>(values);
	}

	public E getSelectedItem() {
		return (E) combobox.getSelectedItem();
	}

	public ArrayList<E> getSelectedItems() {
		Object[] obj = combobox.getSelectedObjects();
		ArrayList<E> selected = new ArrayList<E>(obj.length);
		for (Object o : obj) {
			selected.add((E) o);
		}
		return selected;
	}

	public int getSelectedIndex() {
		return combobox.getSelectedIndex();
	}

	public void addItem(E item) {
		combobox.addItem(item);
	}

	public void addActionListener(ActionListener listener) {
		combobox.addActionListener(listener);
	}
}
