package net.alteiar.campaign.player.gui.tools.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import net.alteiar.campaign.player.gui.tools.adapter.BasicAdapter;

public class GeneralObjectTranfertHandler<E extends BasicAdapter<?>> extends
		TransferHandler {
	private static final long serialVersionUID = 1L;

	@Override
	protected Transferable createTransferable(JComponent c) {
		ListSource panel = (ListSource) c;
		E p = (E) panel.getSelectedItem().getObject();
		return new GeneralObjectTransferable<E>(p);
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	@Override
	public void exportDone(JComponent c, Transferable t, int action) {
		if (action == MOVE) {
			ListSource panel = (ListSource) c;
			panel.removeSelected();
		}
	}

	/**
	 * Teste si les donn�es propos�e comportent une PersonneFlavor. On pourrait
	 * aussi accepter des donn�es de mode texte, par exemple.
	 */
	@Override
	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		return getDataFlavorImport(c, flavors) != null;
	}

	public DataFlavor getDataFlavorImport(JComponent c, DataFlavor[] flavors) {
		DataFlavor result = null;
		for (DataFlavor dataFlavor : flavors) {
			if (dataFlavor.getClass().isInstance(MyDataFlavor.class)) {
				result = dataFlavor;
				break;
			}
		}
		return result;
	}

	/**
	 * L'importation de donn�es proprement dite.
	 * 
	 * @param c
	 *            : la cible du transfert.
	 * @param t
	 *            : donn�es � transf�rer
	 */
	@Override
	public boolean importData(JComponent c, Transferable t) {
		System.out.println("importData");
		try {
			// On extrait l'objet Personne du transferable
			E p = (E) t.getTransferData(getDataFlavorImport(c,
					t.getTransferDataFlavors()));
			// On le range dans la cible.
			((ObjectLabel) c).setObect(p);
			System.out.println("import work");
			return true;
		} catch (UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}
}
