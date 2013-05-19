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
package net.alteiar.campaign.player.gui.connection;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.GlobalProperties;
import net.alteiar.campaign.player.Helpers;

public class PanelLoadGame extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;
	private static final int PREFERED_GAME_LIST_HEIGHT = 100;
	private static final int PREFERED_GAME_LIST_WIDTH = 200;

	private static final String PATH = "./ressources/sauvegarde/";

	JList<String> savedGameList;

	private DefaultComboBoxModel<String> model;
	private JComboBox<String> comboboxServerIp;
	private JTextField port;

	public PanelLoadGame(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		initGui();

	}

	@Override
	protected PanelStartGameDialog getNext() {
		return new PanelLoadingLoadCampaign(getDialog(), this);
	}

	private final void initGui() {

		GlobalProperties globalProp = Helpers.getGlobalProperties();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		model = new DefaultComboBoxModel<String>();
		comboboxServerIp = new JComboBox<String>(model);
		port = new JTextField(5);

		port.setText(globalProp.getLoadPort());

		JPanel server = new JPanel();
		server.setLayout(new BoxLayout(server, BoxLayout.Y_AXIS));
		server.setBorder(BorderFactory.createTitledBorder("Serveur"));

		List<String> allAdresses = getAddress();
		for (String address : allAdresses) {
			model.addElement(address);
		}
		if (allAdresses.contains(globalProp.getLoadIpLocal())) {
			model.setSelectedItem(globalProp.getLoadIpLocal());
		}

		JPanel serverAddressIpPanel = new JPanel(new FlowLayout());
		serverAddressIpPanel.add(new JLabel("Adresse ip"));
		serverAddressIpPanel.add(comboboxServerIp);

		JPanel portLabel = new JPanel(new FlowLayout());
		portLabel.add(new JLabel("Port"));
		portLabel.add(port);

		server.add(serverAddressIpPanel);
		server.add(portLabel);

		this.add(server);

		Vector<String> savedGames = getSavedGames();

		savedGameList = new JList<String>(savedGames);

		savedGameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		savedGameList.setLayoutOrientation(JList.VERTICAL);
		savedGameList.setSelectedIndex(0);

		JScrollPane listScroller = new JScrollPane(savedGameList);
		listScroller.setPreferredSize(new Dimension(PREFERED_GAME_LIST_WIDTH,
				PREFERED_GAME_LIST_HEIGHT));
		this.add(listScroller);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton createButton = new JButton("Charger");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO : is it the correct way to do this check
				// Je veux faire cette v\u00E9rification dans le cas ou
				// un player a cliquer sur load, puis sur annuler dans
				// le panel PanelChoosePlayer et finalement sur
				// annuler ici
				if (CampaignClient.getInstance() != null) {
					System.out.println("Leaving the game");
					CampaignClient.leaveGame();
				}
				load(PATH + PanelLoadGame.this.savedGameList.getSelectedValue());
			}
		});

		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO : is it the correct way to do this check
				// Je veux faire cette v\u00E9rification dans le cas ou
				// un player a cliquer sur load, puis sur annuler dans
				// le panel PanelChoosePlayer et finalement sur
				// annuler ici
				if (CampaignClient.getInstance() != null) {
					System.out.println("Leaving the game");
					CampaignClient.leaveGame();
				}
				previousState();
			}
		});
		buttonPanel.add(cancelButton);
		buttonPanel.add(createButton);

		this.add(buttonPanel);
	}

	public String getServerAddressIp() {
		return (String) comboboxServerIp.getSelectedItem();
	}

	public String getPort() {
		return port.getText();
	}

	public void load(final String campaign) {
		GlobalProperties globalProp = Helpers.getGlobalProperties();
		final String address = getServerAddressIp();
		final String port = getPort();

		Runnable run = new Runnable() {
			@Override
			public void run() {
				CampaignClient.loadCampaignServer(address, port,
						PanelStartGameDialog.GLOBAL_DOCUMENT_PATH, campaign);
			}
		};

		globalProp.setLoadPort(getPort());
		globalProp.setLoadIpServer(getServerAddressIp());

		Thread tr = new Thread(run);

		nextState();

		tr.start();
	}

	public Vector<String> getSavedGames() {

		Vector<String> savedGames = new Vector<String>();

		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
				savedGames.add(listOfFiles[i].getName());
			}
		}
		return savedGames;
	}
}
