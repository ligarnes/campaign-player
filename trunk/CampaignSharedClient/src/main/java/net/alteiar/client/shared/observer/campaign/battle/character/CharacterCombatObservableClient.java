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
package net.alteiar.client.shared.observer.campaign.battle.character;

import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.observer.ProxyClientObservable;
import net.alteiar.server.shared.campaign.battle.ICharacterCombatRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class CharacterCombatObservableClient extends
		ProxyClientObservable<ICharacterCombatRemote> implements
		ICharacterCombatClient {
	private static final long serialVersionUID = 1L;

	private static final Class<?> LISTENER = ICharacterCombatObserver.class;

	public CharacterCombatObservableClient(ICharacterCombatRemote character) {
		super(character);
	}

	public void addCharacterCombatListener(ICharacterCombatObserver listener) {
		this.addListener(LISTENER, listener);
	}

	public void removeCharacterCombatListener(ICharacterCombatObserver listener) {
		this.removeListener(LISTENER, listener);
	}

	protected void notifyCharacterChange() {
		for (Object obs : this.getListener(LISTENER)) {
			((ICharacterCombatObserver) obs).characterChange(this);
		}
	}

	protected void notifyHighLightChange(Boolean isHighlighted) {
		for (Object obs : this.getListener(LISTENER)) {
			((ICharacterCombatObserver) obs).highLightChange(this,
					isHighlighted);
		}
	}

	protected void notifyVisibilityChange() {
		for (Object obs : this.getListener(LISTENER)) {
			((ICharacterCombatObserver) obs).visibilityChange(this);
		}
	}

	protected void notifyInitiativeChange() {
		for (Object obs : this.getListener(LISTENER)) {
			((ICharacterCombatObserver) obs).initiativeChange(this);
		}
	}

	protected void notifyCharacterMovedChange() {
		for (Object obs : this.getListener(LISTENER)) {
			((ICharacterCombatObserver) obs).positionChanged(this);
		}
	}

	protected void notifyRotationChange() {
		for (Object obs : this.getListener(LISTENER)) {
			((ICharacterCombatObserver) obs).rotationChanged(this);
		}
	}
}
