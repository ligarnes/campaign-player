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
package net.alteiar.campaign.player.gui.map.battle;

import javax.swing.JTabbedPane;

import net.alteiar.CampaignAdapter;
import net.alteiar.CampaignClient;
import net.alteiar.documents.map.MapBean;

/**
 * @author Cody Stoutenburg
 * 
 */
public class TabbedPaneListAllBattle extends JTabbedPane {

	private static final long serialVersionUID = -5183267274885482007L;

	public TabbedPaneListAllBattle() {
		super();
		CampaignClient.getInstance()
				.addCampaignListener(new CampaignListener());

		// Add existing battles
		for (MapBean battle : CampaignClient.getInstance().getBattles()) {
			this.addTab(battle.getDocumentName(), new PanelGeneraBattle(battle));
		}
	}

	private class CampaignListener extends CampaignAdapter {
		@Override
		public void battleAdded(MapBean battle) {
			addTab(battle.getDocumentName(), new PanelGeneraBattle(battle));
		}

		@Override
		public void battleRemoved(MapBean battle) {
			removeTabAt(indexOf(battle));
		}
	}

	public MapBean getSelectedBattle() {
		MapBean battle = null;
		int battleIdx = this.getSelectedIndex() - 1;
		if (battleIdx >= 0) {
			battle = findBattleFromName(this.getTitleAt(battleIdx));
		}
		return battle;
	}

	public void setSelectedBattle(MapBean battle) {
		int idx = indexOf(battle);
		if (idx >= 0) {
			this.setSelectedIndex(idx);
		}
	}

	private MapBean findBattleFromName(String name) {
		MapBean finded = null;
		for (MapBean battle : CampaignClient.getInstance().getBattles()) {
			if (battle.getDocumentName().equals(name)) {
				finded = battle;
				break;
			}
		}
		return finded;
	}

	private int indexOf(MapBean battle) {
		int idx = -1;
		String battleName = battle.getDocumentName();
		for (int i = 0; i < this.getTabCount(); ++i) {
			if (battleName.equals(this.getTitleAt(i))) {
				idx = i;
				break;
			}
		}

		return idx;
	}

}
