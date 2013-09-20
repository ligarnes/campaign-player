package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.DocumentNode;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.MyTreeCellRenderer;
import net.alteiar.campaign.player.gui.centerViews.explorer.mine.dnd.TreeTransferHandler;
import net.alteiar.campaign.player.gui.documents.PanelCreateDocument;
import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.documents.BeanDirectory;

import org.apache.log4j.Logger;

public class PanelDocumentExplorer extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String ICON_SAVE = "save.png";

	private final JTree tree;

	public PanelDocumentExplorer() {
		this.setLayout(new BorderLayout());

		BeanDirectory root = CampaignClient.getInstance().getRootDirectory();
		DocumentNode all = new DocumentNode(root, root.isDirectory());

		MyTreeModel treeModel = new MyTreeModel(all);
		tree = new JTree();
		tree.setModel(treeModel);
		tree.setRootVisible(false);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		tree.setCellRenderer(new MyTreeCellRenderer());

		tree.setRowHeight(38);

		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON_OR_INSERT);
		tree.setTransferHandler(new TreeTransferHandler());

		JScrollPane treeView = new JScrollPane(tree);
		this.add(treeView, BorderLayout.CENTER);

		this.add(createPanelCreate(), BorderLayout.SOUTH);

		tree.addMouseListener(new DocumentExplorerListener(tree));

		this.setMinimumSize(new Dimension(300, 300));
		this.setPreferredSize(new Dimension(300, 300));
	}

	protected JPanel createPanelCreate() {
		JPanel pane = new JPanel();
		JButton btnAdd = new JButton("Ajouter");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelCreateDocument.createDocument(CampaignClient.getInstance()
						.getRootDirectory());
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
}
