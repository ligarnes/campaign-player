package pathfinder.gui.document.spell.dragndrop;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import pathfinder.bean.spell.Spell;
import pathfinder.gui.document.spell.SpellListModel;

public class SpellListTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;

	/**
	 * We only support importing strings.
	 */
	@Override
	public boolean canImport(TransferSupport info) {
		if (!info.isDataFlavorSupported(SpellTransferable.FLAVOR_SPELL)) {
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
		JList<Spell> list = (JList<Spell>) c;
		Spell spell = list.getSelectedValue();

		return new SpellTransferable(spell);
	}

	/**
	 * We support both copy and move actions.
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	protected Spell verifyDrop(TransferSupport info) {
		Spell data = null;

		if (info.isDrop()) {
			Transferable t = info.getTransferable();
			try {
				data = (Spell) t
						.getTransferData(SpellTransferable.FLAVOR_SPELL);
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
		Spell data = verifyDrop(info);

		if (data != null) {
			JList<Spell> target = (JList<Spell>) info.getComponent();

			SpellListModel listModel = (SpellListModel) target.getModel();

			listModel.addSpell(data);
		}
		return data != null;
	}

	/**
	 * Remove the items moved from the list.
	 */
	@Override
	protected void exportDone(JComponent c, Transferable t, int action) {
		JList<Spell> source = (JList<Spell>) c;

		SpellListModel listModel = (SpellListModel) source.getModel();

		if (action == TransferHandler.MOVE) {
			Spell data;
			try {
				data = (Spell) t
						.getTransferData(SpellTransferable.FLAVOR_SPELL);
				listModel.removeSpell(data);
				source.revalidate();
				source.repaint();
			} catch (Exception e) {

			}
		}
	}
}
