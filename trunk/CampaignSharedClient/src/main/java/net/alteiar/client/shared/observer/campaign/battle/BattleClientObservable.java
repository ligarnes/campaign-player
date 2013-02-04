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
package net.alteiar.client.shared.observer.campaign.battle;

import net.alteiar.client.shared.campaign.battle.BattleClient;
import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.campaign.map.Map2DClient;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class BattleClientObservable extends Map2DClient<IBattleRemote> {

	private static final long serialVersionUID = 1L;

	public BattleClientObservable(IBattleRemote remote) {
		super(remote);
	}

	public void addBattleListener(IBattleObserver listener) {
		this.addListener(IBattleObserver.class, listener);
	}

	public void removeBattleListener(IBattleObserver listener) {
		this.removeListener(IBattleObserver.class, listener);
	}

	protected void notifyCharacterAdded(BattleClient battle,
			ICharacterCombatClient character) {
		for (Object obs : this.getListener(IBattleObserver.class)) {
			((IBattleObserver) obs).characterAdded(battle, character);
		}
	}

	protected void notifyCharacterRemove(BattleClient battle,
			ICharacterCombatClient character) {
		for (Object obs : this.getListener(IBattleObserver.class)) {
			((IBattleObserver) obs).characterRemove(battle, character);
		}
	}

	protected void notifyTurnChanged(BattleClient battle) {
		for (Object obs : this.getListener(IBattleObserver.class)) {
			((IBattleObserver) obs).turnChanged(battle);
		}
	}

}
