package net.alteiar.campaign.player.gui.centerViews.explorer.mine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.players.PlayerCellRenderer;
import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.list.MyDoubleList;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;

public class PanelPropertyEditor extends JPanel implements PanelOkCancel {

	private static final long serialVersionUID = 1L;

	private final JLabel lblIcon;
	private final JLabel lblDocumentname;

	private final JTextField textField;
	private final JCheckBox chckbxPublic;

	private final JComboBox<Player> comboBoxProp;
	private final MyDoubleList<Player> playersEditors;
	private final MyDoubleList<Player> playersUsers;

	private final DefaultComboBoxModel<Player> comboboxModel;

	public PanelPropertyEditor(BeanBasicDocument doc) {
		this();
		setDocument(doc);
	}

	public PanelPropertyEditor() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 13, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblIcon = new JLabel("");
		lblIcon.setPreferredSize(new Dimension(64, 64));
		lblIcon.setMinimumSize(new Dimension(64, 64));
		lblIcon.setMaximumSize(new Dimension(64, 64));
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.gridheight = 3;
		gbc_lblIcon.insets = new Insets(0, 0, 5, 5);
		gbc_lblIcon.gridx = 0;
		gbc_lblIcon.gridy = 0;
		add(lblIcon, gbc_lblIcon);

		lblDocumentname = new JLabel("DocumentName");
		lblDocumentname.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblDocumentname = new GridBagConstraints();
		gbc_lblDocumentname.gridwidth = 2;
		gbc_lblDocumentname.gridheight = 2;
		gbc_lblDocumentname.insets = new Insets(0, 0, 5, 5);
		gbc_lblDocumentname.gridx = 1;
		gbc_lblDocumentname.gridy = 0;
		add(lblDocumentname, gbc_lblDocumentname);

		JLabel lblRenommer = new JLabel("Renommer:");
		GridBagConstraints gbc_lblRenommer = new GridBagConstraints();
		gbc_lblRenommer.anchor = GridBagConstraints.EAST;
		gbc_lblRenommer.insets = new Insets(0, 0, 5, 5);
		gbc_lblRenommer.gridx = 0;
		gbc_lblRenommer.gridy = 3;
		add(lblRenommer, gbc_lblRenommer);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		add(textField, gbc_textField);
		textField.setColumns(20);

		JPanel panelShared = new JPanel();
		panelShared.setBorder(new TitledBorder(null,
				"Param\u00E8tre de partage", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelShared = new GridBagConstraints();
		gbc_panelShared.insets = new Insets(0, 0, 0, 5);
		gbc_panelShared.gridwidth = 3;
		gbc_panelShared.fill = GridBagConstraints.BOTH;
		gbc_panelShared.gridx = 0;
		gbc_panelShared.gridy = 4;
		add(panelShared, gbc_panelShared);
		GridBagLayout gbl_panelShared = new GridBagLayout();
		gbl_panelShared.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panelShared.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelShared.columnWeights = new double[] { 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panelShared.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panelShared.setLayout(gbl_panelShared);

		chckbxPublic = new JCheckBox("partager avec tout le monde");
		GridBagConstraints gbc_chckbxPublic = new GridBagConstraints();
		gbc_chckbxPublic.gridwidth = 2;
		gbc_chckbxPublic.anchor = GridBagConstraints.WEST;
		gbc_chckbxPublic.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPublic.gridx = 0;
		gbc_chckbxPublic.gridy = 0;
		panelShared.add(chckbxPublic, gbc_chckbxPublic);

		JLabel lblProp = new JLabel("Propriétaire:");
		GridBagConstraints gbc_lblProp = new GridBagConstraints();
		gbc_lblProp.anchor = GridBagConstraints.EAST;
		gbc_lblProp.insets = new Insets(0, 0, 5, 5);
		gbc_lblProp.gridx = 0;
		gbc_lblProp.gridy = 1;
		panelShared.add(lblProp, gbc_lblProp);

		comboBoxProp = new JComboBox<>();
		comboBoxProp.setRenderer(new PlayerCellRenderer());
		comboboxModel = new DefaultComboBoxModel<Player>();
		comboBoxProp.setModel(comboboxModel);
		GridBagConstraints gbc_comboBoxProp = new GridBagConstraints();
		gbc_comboBoxProp.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxProp.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxProp.gridx = 1;
		gbc_comboBoxProp.gridy = 1;
		panelShared.add(comboBoxProp, gbc_comboBoxProp);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		panelShared.add(panel, gbc_panel);

		playersEditors = new MyDoubleList<>("Interdit", "Authoriser");
		playersEditors.setCellRenderer(new PlayerCellRenderer());
		playersEditors.setBorder(BorderFactory
				.createTitledBorder("Édition du document"));
		panel.add(playersEditors);

		playersUsers = new MyDoubleList<>("Interdit", "Authoriser");
		playersUsers.setCellRenderer(new PlayerCellRenderer());
		playersUsers.setBorder(BorderFactory
				.createTitledBorder("Vision du document"));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		panelShared.add(playersUsers, gbc_panel_1);

		chckbxPublic.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				playersUsers.setEnable(!chckbxPublic.isSelected());
				playersEditors.setEnable(!chckbxPublic.isSelected());
			}
		});
	}

	public void setDocument(BeanBasicDocument doc) {
		playersEditors.clear();

		comboboxModel.removeAllElements();
		List<Player> players = CampaignClient.getInstance().getPlayers();
		for (Player player : players) {
			comboboxModel.addElement(player);
		}

		playersEditors.setDatas(players);
		playersUsers.setDatas(players);

		lblIcon.setIcon(ExplorerIconUtils.getIcon(doc, 64, 64));
		lblDocumentname.setText(doc.getDocumentName());

		comboBoxProp.setSelectedItem(doc.getOwner());

		textField.setText(doc.getDocumentName());
		chckbxPublic.setSelected(doc.getPublic());

		doc.getUsers();
		for (UniqueID user : doc.getUsers()) {
			Player player = CampaignClient.getInstance().getBean(user);
			playersUsers.selectData(player);
		}

		for (UniqueID user : doc.getModifiers()) {
			Player player = CampaignClient.getInstance().getBean(user);
			playersEditors.selectData(player);
		}

		playersUsers.setEnable(!chckbxPublic.isSelected());
		playersEditors.setEnable(!chckbxPublic.isSelected());
	}

	public void applyChangeOnBean(BeanBasicDocument doc) {
		doc.setDocumentName(textField.getText());

		doc.setPublic(chckbxPublic.isSelected());

		if (!chckbxPublic.isSelected()) {
			for (Player player : playersUsers.getTargetDatas()) {
				doc.addUser(player.getId());
			}

			for (Player player : playersEditors.getTargetDatas()) {
				doc.addModifier(player.getId());
			}

			for (Player player : playersUsers.getSourceDatas()) {
				doc.removeUser(player.getId());
			}

			for (Player player : playersEditors.getSourceDatas()) {
				doc.removeModifier(player.getId());
			}
		}
	}

	@Override
	public Boolean isDataValid() {
		return !textField.getText().isEmpty();
	}

	@Override
	public String getInvalidMessage() {
		return "Le nom du document ne peut pas être vide";
	}

}
