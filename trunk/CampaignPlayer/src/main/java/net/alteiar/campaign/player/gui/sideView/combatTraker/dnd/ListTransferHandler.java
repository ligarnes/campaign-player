package net.alteiar.campaign.player.gui.sideView.combatTraker.dnd;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import net.alteiar.beans.combatTraker.CombatTraker;

import org.apache.log4j.Logger;

public class ListTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;

	private final CombatTraker traker;

	public ListTransferHandler(CombatTraker traker) {
		this.traker = traker;
	}

	@Override
	public boolean canImport(TransferSupport support) {
		return support.isDataFlavorSupported(IndexTransferable.FLAVOR_INDEX);
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		JList list = (JList) c;
		int idx = list.getSelectedIndex();
		return new IndexTransferable(idx);
	}

	/**
	 * Perform the actual import.
	 */
	@Override
	public boolean importData(TransferSupport support) {
		boolean importData = false;
		try {
			int currentIdx = (Integer) support.getTransferable()
					.getTransferData(IndexTransferable.FLAVOR_INDEX);

			int newIdx = ((JList) support.getComponent()).getSelectedIndex();

			// if move up an element move it over the current element (default)
			// if move down an element move it under the current element
			if (newIdx > currentIdx) {
				newIdx++;
			}

			// if no idx put the element at the last position
			if (newIdx == -1) {
				newIdx = traker.getUnitsId().size();
			}

			traker.moveUnit(currentIdx, newIdx);
			importData = true;
		} catch (UnsupportedFlavorException e) {
			Logger.getLogger(getClass()).warn(
					"Impossible de faire un drop dans cette region", e);
		} catch (Exception e) {
			Logger.getLogger(getClass()).warn(
					"Erreur inattendu durant le drag and drop", e);
		}

		return importData;
	}
}
