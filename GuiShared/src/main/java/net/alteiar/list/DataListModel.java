package net.alteiar.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

public class DataListModel<E> extends AbstractListModel<E> implements
		ListModel<E> {
	private static final long serialVersionUID = 1L;

	private final ArrayList<E> datas;

	public DataListModel(Collection<E> datas) {
		this.datas = new ArrayList<E>();
		this.datas.addAll(datas);
	}

	public DataListModel() {
		this.datas = new ArrayList<E>();
	}

	public ArrayList<E> getDatas() {
		return datas;
	}

	public void addDatas(List<E> data) {
		this.datas.addAll(data);

		this.fireIntervalAdded(this, datas.size() - data.size(), datas.size());
	}

	public void addData(E data) {
		this.datas.add(data);

		this.fireIntervalAdded(this, datas.size() - 1, datas.size());
	}

	public void removeDatas(List<E> data) {
		this.datas.removeAll(data);
		this.fireIntervalRemoved(this, datas.size(), datas.size() + data.size());
	}

	public void removeData(E data) {
		this.datas.remove(data);
		this.fireIntervalRemoved(this, datas.size(), datas.size() + 1);
	}

	public void clear() {
		this.datas.clear();
	}

	@Override
	public int getSize() {
		return datas.size();
	}

	@Override
	public E getElementAt(int index) {
		return datas.get(index);
	}
}
