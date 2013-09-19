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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.CampaignListener;
import net.alteiar.campaign.player.gui.players.PlayerCellRenderer;
import net.alteiar.campaign.player.infos.Helpers;
import net.alteiar.campaign.player.infos.Languages;
import net.alteiar.component.MyList;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.player.Player;

public class PanelChoosePlayer extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;
	private static final int PREFERED_PLAYER_LIST_HEIGHT = 100;
	private static final int PREFERED_PLAYER_LIST_WIDTH = 200;

	public static List<Player> getAvaiblePlayers() {
		List<Player> allPlayers = CampaignClient.getInstance().getPlayers();
		Iterator<Player> itt = allPlayers.iterator();
		while (itt.hasNext()) {
			if (itt.next().getConnected()) {
				itt.remove();
			}
		}
		return allPlayers;
	}

	private MyList<Player> playerList;

	public PanelChoosePlayer(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		refreshGui();
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return null;
	}

	private final void refreshGui() {
		this.removeAll();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		List<Player> players = getAvaiblePlayers();

		playerList = new MyList<Player>(players);
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setLayoutOrientation(JList.VERTICAL);
		playerList.setCellRenderer(new PlayerCellRenderer());
		playerList.setSelectedIndex(0);

		JScrollPane listScroller = new JScrollPane(playerList);
		listScroller.setPreferredSize(new Dimension(PREFERED_PLAYER_LIST_WIDTH,
				PREFERED_PLAYER_LIST_HEIGHT));

		this.add(listScroller);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton chooseButton = new JButton(Languages.getText("select_player"));
		chooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				choosePlayer();
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
		buttonPanel.add(chooseButton);

		String playerName = Helpers.getGlobalProperties().getPlayerChoose();

		for (Player player : players) {
			if (playerName.equals(player.getName())) {
				playerList.setSelectedValue(player, true);
			}
		}

		this.add(buttonPanel);

		CampaignClient.getInstance().addCampaignListener(
				new CampaignListener() {
					@Override
					public void playerAdded(Player player) {
						refreshGui();
					}

					@Override
					public void beanRemoved(BeanBasicDocument bean) {
					}

					@Override
					public void beanAdded(BeanBasicDocument bean) {
					}
				});
	}

	private void choosePlayer() {
		Player player = playerList.getSelectedValue();
		CampaignClient.getInstance().selectPlayer(player);
		Helpers.getGlobalProperties().setPlayerChoose(player.getName());
		Helpers.getGlobalProperties().save();
		nextState();
	}
}
