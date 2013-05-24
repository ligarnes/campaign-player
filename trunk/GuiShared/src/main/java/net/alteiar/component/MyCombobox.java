package net.alteiar.component;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

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

	public MyCombobox(Collection<E> values) {
		combobox = new JComboBox<E>(new Vector<E>(values));
	}

	@SuppressWarnings("unchecked")
	public E getSelectedItem() {
		return (E) combobox.getSelectedItem();
	}

	@SuppressWarnings("unchecked")
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

	public void removeAllItems() {
		combobox.removeAllItems();
	}

	public int getItemCount() {
		return combobox.getItemCount();
	}
}
