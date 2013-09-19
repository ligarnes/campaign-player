package net.alteiar.list.dnd;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import net.alteiar.list.DataListModel;

public class ListTransferHandler<E> extends TransferHandler {
	private static final long serialVersionUID = 1L;

	/**
	 * We only support importing strings.
	 */
	@Override
	public boolean canImport(TransferSupport info) {
		if (!info
				.isDataFlavorSupported(ObjectTransferable.FLAVOR_OBJECT_TRANSFERT)) {
			return false;
		}
		return true;
	}

	/**
	 * Bundle up the selected items in a single list for export. Each line is
	 * separated by a newline.
	 */
	@Override
	protected Transferable createTransferable(JComponent c) {
		JList<E> list = (JList<E>) c;
		E spell = list.getSelectedValue();

		return new ObjectTransferable<E>(new TransfertObjectEncapsulator<E>(
				spell));
	}

	/**
	 * We support both copy and move actions.
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	protected TransfertObjectEncapsulator<E> verifyDrop(TransferSupport info) {
		TransfertObjectEncapsulator<E> data = null;

		if (info.isDrop()) {
			Transferable t = info.getTransferable();
			try {
				data = (TransfertObjectEncapsulator<E>) t
						.getTransferData(ObjectTransferable.FLAVOR_OBJECT_TRANSFERT);
			} catch (UnsupportedFlavorException e) {
				data = null;
			} catch (IOException e) {
				data = null;
			}
		}
		return data;
	}

	/**
	 * Perform the actual import.
	 */
	@Override
	public boolean importData(TransferSupport info) {
		TransfertObjectEncapsulator<E> data = verifyDrop(info);

		if (data != null) {
			JList<E> target = (JList<E>) info.getComponent();

			DataListModel<E> listModel = (DataListModel<E>) target.getModel();

			listModel.addData(data.getObject());
		}
		return data != null;
	}

	/**
	 * Remove the items moved from the list.
	 */
	@Override
	protected void exportDone(JComponent c, Transferable t, int action) {
		JList<E> source = (JList<E>) c;

		DataListModel<E> listModel = (DataListModel<E>) source.getModel();

		if (action == TransferHandler.MOVE) {
			TransfertObjectEncapsulator<E> data;
			try {
				data = (TransfertObjectEncapsulator<E>) t
						.getTransferData(ObjectTransferable.FLAVOR_OBJECT_TRANSFERT);
				listModel.removeData(data.getObject());
				source.revalidate();
				source.repaint();
			} catch (Exception e) {

			}
		}
	}
}
