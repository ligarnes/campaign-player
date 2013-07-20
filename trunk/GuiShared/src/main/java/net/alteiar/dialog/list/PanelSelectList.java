package net.alteiar.dialog.list;

import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import net.alteiar.component.MyList;
import net.alteiar.dialog.PanelOkCancel;

public class PanelSelectList<E> extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private final MyList<E> datas;

	public PanelSelectList(Collection<E> elements, boolean multipleSelection) {
		datas = new MyList<E>(elements);

		if (multipleSelection) {
			datas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			datas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		this.add(datas);
	}

	public List<E> getSelectedValues() {
		return datas.getSelectedValues();
	}

	public E getSelectedValue() {
		return datas.getSelectedValue();
	}

	@Override
	public Boolean isDataValid() {
		return !datas.getSelectedValues().isEmpty();
	}

	@Override
	public String getInvalidMessage() {
		return "Vous devez séléctionner au moins un éléments";
	}

}
