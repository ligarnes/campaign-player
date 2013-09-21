package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.NewDirectoryAction;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.PropertyDocumentAction;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.RemoveDirectoryAction;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.files.DeleteDocumentAction;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.files.NewDocumentAction;
import net.alteiar.campaign.player.gui.centerViews.explorer.actions.files.ViewDocumentAction;
import net.alteiar.campaign.player.gui.centerViews.explorer.tree.DocumentNode;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.documents.BeanDocument;

public class DocumentExplorerListener extends MouseAdapter {

	private final JTree tree;

	public DocumentExplorerListener(JTree tree) {
		this.tree = tree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			rightClick(e);
		}
	}

	private void rightClick(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());

		if (path == null) {
			rootClick(e);
		} else {
			DocumentNode node = (DocumentNode) path.getLastPathComponent();

			BeanBasicDocument doc = node.getUserObject();
			BeanDirectory parent = CampaignClient.getInstance().getBean(
					doc.getParent());
			dirClick(e, parent, doc);
		}
	}

	private void rootClick(MouseEvent e) {
		BeanDirectory root = CampaignClient.getInstance().getRootDirectory();

		JPopupMenu popup = new JPopupMenu();

		popup.add(new JMenuItem(new NewDirectoryAction(root)));
		popup.add(new JMenuItem(new NewDocumentAction(root)));

		popup.show(tree, e.getX(), e.getY());
	}

	private void dirClick(MouseEvent e, BeanDirectory parent,
			BeanBasicDocument doc) {

		JPopupMenu popup = new JPopupMenu();

		if (doc.isDirectory()) {
			BeanDirectory dir = (BeanDirectory) doc;

			popup.add(new JMenuItem(new PropertyDocumentAction(dir)));
			popup.add(new JMenuItem(new RemoveDirectoryAction(dir)));
			popup.addSeparator();
			popup.add(new JMenuItem(new NewDirectoryAction(dir)));
			popup.add(new JMenuItem(new NewDocumentAction(dir)));
		} else {
			BeanDocument file = (BeanDocument) doc;
			popup.add(new JMenuItem(new PropertyDocumentAction(file)));
			popup.add(new JMenuItem(new ViewDocumentAction(file)));
			popup.addSeparator();
			popup.add(new JMenuItem(new DeleteDocumentAction(file)));
		}

		popup.show(tree, e.getX(), e.getY());
	}
}
