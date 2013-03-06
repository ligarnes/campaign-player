package net.alteiar.campaign.player.gui.tools.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import net.alteiar.server.document.player.PlayerClient;

public class PlayerTranferable implements Transferable {

	private final PlayerClient player;

	public PlayerTranferable(PlayerClient player) {
		this.player = player;

	}

	private static DataFlavor createConstant(Class<?> rc, String prn) {
		try {
			return new DataFlavor(rc, prn);
		} catch (Exception e) {
			return null;
		}
	}

	public static final DataFlavor PLAYER_FLAVOR = createConstant(
			PlayerClient.class, "Joueur");

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] result = new DataFlavor[2];
		result[0] = PLAYER_FLAVOR;
		result[1] = DataFlavor.stringFlavor;
		return result;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return PLAYER_FLAVOR.equals(flavor)
				|| DataFlavor.stringFlavor.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (PLAYER_FLAVOR.equals(flavor))
			return player;
		else if (DataFlavor.stringFlavor.equals(flavor)) {
			return player.getId();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

}
