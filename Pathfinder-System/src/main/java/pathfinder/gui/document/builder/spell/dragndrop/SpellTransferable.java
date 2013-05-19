package pathfinder.gui.document.builder.spell.dragndrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import pathfinder.bean.spell.Spell;

public class SpellTransferable implements Transferable {

	public static DataFlavor FLAVOR_SPELL = new DataFlavor(Spell.class, "Spell");

	private static DataFlavor[] SUPPORTED = new DataFlavor[] { FLAVOR_SPELL };

	private final Spell data;

	public SpellTransferable(Spell data) {
		this.data = data;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return SUPPORTED.clone();
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(FLAVOR_SPELL);
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
