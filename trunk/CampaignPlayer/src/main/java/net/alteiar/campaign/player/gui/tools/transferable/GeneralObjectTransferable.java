package net.alteiar.campaign.player.gui.tools.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import net.alteiar.campaign.player.gui.tools.adapter.BasicAdapter;

public class GeneralObjectTransferable<E extends BasicAdapter<?>> implements
		Transferable {
	private final E adapter;
	private final MyDataFlavor dataFlavor;

	private static MyDataFlavor createConstant(Class<?> rc, String prn) {
		try {
			return new MyDataFlavor(rc, prn);
		} catch (Exception e) {
			return null;
		}
	}

	public GeneralObjectTransferable(E typeDegat) {
		this.adapter = typeDegat;
		dataFlavor = createConstant(typeDegat.getClass(), typeDegat.getClass()
				.getName());

	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] result = new DataFlavor[2];

		result[0] = dataFlavor;
		result[1] = DataFlavor.stringFlavor;

		return result;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return dataFlavor.equals(flavor)
				|| DataFlavor.stringFlavor.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (dataFlavor.equals(flavor))
			return adapter;
		else if (DataFlavor.stringFlavor.equals(flavor)) {
			return adapter.toString();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}
}
