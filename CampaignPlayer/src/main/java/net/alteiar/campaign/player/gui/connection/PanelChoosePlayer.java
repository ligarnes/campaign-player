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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import net.alteiar.CampaignClient;
import net.alteiar.player.Player;

public class PanelChoosePlayer extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int PREFERED_PLAYER_LIST_HEIGHT = 100;
	private static final int PREFERED_PLAYER_LIST_WIDTH = 200;

	JPanel previous;
	StartGameDialog startGameDialog;

	JList<Player> playerList;

	public PanelChoosePlayer(StartGameDialog startGameDialog, JPanel previous) {
		this.previous = previous;
		this.startGameDialog = startGameDialog;

		initGui();
	}

	private final void initGui() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		List<Player> playersName = getPlayers();
		// TODO : Here we should check whether the player is already
		// controlled by someone or not
		
		playerList = new JList<Player>(playersName.toArray(new Player[0]));

		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setLayoutOrientation(JList.VERTICAL);
		playerList.setCellRenderer(new PlayerCellRenderer());
		playerList.setSelectedIndex(0);

		JScrollPane listScroller = new JScrollPane(playerList);
		listScroller.setPreferredSize(new Dimension(PREFERED_PLAYER_LIST_WIDTH, PREFERED_PLAYER_LIST_HEIGHT));

		this.add(listScroller);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		JButton chooseButton = new JButton("Sélectionner");
		chooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				choosePlayer();
			}

		});
		buttonPanel.add(chooseButton);

		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				PanelChoosePlayer.this.startGameDialog.changeState(previous);
			}
		});
		buttonPanel.add(cancelButton);
		
		this.add(buttonPanel);
	}

	public List<Player> getPlayers() {
		return CampaignClient.getInstance().getPlayers();
	}

	private void choosePlayer() {
		CampaignClient.getInstance().selectPlayer(
				(Player) playerList.getSelectedValue());
		startGameDialog.startApplication();
	}
	
	static class PlayerCellRenderer extends JLabel implements ListCellRenderer<Player> {

		private static final long serialVersionUID = 1L;
		private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

		  public PlayerCellRenderer() {
		    setOpaque(true);
		  }

		public Component getListCellRendererComponent(JList<? extends Player> list, Player player,
		      int index, boolean isSelected, boolean cellHasFocus) {
		    
			String cellText = "";
			
			cellText += player.getName();
		    if (player.isMj()) {
		    	cellText += " (Maître du jeu)";
		    }
		    
		    setText(cellText);
		    if (isSelected) {
		      setBackground(HIGHLIGHT_COLOR);
		      setForeground(Color.white);
		    } else {
		      setBackground(Color.white);
		      setForeground(Color.black);
		    }
		    return this;
		}

	}
}
