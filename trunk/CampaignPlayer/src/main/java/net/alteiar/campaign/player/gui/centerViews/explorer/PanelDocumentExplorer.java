package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
import net.alteiar.campaign.player.gui.documents.PanelCreateDocument;
import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDirectory;
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
		DefaultMutableTreeNode all = new DefaultMutableTreeNode(
				new DocumentAdapter(root), root.isDirectory());

		treeModel = new DefaultTreeModel(all, true);
		tree = new JTree();
		tree.setModel(treeModel);
		tree.setRootVisible(false);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

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
				DocumentAdapter adapter = (DocumentAdapter) dirNode
						.getUserObject();
				PanelCreateDocument.createDocument((BeanDirectory) adapter
						.getDocument());
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
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel
						.getRoot();

				System.out.println("fill it");
				fillIt(root);

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

	private void fillIt(final DefaultMutableTreeNode parent) {
		DocumentAdapter dir = (DocumentAdapter) parent.getUserObject();

		BeanDirectory beanDir = (BeanDirectory) dir.getDocument();

		beanDir.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				refreshTree();
			}
		});

		ArrayList<BeanBasicDocument> docs = new ArrayList<>();
		for (final UniqueID id : beanDir.getDocuments()) {
			BeanBasicDocument beanBasicDocument = CampaignClient.getInstance()
					.getBean(id);

			if (beanBasicDocument != null) {
				docs.add(beanBasicDocument);

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

		if (docs.isEmpty()) {
			parent.removeAllChildren();
			return;
		}

		Collections.sort(docs, new DocumentNameComparator());

		int childIdx = 0;
		for (int i = 0; i < docs.size(); ++i) {
			DefaultMutableTreeNode currentTreeNode = null;

			if (parent.getChildCount() > childIdx) {
				currentTreeNode = (DefaultMutableTreeNode) parent
						.getChildAt(childIdx);
				String docName = ((DocumentAdapter) currentTreeNode
						.getUserObject()).getDocument().getDocumentName();

				String currentDocName = docs.get(i).getDocumentName();
				if (docName.equals(currentDocName)) {
					// already there
					childIdx++;
				} else if (docName.compareToIgnoreCase(currentDocName) < 0) {
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(
							new DocumentAdapter(docs.get(i)), docs.get(i)
									.isDirectory());
					this.treeModel.insertNodeInto(node, parent, i);
				} else {
					System.out.println("node removed");
					// the node as been removed
					// FIXME, TODO TO TEST
					this.treeModel.removeNodeFromParent(currentTreeNode);
					i--;
				}
			} else {
				// just append the node
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(
						new DocumentAdapter(docs.get(i)), docs.get(i)
								.isDirectory());
				this.treeModel.insertNodeInto(node, parent, i);
			}

			// if is directory we need to fillIt
			if (docs.get(i).isDirectory()) {
				fillIt((DefaultMutableTreeNode) parent.getChildAt(i));
			}
		}
	}

	private class DocumentNameComparator implements
			Comparator<BeanBasicDocument> {

		@Override
		public int compare(BeanBasicDocument o1, BeanBasicDocument o2) {
			return o1.getDocumentName().compareToIgnoreCase(
					o2.getDocumentName());
		}
	}
}
