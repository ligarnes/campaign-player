package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.alteiar.WaitBeanListener;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignListener;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.DocumentNode;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.MyTreeCellRenderer;
import net.alteiar.campaign.player.gui.documents.PanelCreateDocument;
import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;

import org.apache.log4j.Logger;

public class PanelDocumentExplorer extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String ICON_SAVE = "save.png";

	private final JTree tree;
	private final DefaultTreeModel treeModel;

	public PanelDocumentExplorer() {
		this.setLayout(new BorderLayout());

		BeanDirectory root = CampaignClient.getInstance().getRootDirectory();
		DefaultMutableTreeNode all = new DocumentNode(root, root.isDirectory());

		treeModel = new DefaultTreeModel(all, true);
		tree = new JTree();
		tree.setModel(treeModel);
		tree.setRootVisible(false);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		tree.setCellRenderer(new MyTreeCellRenderer());

		// tree.setCellEditor(new MyTreeCellEditor(tree));
		// tree.setEditable(true);

		tree.setRowHeight(38);

		JScrollPane treeView = new JScrollPane(tree);
		this.add(treeView, BorderLayout.CENTER);

		this.add(createPanelCreate(), BorderLayout.SOUTH);

		refreshTree();

		tree.addMouseListener(new DocumentExplorerListener(tree));

		CampaignClient.getInstance().addCampaignListener(
				new CampaignListener() {

					@Override
					public void playerAdded(Player player) {

					}

					@Override
					public void beanRemoved(BeanBasicDocument bean) {
						refreshTree();
					}

					@Override
					public void beanAdded(BeanBasicDocument bean) {
						refreshTree();
					}
				});

		this.setMinimumSize(new Dimension(300, 300));
		this.setPreferredSize(new Dimension(300, 300));
	}

	protected JPanel createPanelCreate() {
		JPanel pane = new JPanel();
		JButton btnAdd = new JButton("Ajouter");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//
				TreePath selection = tree.getSelectionPath();

				DefaultMutableTreeNode dirNode = (DefaultMutableTreeNode) treeModel
						.getRoot();
				if (selection != null) {
					dirNode = (DefaultMutableTreeNode) selection
							.getLastPathComponent();

					if (!dirNode.getAllowsChildren()) {
						dirNode = (DefaultMutableTreeNode) dirNode.getParent();
					}
				}
				PanelCreateDocument.createDocument((BeanDirectory) dirNode
						.getUserObject());
			}
		});

		JButton btnSave = new JButton(HelpersImages.getIcon(ICON_SAVE, 24, 24));
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CampaignClient.getInstance().saveGame();
					JOptionPane.showMessageDialog(null,
							"La campagne à bien \u00E9t\u00E9 sauvegard\u00E9",
							"Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					Logger.getLogger(getClass()).error(
							"Impossible de sauver la campagne", e1);
					ExceptionTool.showError(e1,
							"Impossible de sauver la campagne");
				}
			}
		});

		pane.add(btnSave);
		pane.add(btnAdd);
		return pane;
	}

	private void refreshTree() {
		Threads.execute(new MyRunnable() {
			@Override
			public void run() {
				DocumentNode root = (DocumentNode) treeModel.getRoot();
				synchronized (treeModel) {
					fillIt(root);
				}

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						treeModel.reload();
						revalidate();
						repaint();
					}
				});
			}

			@Override
			public String getTaskName() {
				return "refresh document tree";
			}
		});
	}

	private void fillIt(final DocumentNode parent) {
		BeanBasicDocument dir = parent.getUserObject();

		BeanDirectory beanDir = (BeanDirectory) dir;

		beanDir.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				refreshTree();
			}
		});

		Map<String, DocumentNode> existingDocs = new HashMap<>();

		for (int i = 0; i < parent.getChildCount(); i++) {
			DocumentNode childNode = (DocumentNode) parent.getChildAt(i);
			String documentId = childNode.getUserObject().getId().toString();
			existingDocs.put(documentId, childNode);
		}

		for (final UniqueID id : beanDir.getDocuments()) {
			BeanBasicDocument beanBasicDocument = CampaignClient.getInstance()
					.getBean(id);

			if (beanBasicDocument != null) {
				DocumentNode node = existingDocs.get(beanBasicDocument.getId()
						.toString());

				if (node != null) {
					existingDocs.remove(beanBasicDocument.getId().toString());
				} else {
					node = new DocumentNode(beanBasicDocument,
							beanBasicDocument.isDirectory());
					parent.add(node);
				}

				if (beanBasicDocument.isDirectory()) {
					fillIt(node);
				}
			} else {
				CampaignClient.getInstance().addWaitBeanListener(
						new WaitBeanListener() {
							@Override
							public UniqueID getBeanId() {
								return id;
							}

							@Override
							public void beanReceived(BasicBean bean) {
								refreshTree();
							}
						});
			}
		}

		if (!existingDocs.isEmpty()) {
			for (String docIdToRemove : existingDocs.keySet()) {
				Iterator<DefaultMutableTreeNode> itt = new DefaultMutableTreeNodeChildIterator(
						parent);

				boolean docRemoved = false;
				while (itt.hasNext() && !docRemoved) {
					DocumentNode childNode = (DocumentNode) itt.next();
					String documentId = childNode.getUserObject().getId()
							.toString();
					if (documentId.equals(docIdToRemove)) {
						itt.remove();
						docRemoved = true;
					}
				}
			}
		}
	}
}
