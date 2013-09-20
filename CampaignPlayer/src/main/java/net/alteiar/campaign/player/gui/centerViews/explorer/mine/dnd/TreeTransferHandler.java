package net.alteiar.campaign.player.gui.centerViews.explorer.mine.dnd;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreePath;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.DocumentNode;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.shared.UniqueID;

public class TreeTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;

	/**
	 * We only support importing strings.
	 */
	@Override
	public boolean canImport(TransferSupport info) {
		if (!info
				.isDataFlavorSupported(UniqueIdTransferable.FLAVOR_UNIQUE_ID_TRANSFERT)) {
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
		JTree tree = (JTree) c;

		TreePath path = tree.getSelectionPath();
		DocumentNode doc = (DocumentNode) path.getLastPathComponent();

		return new UniqueIdTransferable(doc.getUserObject().getId());
	}

	/**
	 * We support both copy and move actions.
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	protected UniqueID verifyDrop(TransferSupport info) {
		UniqueID data = null;

		if (info.isDrop()) {
			Transferable t = info.getTransferable();
			try {
				data = (UniqueID) t
						.getTransferData(UniqueIdTransferable.FLAVOR_UNIQUE_ID_TRANSFERT);
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
		UniqueID data = verifyDrop(info);

		if (data != null) {
			JTree target = (JTree) info.getComponent();
			BeanBasicDocument docToMove = CampaignClient.getInstance().getBean(
					data);

			Point p = info.getDropLocation().getDropPoint();
			TreePath path = target.getPathForLocation(p.x, p.y);

			BeanDirectory targetDir = null;
			if (path == null) {
				targetDir = CampaignClient.getInstance().getRootDirectory();
			} else {
				DocumentNode node = (DocumentNode) path.getLastPathComponent();

				if (node.getUserObject().isDirectory()) {
					targetDir = (BeanDirectory) node.getUserObject();
				} else {
					UniqueID dirBean = node.getUserObject().getParent();
					targetDir = CampaignClient.getInstance().getBean(dirBean);
				}
			}

			BeanDirectory previousDir = CampaignClient.getInstance().getBean(
					docToMove.getParent());

			if (!previousDir.equals(targetDir)) {
				previousDir.removeDocument(docToMove);
				targetDir.addDocument(docToMove);
				docToMove.setParent(targetDir.getId());
			}
		}
		return data != null;
	}

}
