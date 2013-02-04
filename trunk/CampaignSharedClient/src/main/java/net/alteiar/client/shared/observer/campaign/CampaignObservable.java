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
package net.alteiar.client.shared.observer.campaign;

import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.notes.NoteClient;
import net.alteiar.client.shared.campaign.player.IPlayerClient;
import net.alteiar.client.shared.campaign.player.PlayerClient;
import net.alteiar.client.shared.observer.ProxyClientObservable;
import net.alteiar.server.shared.campaign.IServerCampaign;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CampaignObservable extends ProxyClientObservable<IServerCampaign> {
	private static final long serialVersionUID = 1L;

	private static final Class<?> LISTENER = ICampaignObserver.class;

	public CampaignObservable(IServerCampaign remote) {
		super(remote);
	}

	public void addCampaignListener(ICampaignObserver listener) {
		this.addListener(LISTENER, listener);
	}

	public void removeCampaignListener(ICampaignObserver listener) {
		this.addListener(LISTENER, listener);
	}

	protected void notifyBattleAdded(IBattleClient remote) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).battleAdded(remote);
		}
	}

	protected void notifyBattleRemoved(IBattleClient remote) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).battleRemoved(remote);
		}
	}

	protected void notifyPlayerAdded(PlayerClient remote) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).playerAdded(remote);
		}
	}

	protected void notifyPlayerRemoved(IPlayerClient remote) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).playerRemoved(remote);
		}
	}

	protected void notifyNoteAdded(NoteClient remote) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).noteAdded(remote);
		}
	}

	protected void notifyNoteRemoved(NoteClient remote) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).noteRemoved(remote);
		}
	}

	protected void notifyCharacterAdded(ICharacterSheetClient client) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).characterAdded(client);
		}
	}

	protected void notifyCharacterRemoved(ICharacterSheetClient client) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).characterRemoved(client);
		}
	}

	protected void notifyMonsterAdded(ICharacterSheetClient client) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).monsterAdded(client);
		}
	}

	protected void notifyMonsterRemoved(ICharacterSheetClient client) {
		for (Object observer : this.getListener(LISTENER)) {
			((ICampaignObserver) observer).monsterRemoved(client);
		}
	}
}
