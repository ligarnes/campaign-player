package net.alteiar.list.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ObjectTransferable<E> implements Transferable {

	public static DataFlavor FLAVOR_OBJECT_TRANSFERT = new DataFlavor(
			TransfertObjectEncapsulator.class, "TransfertObjectEncaps");

	private static DataFlavor[] SUPPORTED = new DataFlavor[] { FLAVOR_OBJECT_TRANSFERT };

	private final TransfertObjectEncapsulator<E> data;

	public ObjectTransferable(TransfertObjectEncapsulator<E> data) {
		this.data = data;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return SUPPORTED.clone();
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(FLAVOR_OBJECT_TRANSFERT);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException {
		if (!isDataFlavorSupported(flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return data;
	}

}
