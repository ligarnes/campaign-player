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
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.chat.PanelChatFactory;
import net.alteiar.campaign.player.gui.dashboard.PanelListBattle;
import net.alteiar.campaign.player.gui.dashboard.PanelListSimpleCharacter;
import net.alteiar.campaign.player.gui.dashboard.PanelListSimpleMonster;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.notes.NoteClient;
import net.alteiar.client.shared.campaign.player.IPlayerClient;
import net.alteiar.client.shared.observer.campaign.ICampaignObserver;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelDashboard extends MyPanel implements ICampaignObserver {
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

	private final JPanel panelUsers;
	private final GridLayout gridLayoutUsers;

	public PanelDashboard() {
		super(new BorderLayout());
		panelUsers = new JPanel();
		panelUsers.setOpaque(false);
		gridLayoutUsers = new GridLayout(2, 2, 5, 5);

		titleName = new JLabel("Nom");
		titleStatus = new JLabel("status");
		titleName.setFont(TITLE_FONT);
		titleStatus.setFont(TITLE_FONT);

		labelMJ = new JLabel("MJ");
		labelPJ = new JLabel("Joueur");
		labelMJ.setFont(TEXT_FONT);
		labelPJ.setFont(TEXT_FONT);

		initPanelUser();
		CampaignClient.INSTANCE.addCampaignListener(this);

		JPanel center = new JPanel();
		center.setOpaque(false);
		// center.add(panelUsers);
		center.add(new PanelListSimpleCharacter());

		if (CampaignClient.INSTANCE.getCurrentPlayer().isMj()) {
			center.add(new PanelListSimpleMonster());
		}
		center.add(new PanelListBattle());

		this.add(PanelChatFactory.buildChatLarge(), BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		refreshPlayer();
	}

	private void initPanelUser() {
		panelUsers.setBorder(BorderFactory.createTitledBorder("Utilisateurs"));
		panelUsers.setLayout(gridLayoutUsers);
		refreshPlayer();
	}

	private void refreshPlayer() {
		panelUsers.removeAll();

		Collection<IPlayerClient> clients = CampaignClient.INSTANCE
				.getAllPlayer();

		gridLayoutUsers.setRows(clients.size() + 1);

		panelUsers.add(titleName);
		panelUsers.add(titleStatus);
		for (IPlayerClient player : clients) {
			JLabel name = new JLabel(player.getName());
			name.setFont(TEXT_FONT);

			panelUsers.add(name);
			if (player.isMj()) {
				panelUsers.add(labelMJ);
			} else {
				panelUsers.add(labelPJ);
			}
		}

		this.revalidate();
	}

	@Override
	public void playerAdded(IPlayerClient player) {
		refreshPlayer();
	}

	@Override
	public void playerRemoved(IPlayerClient player) {
		refreshPlayer();
	}

	// ///////////////////////USELESS METHODS///////////////////
	@Override
	public void battleAdded(IBattleClient battle) {
	}

	@Override
	public void battleRemoved(IBattleClient battle) {
	}

	@Override
	public void noteAdded(NoteClient note) {
	}

	@Override
	public void noteRemoved(NoteClient note) {
	}

	@Override
	public void characterAdded(ICharacterSheetClient note) {
	}

	@Override
	public void characterRemoved(ICharacterSheetClient note) {
	}

	@Override
	public void monsterAdded(ICharacterSheetClient note) {
	}

	@Override
	public void monsterRemoved(ICharacterSheetClient note) {
	}
}
