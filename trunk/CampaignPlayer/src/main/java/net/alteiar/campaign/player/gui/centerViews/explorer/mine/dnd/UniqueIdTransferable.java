package net.alteiar.campaign.player.gui.centerViews.explorer.mine.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import net.alteiar.list.dnd.TransfertObjectEncapsulator;
import net.alteiar.shared.UniqueID;

public class UniqueIdTransferable implements Transferable {

	public static DataFlavor FLAVOR_UNIQUE_ID_TRANSFERT = new DataFlavor(
			TransfertObjectEncapsulator.class, "TransfertObjectEncaps");

	private static DataFlavor[] SUPPORTED = new DataFlavor[] { FLAVOR_UNIQUE_ID_TRANSFERT };

	private final UniqueID data;

	public UniqueIdTransferable(UniqueID data) {
		this.data = data;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return SUPPORTED.clone();
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(FLAVOR_UNIQUE_ID_TRANSFERT);
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
