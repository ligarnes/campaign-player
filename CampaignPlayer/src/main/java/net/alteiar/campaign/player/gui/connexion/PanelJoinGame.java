/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg. All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.campaign.player.gui.connexion;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.campaign.CampaignFactoryNew;
import net.alteiar.campaign.player.infos.Helpers;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.infos.Languages;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.campaign.player.tools.GlobalProperties;
import net.alteiar.campaign.player.tools.NetworkProperties;
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.component.MyCombobox;
import net.alteiar.thread.MyRunnable;

public class PanelJoinGame extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;

	private MyCombobox<String> comboboxLocalIp;
	private JTextField addressIpServer;
	private JTextField port;

	public PanelJoinGame(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		initGui();
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return new PanelLoading(getDialog(), this,
				new PanelCreateOrChoosePlayer(getDialog()));
	}

	private final void initGui() {
		GlobalProperties globalProp = Helpers.getGlobalProperties();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		comboboxLocalIp = new MyCombobox<String>();
		addressIpServer = new JTextField(10);
		port = new JTextField(5);

		addressIpServer.setText(globalProp.getJoinIpServer());
		port.setText(globalProp.getJoinPort());

		JPanel server = new JPanel();
		server.setLayout(new BoxLayout(server, BoxLayout.Y_AXIS));
		server.setBorder(BorderFactory.createTitledBorder(Languages
				.getText("connexion")));

		List<String> allAdresses = getAddress();
		for (String address : allAdresses) {
			comboboxLocalIp.addItem(address);
		}
		if (allAdresses.contains(globalProp.getJoinIpLocal())) {
			comboboxLocalIp.setSelectedItem(globalProp.getJoinIpLocal());
		}

		JPanel localAddressIpPanel = new JPanel(new FlowLayout());
		localAddressIpPanel.add(new JLabel(Languages.getText("local_ip")));
		localAddressIpPanel.add(comboboxLocalIp);

		JPanel addressIpPanel = new JPanel(new FlowLayout());
		addressIpPanel.add(new JLabel(Languages.getText("serveur_ip")));
		addressIpPanel.add(addressIpServer);

		JPanel portLabel = new JPanel(new FlowLayout());
		portLabel.add(new JLabel(Languages.getText("port")));
		portLabel.add(port);

		server.add(localAddressIpPanel);
		server.add(addressIpPanel);
		server.add(portLabel);

		this.add(server);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton joinButton = new JButton(Languages.getText("join"));
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				join();
			}
		});

		JButton cancelButton = new JButton(Languages.getText("cancel"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				previousState();
			}
		});
		buttonPanel.add(cancelButton);
		buttonPanel.add(joinButton);

		this.add(buttonPanel);

	}

	public String getLocalAdressIP() {
		return comboboxLocalIp.getSelectedItem();
	}

	public String getServerAddressIp() {
		return addressIpServer.getText();
	}

	public String getPort() {
		return port.getText();
	}

	public void join() {
		Threads.execute(new MyRunnable() {
			@Override
			public void run() {
				NetworkProperties networkProp = new NetworkProperties();
				CampaignFactoryNew.connectToServer(networkProp.getServerIp(),
						networkProp.getServerPort(),
						HelpersPath.PATH_DOCUMENT_GLOBAL, PluginSystem
								.getInstance().getKryo());
			}

			@Override
			public String getTaskName() {
				return "Connect to server";
			}
		});

		nextState();
	}
}
