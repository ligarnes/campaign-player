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
package net.alteiar.server.document.map.battle;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.battle.character.CharacterCombatClient;
import net.alteiar.shared.ExceptionTool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BattleClient extends MapClient<IBattleRemote> {
	private static final long serialVersionUID = 1L;

	private transient BattleClientRemoteObserver battleListener;
	private Integer currentTurn;

	private final HashSet<Long> characters;

	public BattleClient(IBattleRemote battle) throws RemoteException {
		super(battle);
		currentTurn = getRemote().getCurrentTurn();

		characters = getRemote().getCharacterCombats();
	}

	public ArrayList<CharacterCombatClient> getCharacterCombats() {
		ArrayList<CharacterCombatClient> characters = new ArrayList<CharacterCombatClient>();

		synchronized (this.characters) {
			for (Long characterCombatId : this.characters) {
				characters.add((CharacterCombatClient) CampaignClient
						.getInstance().getDocument(characterCombatId));
			}
		}

		return characters;
	}

	public void addCharacterCombat(Long id) {
		try {
			this.getRemote().addCharacterCombat(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeCharacterCombat(Long id) {
		try {
			this.getRemote().removeCharacterCombat(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void nextTurn() {
		try {
			getRemote().nextTurn();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public Integer getCurrentTurn() {
		return currentTurn;
	}

	protected void setLocalCurrentTurn(Integer currentTurn) {
		this.currentTurn = currentTurn;
		// TODO this.notifyTurnChanged(this);
	}

	protected void addLocalCharacterCombat(Long characterCombatId) {
		synchronized (characters) {
			characters.add(characterCombatId);
		}
		// TODO notify
	}

	protected void removeLocalCharacterCombat(Long characterCombatId) {
		synchronized (characters) {
			characters.remove(characterCombatId);
		}
		// TODO notify
	}

	@Override
	protected void closeDocument() throws RemoteException {
		super.closeDocument();
		getRemote().removeBattleListener(battleListener);
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
		super.loadDocumentRemote();
		battleListener = new BattleClientRemoteObserver(getRemote());
	}

	/**
	 * this class should be observer and will use the notify of the Battle
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class BattleClientRemoteObserver extends UnicastRemoteObject
			implements IBattleListenerRemote {
		private static final long serialVersionUID = 1L;

		protected BattleClientRemoteObserver(IBattleRemote battle)
				throws RemoteException {
			super();
			battle.addBattleListener(this);
		}

		@Override
		public void nextTurn(Integer currentTurn) throws RemoteException {
			setLocalCurrentTurn(currentTurn);
		}

		@Override
		public void characterAdded(Long characterId) throws RemoteException {
			addLocalCharacterCombat(characterId);
		}

		@Override
		public void characterRemoved(Long characterId) throws RemoteException {
			removeCharacterCombat(characterId);
		}
	}
}
