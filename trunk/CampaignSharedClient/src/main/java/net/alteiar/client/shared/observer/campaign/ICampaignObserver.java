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

/**
 * @author Cody Stoutenburg
 * 
 */
public interface ICampaignObserver {
	void battleAdded(IBattleClient battle);

	void battleRemoved(IBattleClient battle);

	void playerAdded(IPlayerClient player);

	void playerRemoved(IPlayerClient player);

	void noteAdded(NoteClient note);

	void noteRemoved(NoteClient note);

	void characterAdded(ICharacterSheetClient character);

	void characterRemoved(ICharacterSheetClient character);

	void monsterAdded(ICharacterSheetClient monster);

	void monsterRemoved(ICharacterSheetClient monster);
}
