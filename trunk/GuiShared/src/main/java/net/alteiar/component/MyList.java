package net.alteiar.component;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class MyList<E> extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JList<E> list;

	public MyList() {
		this(new JList<E>());
	}

	public MyList(Vector<E> listData) {
		this(new JList<E>(listData));
	}

	public MyList(Collection<E> listData) {
		this(new JList<E>(new Vector<E>(listData)));
	}

	private MyList(JList<E> listNew) {
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

	public void setCellRenderer(ListCellRenderer<E> cellRenderer) {
		list.setCellRenderer(cellRenderer);
	}

	public void setSelectedIndex(int idx) {
		list.setSelectedIndex(idx);
	}

	public E getSelectedValue() {
		return list.getSelectedValue();
	}

	public List<E> getSelectedValues() {
		return list.getSelectedValuesList();
	}

}
