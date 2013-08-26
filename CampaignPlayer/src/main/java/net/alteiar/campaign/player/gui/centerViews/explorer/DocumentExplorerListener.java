package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.NewDocumentAction;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.RemoveDirectoryAction;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDirectory;

public class DocumentExplorerListener extends MouseAdapter {

	private final JTree tree;

	public DocumentExplorerListener(JTree tree) {
		this.tree = tree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());

			if (path == null) {
				rootClick(e);
			} else {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				DocumentAdapter docAdapter = (DocumentAdapter) node
						.getUserObject();

				BeanBasicDocument doc = docAdapter.getDocument();
				BeanDirectory parent = CampaignClient.getInstance().getBean(
						doc.getParent());
				dirClick(e, parent, doc);
			}

		}
	}

	private void rootClick(MouseEvent e) {
		BeanDirectory root = CampaignClient.getInstance().getRootDirectory();

		JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItem(new NewDocumentAction(root)));

		popup.show(tree, e.getX(), e.getY());
	}

	private void dirClick(MouseEvent e, BeanDirectory parent,
			BeanBasicDocument doc) {

		if (doc.isDirectory()) {
			BeanDirectory dir = (BeanDirectory) doc;

			JPopupMenu popup = new JPopupMenu();

			popup.add(new JMenuItem(new RemoveDirectoryAction(dir)));
			popup.add(new JMenuItem(new NewDocumentAction(dir)));

			popup.show(tree, e.getX(), e.getY());
		} else {

			System.out.println("is a file");
			JPopupMenu menu = new JPopupMenu();
		}
	}
}
