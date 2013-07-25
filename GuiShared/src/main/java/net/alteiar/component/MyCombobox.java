package net.alteiar.component;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MyCombobox<E> extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JComboBox combobox;

	public MyCombobox() {
		this(new JComboBox());
	}

	public MyCombobox(E[] values) {
		this(new JComboBox(values));
	}

	public MyCombobox(Collection<E> values) {
		this(new JComboBox(new Vector<E>(values)));
	}

	private MyCombobox(JComboBox combobox) {
		this.combobox = combobox;
		this.add(this.combobox);

	}

	public void setValues(E[] values) {
		combobox.setModel(new DefaultComboBoxModel(values));
	}

	public void setValues(Collection<E> values) {
		combobox.setModel(new DefaultComboBoxModel(new Vector<E>(values)));
	}

	public void setSelectedItem(E item) {
		combobox.setSelectedItem(item);
	}

	public E getItemAt(int idx) {
		return (E) combobox.getItemAt(idx);
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

	public void removeItem(E item) {
		combobox.removeItem(item);
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
