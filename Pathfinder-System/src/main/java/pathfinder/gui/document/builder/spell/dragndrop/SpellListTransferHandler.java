package pathfinder.gui.document.builder.spell.dragndrop;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import pathfinder.bean.spell.Spell;
import pathfinder.gui.document.builder.spell.SpellListModel;

public class SpellListTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;

	public SpellListTransferHandler() {
	}

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
		JList list = (JList) c;
		Spell spell = (Spell) list.getSelectedValue();

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

	JList target = null;;

	/**
	 * Perform the actual import.
	 */
	@Override
	public boolean importData(TransferSupport info) {
		Spell data = verifyDrop(info);

		if (data != null) {
			target = (JList) info.getComponent();

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
		JList source = (JList) c;

		if (source == target) {
			System.out.println("source == target");
			return;
		}

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
