package net.alteiar.campaign.player.gui.centerViews.settings;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.panel.PanelSelectColor;

public class PanelPlayerSettings extends PanelBaseSetting {
	private static final long serialVersionUID = 1L;

	private final PanelSelectColor btnColor;

	// Player color
	// Player name
	// ...

	public PanelPlayerSettings() {
		super("Informations joueur");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblPseudo = new JLabel("Pseudo");
		GridBagConstraints gbc_lblPseudo = new GridBagConstraints();
		gbc_lblPseudo.anchor = GridBagConstraints.EAST;
		gbc_lblPseudo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPseudo.gridx = 0;
		gbc_lblPseudo.gridy = 0;
		add(lblPseudo, gbc_lblPseudo);

		JTextField txtPseudo = new JTextField();
		txtPseudo.setHorizontalAlignment(SwingConstants.CENTER);
		txtPseudo.setEditable(false);
		GridBagConstraints gbc_txtPseudo = new GridBagConstraints();
		gbc_txtPseudo.insets = new Insets(0, 0, 5, 0);
		gbc_txtPseudo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPseudo.gridx = 1;
		gbc_txtPseudo.gridy = 0;
		add(txtPseudo, gbc_txtPseudo);
		txtPseudo.setColumns(10);

		JLabel lblCouleur = new JLabel("Couleur:");
		GridBagConstraints gbc_lblCouleur = new GridBagConstraints();
		gbc_lblCouleur.anchor = GridBagConstraints.EAST;
		gbc_lblCouleur.insets = new Insets(0, 0, 5, 5);
		gbc_lblCouleur.gridx = 0;
		gbc_lblCouleur.gridy = 1;
		add(lblCouleur, gbc_lblCouleur);

		btnColor = new PanelSelectColor();
		GridBagConstraints gbc_btnColor = new GridBagConstraints();
		gbc_btnColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnColor.gridx = 1;
		gbc_btnColor.gridy = 1;
		add(btnColor, gbc_btnColor);

		JLabel lblMj = new JLabel("Mj:");
		GridBagConstraints gbc_lblMj = new GridBagConstraints();
		gbc_lblMj.insets = new Insets(0, 0, 0, 5);
		gbc_lblMj.anchor = GridBagConstraints.EAST;
		gbc_lblMj.gridx = 0;
		gbc_lblMj.gridy = 2;
		add(lblMj, gbc_lblMj);

		JCheckBox chckbxDm = new JCheckBox("");
		chckbxDm.setEnabled(false);
		GridBagConstraints gbc_chckbxDm = new GridBagConstraints();
		gbc_chckbxDm.gridx = 1;
		gbc_chckbxDm.gridy = 2;
		add(chckbxDm, gbc_chckbxDm);

		String pseudo = CampaignClient.getInstance().getCurrentPlayer()
				.getName();
		Color color = CampaignClient.getInstance().getCurrentPlayer()
				.getColor();
		Boolean isDm = CampaignClient.getInstance().getCurrentPlayer().isDm();

		txtPseudo.setText(pseudo);
		btnColor.setColor(color);
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				colorChanged();
			}
		});
		chckbxDm.setSelected(isDm);
	}

	protected void colorChanged() {
		CampaignClient.getInstance().getCurrentPlayer()
				.setColor(btnColor.getColor());
	}
}
