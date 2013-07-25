package net.alteiar.component;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class MyList<E> extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JList list;

	public MyList() {
		this(new JList());
	}

	public MyList(Vector<E> listData) {
		this(new JList(listData));
	}

	public MyList(Collection<E> listData) {
		this(new JList(new Vector<E>(listData)));
	}

	private MyList(JList listNew) {
		super(new BorderLayout());
		this.list = listNew;
		this.add(list, BorderLayout.CENTER);
	}

	public void setSelectionMode(int mode) {
		list.setSelectionMode(mode);
	}

	public void setLayoutOrientation(int orientation) {
		list.setLayoutOrientation(orientation);
	}

	public void setSelectedValue(E item, boolean shouldScroll) {
		list.setSelectedValue(item, shouldScroll);
	}

	public void setCellRenderer(ListCellRenderer cellRenderer) {
		list.setCellRenderer(cellRenderer);
	}

	public void setSelectedIndex(int idx) {
		list.setSelectedIndex(idx);
	}

	@SuppressWarnings("unchecked")
	public E getSelectedValue() {
		return (E) list.getSelectedValue();
	}

	@SuppressWarnings("unchecked")
	public List<E> getSelectedValues() {
		Object[] values = list.getSelectedValues();

		ArrayList<E> results = new ArrayList<E>();
		for (Object object : values) {
			results.add((E) object);
		}

		return results;
		// for jdk1.7
		// return list.getSelectedValues();
	}

}
