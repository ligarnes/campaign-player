package net.alteiar.campaign.player.gui.centerViews.explorer.mine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.players.PlayerCellRenderer;
import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.list.MyDoubleList;

public class PanelPropertyEditor extends JPanel implements PanelOkCancel {

	private static final long serialVersionUID = 1L;

	private final JLabel lblIcon;
	private final JLabel lblDocumentname;

	private final JTextField textField;
	private final JCheckBox chckbxPublic;

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
		gbl_panelShared.columnWidths = new int[] { 0, 0 };
		gbl_panelShared.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelShared.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelShared.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelShared.setLayout(gbl_panelShared);

		chckbxPublic = new JCheckBox("partager avec tout le monde");
		GridBagConstraints gbc_chckbxPublic = new GridBagConstraints();
		gbc_chckbxPublic.anchor = GridBagConstraints.WEST;
		gbc_chckbxPublic.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxPublic.gridx = 0;
		gbc_chckbxPublic.gridy = 0;
		panelShared.add(chckbxPublic, gbc_chckbxPublic);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		panelShared.add(panel, gbc_panel);

		MyDoubleList<net.alteiar.player.Player> players = new MyDoubleList<>(
				CampaignClient.getInstance().getPlayers());
		players.setCellRenderer(new PlayerCellRenderer());
		panel.add(players);
	}

	public void setDocument(BeanBasicDocument doc) {
		lblIcon.setIcon(ExplorerIconUtils.getIcon(doc, 64, 64));
		lblDocumentname.setText(doc.getDocumentName());

		textField.setText(doc.getDocumentName());
		chckbxPublic.setSelected(doc.getPublic());
	}

	public String getDocumentName() {
		return textField.getText();
	}

	public boolean isPublic() {
		return chckbxPublic.isSelected();
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
