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

import java.awt.Color;
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
import javax.swing.SwingUtilities;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignFactory;
import net.alteiar.campaign.player.infos.Helpers;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.infos.Languages;
import net.alteiar.campaign.player.tools.GlobalProperties;
import net.alteiar.component.MyCombobox;
import net.alteiar.panel.PanelSelectColor;

public class PanelCreateGame extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;

	private JTextField gameNameTextField;

	private JTextField pseudoTextField;
	private PanelSelectColor playerColorSelector;

	private MyCombobox<String> localIpComboBox;
	private JTextField portTextField;

	public PanelCreateGame(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);
		initGui();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gameNameTextField.requestFocusInWindow();
			}
		});
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return null;
	}

	private final void initGui() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Initialize all the component of the panel
		gameNameTextField = new JTextField(10);

		pseudoTextField = new JTextField(10);
		playerColorSelector = new PanelSelectColor();

		localIpComboBox = new MyCombobox<String>();
		portTextField = new JTextField(5);

		// Build inner panel

		// Game Info Panel
		JPanel gamePanel = new JPanel(new FlowLayout());
		gamePanel.setBorder(BorderFactory.createTitledBorder(Languages
				.getText("new_game")));
		JPanel gameNamePanel = new JPanel(new FlowLayout());
		gameNamePanel.add(new JLabel(Languages.getText("game_name")));
		gameNamePanel.add(gameNameTextField);
		gamePanel.add(gameNamePanel);

		// Player Info Panel
		JPanel identityPanel = new JPanel(new FlowLayout());
		identityPanel.setBorder(BorderFactory.createTitledBorder(Languages
				.getText("your_identity")));
		JPanel pseudo = new JPanel(new FlowLayout());
		pseudo.add(new JLabel(Languages.getText("nickname")));
		pseudo.add(pseudoTextField);
		identityPanel.add(pseudo);

		identityPanel.add(playerColorSelector);

		// Server Info Panel
		JPanel serverPanel = new JPanel();
		serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.Y_AXIS));
		serverPanel.setBorder(BorderFactory.createTitledBorder(Languages
				.getText("connexion")));

		List<String> allAdresses = getAddress();
		for (String address : allAdresses) {
			localIpComboBox.addItem(address);
		}

		JPanel localAddressIpPanel = new JPanel(new FlowLayout());
		localAddressIpPanel.add(new JLabel(Languages.getText("local_ip")));
		localAddressIpPanel.add(localIpComboBox);

		JPanel portLabel = new JPanel(new FlowLayout());
		portLabel.add(new JLabel(Languages.getText("port")));
		portLabel.add(portTextField);

		serverPanel.add(localAddressIpPanel);
		serverPanel.add(portLabel);

		// Button Panel
		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton createButton = new JButton(Languages.getText("connexion"));
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				create();
			}
		});

		JButton cancelButton = new JButton(Languages.getText("cancel"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				previousState();
			}
		});

		buttonPanel.add(cancelButton);
		buttonPanel.add(createButton);

		// Add all the panel to this panel
		this.add(gamePanel);
		this.add(identityPanel);
		this.add(serverPanel);
		this.add(buttonPanel);

		// Set default values with the values stocked in global properties
		GlobalProperties globalProp = Helpers.getGlobalProperties();
		pseudoTextField.setText(globalProp.getCreatePseudo());
		playerColorSelector.setColor(globalProp.getCreateColor());
		portTextField.setText(globalProp.getCreatePort());
		if (allAdresses.contains(globalProp.getCreateIpLocal())) {
			localIpComboBox.setSelectedItem(globalProp.getCreateIpLocal());
		}
	}

	public String getPseudo() {
		return pseudoTextField.getText();
	}

	public Color getPlayerColor() {
		return playerColorSelector.getColor();
	}

	public String getGameName() {
		return HelpersPath.PATH_SAVE + gameNameTextField.getText();
	}

	public String getLocalAdressIP() {
		return localIpComboBox.getSelectedItem();
	}

	public String getServerAddressIp() {
		return getLocalAdressIP();
	}

	public String getPort() {
		return portTextField.getText();
	}

	public void create() {
		GlobalProperties globalProp = Helpers.getGlobalProperties();
		globalProp.setCreateColor(getPlayerColor());
		globalProp.setCreatePseudo(getPseudo());
		globalProp.setCreateIpLocal(getLocalAdressIP());
		globalProp.setCreatePort(getPort());
		globalProp.save();

		// Create the game
		CampaignFactory.startNewCampaignServer(getServerAddressIp(), getPort(),
				HelpersPath.PATH_DOCUMENT_GLOBAL, getGameName());

		// Create one player NOTE: here we force the player who
		// creates the game to be the Game Master
		CampaignClient.getInstance().createPlayer(getPseudo(), true,
				getPlayerColor());

		nextState();
	}
}
