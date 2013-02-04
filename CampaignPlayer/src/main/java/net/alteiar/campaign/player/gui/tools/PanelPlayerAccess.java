package net.alteiar.campaign.player.gui.tools;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import net.alteiar.campaign.player.gui.tools.adapter.PlayerAdapter;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.server.shared.campaign.player.PlayerAccess;

public class PanelPlayerAccess extends PanelAlwaysValidOkCancel {
	private static final long serialVersionUID = 1L;

	private final ListDouble<PlayerAdapter> panel;

	private final JCheckBox checkBoxMj;

	private final JCheckBox checkBoxAll;

	public PanelPlayerAccess() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblMj = new JLabel("Acces MJ:");
		GridBagConstraints gbc_lblMj = new GridBagConstraints();
		gbc_lblMj.anchor = GridBagConstraints.EAST;
		gbc_lblMj.insets = new Insets(0, 0, 5, 5);
		gbc_lblMj.gridx = 0;
		gbc_lblMj.gridy = 0;
		add(lblMj, gbc_lblMj);

		checkBoxMj = new JCheckBox("");
		GridBagConstraints gbc_checkBoxMj = new GridBagConstraints();
		gbc_checkBoxMj.insets = new Insets(0, 0, 5, 5);
		gbc_checkBoxMj.gridx = 1;
		gbc_checkBoxMj.gridy = 0;
		add(checkBoxMj, gbc_checkBoxMj);

		JLabel lblAll = new JLabel("Acces pour Tous:");
		GridBagConstraints gbc_lblTous = new GridBagConstraints();
		gbc_lblTous.anchor = GridBagConstraints.EAST;
		gbc_lblTous.insets = new Insets(0, 0, 5, 5);
		gbc_lblTous.gridx = 0;
		gbc_lblTous.gridy = 1;
		add(lblAll, gbc_lblTous);

		checkBoxAll = new JCheckBox("");
		checkBoxAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkChanged();
			}
		});
		GridBagConstraints gbc_checkBoxTous = new GridBagConstraints();
		gbc_checkBoxTous.insets = new Insets(0, 0, 5, 5);
		gbc_checkBoxTous.gridx = 1;
		gbc_checkBoxTous.gridy = 1;
		add(checkBoxAll, gbc_checkBoxTous);

		panel = new ListDouble<PlayerAdapter>(
				PlayerAdapter.getAdapters(CampaignClient.INSTANCE
						.getAllPlayer()), "Tous les joueurs",
				"Les joueurs qui ont acces");
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		chkChanged();
	}

	protected void chkChanged() {
		this.panel.setEnabled(!this.checkBoxAll.isSelected());
	}

	public void setAccess(PlayerAccess access) {
		Boolean authAll = access.getAuthorizationAll();
		Boolean authMj = access.getAuthorizationMj();

		this.checkBoxMj.setSelected(authMj);
		this.checkBoxAll.setSelected(authAll);
		if (authAll) {
			this.panel.setEnabled(false);

		} else {
			this.panel.setEnabled(true);
			List<Long> authList = access.getAuthorizationList();

			this.panel
					.setSelectedData(PlayerAdapter
							.getAdapters(CampaignClient.INSTANCE
									.getAllPlayer(authList)));

		}
	}

	public PlayerAccess getPlayerAccess() {
		List<PlayerAdapter> lstPlayer = this.panel.getSelectedData();
		PlayerAccess access = new PlayerAccess(this.checkBoxMj.isSelected(),
				this.checkBoxAll.isSelected());

		if (!access.getAuthorizationAll()) {
			for (PlayerAdapter playerAdapter : lstPlayer) {
				access.addPlayer(playerAdapter.getObject().getId());
			}
		}

		return access;
	}
}
