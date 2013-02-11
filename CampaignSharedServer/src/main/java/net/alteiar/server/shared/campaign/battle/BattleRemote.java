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
package net.alteiar.server.shared.campaign.battle;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.ExceptionTool;
import net.alteiar.SerializableFile;
import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.observer.campaign.battle.BattleObservableRemote;
import net.alteiar.shared.tool.Randomizer;
import net.alteiar.shared.tool.SynchronizedHashMap;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BattleRemote extends BattleObservableRemote implements
		IBattleRemote {
	private static final long serialVersionUID = -8540822570181198244L;

	private Integer currentTurn;
	private Boolean initiativeEachTurn;

	private final String battleName;
	private final SynchronizedHashMap<Long, ICharacterCombatRemote> allCharacter;

	public BattleRemote(String battleName, SerializableFile background,
			Scale scale) throws RemoteException {
		super(background, scale);

		this.battleName = battleName;
		this.allCharacter = new SynchronizedHashMap<Long, ICharacterCombatRemote>();

		currentTurn = 0;
		initiativeEachTurn = false;
	}

	@Override
	public String getName() throws RemoteException {
		return battleName;
	}

	@Override
	public void addCharacter(Long id, Integer init, Point position)
			throws RemoteException {

		ICharacterRemote found = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getCharater(id);
		ICharacterCombatRemote combat = new CharacterCombatRemote(found);
		combat.setPosition(position);
		combat.setInitiative(init);
		allCharacter.put(combat.getId(), combat);

		this.notifyCharacterAdded(combat);
	}

	@Override
	public void addMonster(Long id, Integer init, Point position,
			Boolean isVisible) throws RemoteException {
		ICharacterRemote found = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getMonster(id);

		String orgName = found.getCharacterFacade().getName();
		int idx = 1;
		String name = orgName + idx;

		Boolean characterChanged = true;
		while (characterChanged) {
			characterChanged = false;
			for (ICharacterCombatRemote character : this.allCharacter.values()) {
				if (character.getCharacterSheet().getCharacterFacade()
						.getName().equals(name)) {
					idx++;
					name = orgName + idx;
					characterChanged = true;
					break;
				}
			}
		}
		ICharacterCombatRemote combat = new CharacterCombatRemote(
				found.fastCopy(name));
		combat.setVisibleForPlayer(isVisible);
		combat.setPosition(position);
		combat.setInitiative(init);
		allCharacter.put(combat.getId(), combat);

		this.notifyCharacterAdded(combat);
	}

	@Override
	public void removeCharacter(Long characterId) throws RemoteException {
		allCharacter.remove(characterId);
		this.notifyCharacterRemoved(characterId);
	}

	@Override
	public List<ICharacterCombatRemote> getAllCharacter()
			throws RemoteException {
		allCharacter.incCounter();
		ArrayList<ICharacterCombatRemote> characters = new ArrayList<ICharacterCombatRemote>(
				allCharacter.values());
		allCharacter.decCounter();

		return characters;
	}

	@Override
	public void setInitiativeEachTurn(Boolean isInitEachTurn)
			throws RemoteException {
		initiativeEachTurn = isInitEachTurn;
	}

	@Override
	public void nextTurn() throws RemoteException {
		if (initiativeEachTurn) {
			runInitForAllCharacter();
		}

		++currentTurn;
		notifyNextTurn(currentTurn);
	}

	@Override
	public Integer getCurrentTurn() throws RemoteException {
		return currentTurn;
	}

	private Integer rollInitiative() {
		return Randomizer.dice(20);
	}

	private void runInitForAllCharacter() {
		for (ICharacterCombatRemote character : allCharacter.values()) {
			try {
				character.setInitiative(rollInitiative());
			} catch (RemoteException e) {
				ExceptionTool.showError(e);
			}
		}
	}
}
