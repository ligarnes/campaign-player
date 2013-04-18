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
package net.alteiar.campaign.player.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.alteiar.CampaignClient;
import net.alteiar.player.Player;

public class PanelChoosePlayer extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String PATH = "./ressources/sauvegarde/";

	JPanel previous;

	MainPanelStartGame mainPanelStartGame;

	JList<Object> playerList;

	public PanelChoosePlayer(MainPanelStartGame mainPanelStartGame,
			JPanel previous) {
		this.previous = previous;
		this.mainPanelStartGame = mainPanelStartGame;

		initGui();

	}

	private final void initGui() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		List<Player> playersName = getPlayersName();

		playerList = new JList<Object>(playersName.toArray());

		playerList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		playerList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		playerList.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(playerList);
		listScroller.setPreferredSize(new Dimension(250, 80));

		this.add(listScroller);

		JButton createButton = new JButton("Sélectionner");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				choosePlayer();
			}

		});
		this.add(createButton);

		JButton loadButton = new JButton("Annuler");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelChoosePlayer.this.mainPanelStartGame.changeState(previous);
			}
		});
		this.add(loadButton);
	}

	public List<Player> getPlayersName() {
		return CampaignClient.getInstance().getPlayers();
	}

	private void choosePlayer() {
		CampaignClient.getInstance().selectPlayer(
				(Player) playerList.getSelectedValue());
		mainPanelStartGame.startApplication();
	}
}
