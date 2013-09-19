package net.alteiar.campaign.player.gui.sideView.combatTraker.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import net.alteiar.beans.combatTraker.CombatTrackerUnit;

public class IndexTransferable implements Transferable {

	public static DataFlavor FLAVOR_INDEX = new DataFlavor(
			CombatTrackerUnit.class, "CombatTrackerUnit");

	private static DataFlavor[] SUPPORTED = new DataFlavor[] { FLAVOR_INDEX };

	private final int idx;

	public IndexTransferable(int data) {
		this.idx = data;
	}

	public int getIdx() {
		return idx;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return SUPPORTED.clone();
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(FLAVOR_INDEX);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException {
		if (!isDataFlavorSupported(flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return idx;
	}

}
