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
package net.alteiar.client.shared.campaign.battle.character;

import java.util.Comparator;
import java.util.Random;


/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterCombatInitiativeComparator implements
		Comparator<ICharacterCombatClient> {
	private static final Random RANDOM = new Random();

	private final long seed;

	public CharacterCombatInitiativeComparator() {
		seed = RANDOM.nextLong();
	}

	@Override
	public int compare(ICharacterCombatClient o1, ICharacterCombatClient o2) {
		// si o1 < o2 = -1
		int result = o1.getInitiative() - o2.getInitiative();
		if (result == 0) {
			result = o1.getCharacter().getInitModifier()
					- o2.getCharacter().getInitModifier();
		}

		while (result == 0) {
			// Do this in order to keep similar
			RANDOM.setSeed(seed
					- (o1.getCharacter().getId() + o2.getCharacter().getId() + o1
							.getInitiative()));
			result = RANDOM.nextInt(20) - RANDOM.nextInt(20);
		}
		return result;
	}

}
