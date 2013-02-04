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
package net.alteiar.campaign.player.gui.notes;

import javax.swing.JTabbedPane;

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
public class TabbedPaneListAllNotes extends JTabbedPane implements
		ICampaignObserver {

	private static final long serialVersionUID = -5183267274885482007L;

	public TabbedPaneListAllNotes() {
		super();
		CampaignClient.INSTANCE.addCampaignListener(this);

		// Add Main Menu
		this.addTab("Accueil", new PanelListAllNotes());

		// Add existing notes
		CampaignClient campaign = CampaignClient.INSTANCE;

		for (NoteClient note : campaign.getAllNotes()) {
			this.addTab(note.getTitle(), new PanelNote(note));
		}
	}

	@Override
	public void noteAdded(NoteClient note) {
		this.addTab(note.getTitle(), new PanelNote(note));
	}

	@Override
	public void noteRemoved(NoteClient note) {
		this.removeTabAt(indexOf(note));
	}

	/*
	public NoteClient getSelectedNote() {
		NoteClient battle = null;
		int battleIdx = this.getSelectedIndex() - 1;
		if (battleIdx >= 0) {
			battle = CampaignClient.INSTANCE.getBattle(battleIdx);
		}
		return battle;
	}*/

	private int indexOf(NoteClient note) {
		int idx = 0;
		String noteTitle = note.getTitle();
		for (int i = 0; i < this.getTabCount(); ++i) {
			if (noteTitle.equals(this.getTitleAt(i))) {
				idx = i;
				break;
			}
		}

		return idx;
	}

	// ///////////////////////USELESS METHODS///////////////////

	@Override
	public void playerAdded(IPlayerClient player) {
	}

	@Override
	public void playerRemoved(IPlayerClient player) {
	}

	@Override
	public void battleAdded(IBattleClient battle) {
	}

	@Override
	public void battleRemoved(IBattleClient battle) {
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
