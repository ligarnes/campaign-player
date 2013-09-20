package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import net.alteiar.WaitBeanListener;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignListener;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.DocumentNode;
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.documents.DocumentAdapter;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;

public class MyTreeModel extends DocumentAdapter implements TreeModel {

	private final DefaultTreeModel fullModel;
	private final DefaultTreeModel printModel;

	/**
	 * Avoid multiple concurrent refresh
	 */
	private boolean needRefresh = false;
	private boolean isRefreshing = false;

	public MyTreeModel(DocumentNode root) {
		fullModel = new DefaultTreeModel(root);

		printModel = new DefaultTreeModel(root.clone());

		fullModel.addTreeModelListener(new TreeModelListener() {

			@Override
			public void treeStructureChanged(TreeModelEvent e) {
				refreshModel();
			}

			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				refreshModel();
			}

			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				refreshModel();
			}

			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				refreshModel();
			}
		});

		CampaignClient.getInstance().addCampaignListener(
				new CampaignListener() {

					@Override
					public void playerAdded(Player player) {

					}

					@Override
					public void beanRemoved(BeanBasicDocument bean) {
						bean.addPropertyChangeListener(MyTreeModel.this);
						refreshTree();
					}

					@Override
					public void beanAdded(BeanBasicDocument bean) {
						refreshTree();
					}
				});

		refreshTree();
	}

	@Override
	public void documentChanged(PropertyChangeEvent evt) {
		refreshModel();
	}

	private synchronized void refreshTree() {
		if (isRefreshing) {
			needRefresh = true;
			return;
		}
		needRefresh = false;
		isRefreshing = true;

		Threads.execute(new MyRunnable() {
			@Override
			public void run() {
				DocumentNode root = (DocumentNode) fullModel.getRoot();
				fillCompleteModel(root);
				fullModel.reload();
				isRefreshing = false;
				if (needRefresh) {
					refreshTree();
				}
			}

			@Override
			public String getTaskName() {
				return "refresh document tree";
			}
		});
	}

	private synchronized void fillCompleteModel(final DocumentNode parent) {
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
					fillCompleteModel(node);
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

	private synchronized void refreshModel() {
		DocumentNode root = (DocumentNode) fullModel.getRoot();

		DocumentNode rootNode = (DocumentNode) printModel.getRoot();
		rootNode.removeAllChildren();

		Player current = CampaignClient.getInstance().getCurrentPlayer();
		fillPrintNode(current, rootNode, root);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				reload();
			}
		});
	}

	private synchronized void reload() {
		printModel.reload();
	}

	private void fillPrintNode(Player current, DocumentNode root,
			DocumentNode realNode) {
		for (int i = 0; i < realNode.getChildCount(); i++) {
			DocumentNode node = (DocumentNode) realNode.getChildAt(i);
			DocumentNode newNode = node.clone();
			if (node.getUserObject().isAllowedToSee(current)) {
				root.add(newNode);
				if (!node.isLeaf()) {
					fillPrintNode(current, newNode, node);
				}
			}
		}
	}

	@Override
	public Object getRoot() {
		return printModel.getRoot();
	}

	@Override
	public Object getChild(Object parent, int index) {
		return printModel.getChild(parent, index);
	}

	@Override
	public int getChildCount(Object parent) {
		return printModel.getChildCount(parent);
	}

	@Override
	public boolean isLeaf(Object node) {
		return printModel.isLeaf(node);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		printModel.valueForPathChanged(path, newValue);
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return printModel.getIndexOfChild(parent, child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		printModel.addTreeModelListener(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		printModel.removeTreeModelListener(l);
	}

}
