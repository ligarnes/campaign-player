/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
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

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.alteiar.campaign.player.gui.documents.PanelDocumentManager;
import net.alteiar.campaign.player.gui.players.PanelViewPlayers;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelDashboard extends MyPanel {
	private static final long serialVersionUID = 1L;

	private static Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);

	private static Font TEXT_FONT = new Font("SansSerif", Font.PLAIN, 14);

	// nice font for fantasy
	// private static Font PLAYER_FONT = new Font("French Script MT",
	// Font.PLAIN,
	// 32);

	private final JLabel titleName;
	private final JLabel titleStatus;

	private final JLabel labelMJ;
	private final JLabel labelPJ;

	public PanelDashboard() {
		super(new BorderLayout());

		titleName = new JLabel("Nom");
		titleStatus = new JLabel("status");
		titleName.setFont(TITLE_FONT);
		titleStatus.setFont(TITLE_FONT);

		labelMJ = new JLabel("MJ");
		labelPJ = new JLabel("Joueur");
		labelMJ.setFont(TEXT_FONT);
		labelPJ.setFont(TEXT_FONT);

		JPanel center = new JPanel();
		center.setOpaque(false);

		/*
		 * center.add(new PanelListSimpleCharacter());
		 * 
		 * if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
		 * center.add(new PanelListSimpleMonster()); }
		 */
		// center.add(new PanelListBattle());
		center.add(new PanelViewPlayers());
		center.add(new PanelDocumentManager());

		JScrollPane scroll = new JScrollPane(new PanelWest());
		this.add(scroll, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
	}

}
