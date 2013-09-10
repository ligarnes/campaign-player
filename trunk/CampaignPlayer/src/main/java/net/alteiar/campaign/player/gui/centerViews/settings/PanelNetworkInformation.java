package net.alteiar.campaign.player.gui.centerViews.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.alteiar.campaign.CampaignClient;

public class PanelNetworkInformation extends PanelBaseSetting {
	private static final long serialVersionUID = 1L;

	private final JTextField textFieldServer;
	private final JTextField textFieldPort;

	public PanelNetworkInformation() {
		super("Informations réseaux");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblIpServer = new JLabel("Adresse ip du serveur:");
		GridBagConstraints gbc_lblIpServer = new GridBagConstraints();
		gbc_lblIpServer.anchor = GridBagConstraints.EAST;
		gbc_lblIpServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblIpServer.gridx = 0;
		gbc_lblIpServer.gridy = 0;
		add(lblIpServer, gbc_lblIpServer);

		textFieldServer = new JTextField();
		textFieldServer.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldServer.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textFieldServer, gbc_textField);
		textFieldServer.setColumns(10);

		JLabel lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 0, 5);
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 1;
		add(lblPort, gbc_lblPort);

		textFieldPort = new JTextField();
		textFieldPort.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPort.setEditable(false);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 1;
		add(textFieldPort, gbc_textField_2);
		textFieldPort.setColumns(10);

		// String localAddressIp = CampaignClient.getInstance().getIpLocal();
		String serverAddressIp = CampaignClient.getInstance().getIpServer();
		Integer port = CampaignClient.getInstance().getPort();

		// textFieldLocal.setText(localAddressIp);
		textFieldServer.setText(serverAddressIp);
		textFieldPort.setText(port.toString());
	}
}
