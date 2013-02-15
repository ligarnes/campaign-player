package net.alteiar.campaign.player.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import net.alteiar.client.shared.campaign.CampaignClient;

public class PanelLoadCampaign extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JList listCampaign;

	public PanelLoadCampaign() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		File current = new File("./ressources/sauvegarde/");
		listCampaign = new JList(current.list());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		add(listCampaign, gbc_list);

		JButton btnCharger = new JButton("Charger");
		GridBagConstraints gbc_btnCharger = new GridBagConstraints();
		gbc_btnCharger.gridx = 0;
		gbc_btnCharger.gridy = 1;
		add(btnCharger, gbc_btnCharger);

		btnCharger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadCampaign();
			}
		});
	}

	public void loadCampaign() {
		String campaignName = (String) listCampaign.getSelectedValue();
		CampaignClient.INSTANCE.load(new File("./ressources/sauvegarde/"
				+ campaignName));

	}
}
