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

import net.alteiar.client.CampaignAdapter;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.map.battle.BattleClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public class TabbedPaneListAllBattle extends JTabbedPane
/* JFatTabbedPane */{

	private static final long serialVersionUID = -5183267274885482007L;

	public TabbedPaneListAllBattle() {
		super();
		CampaignClient.getInstance()
				.addCampaignListener(new CampaignListener());

		// Add existing battles
		for (BattleClient battle : CampaignClient.getInstance().getBattles()) {
			this.addTab(battle.getName(), new PanelGeneraBattle(battle));
		}
	}

	private class CampaignListener extends CampaignAdapter {
		@Override
		public void battleAdded(BattleClient battle) {
			addTab(battle.getName(), new PanelGeneraBattle(battle));
		}

		@Override
		public void battleRemoved(BattleClient battle) {
			removeTabAt(indexOf(battle));
		}
	}

	public BattleClient getSelectedBattle() {
		BattleClient battle = null;
		int battleIdx = this.getSelectedIndex() - 1;
		if (battleIdx >= 0) {
			battle = findBattleFromName(this.getTitleAt(battleIdx));
		}
		return battle;
	}

	public void setSelectedBattle(BattleClient battle) {
		int idx = indexOf(battle);
		if (idx >= 0) {
			this.setSelectedIndex(idx);
		}
	}

	private BattleClient findBattleFromName(String name) {
		BattleClient finded = null;
		for (BattleClient battle : CampaignClient.getInstance().getBattles()) {
			if (battle.getName().equals(name)) {
				finded = battle;
				break;
			}
		}
		return finded;
	}

	private int indexOf(BattleClient battle) {
		int idx = -1;
		String battleName = battle.getName();
		for (int i = 0; i < this.getTabCount(); ++i) {
			if (battleName.equals(this.getTitleAt(i))) {
				idx = i;
				break;
			}
		}

		return idx;
	}

}
