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
package net.alteiar.campaign.player.gui.centerViews;

import java.beans.Beans;
import java.beans.PropertyChangeEvent;

import javax.swing.JTabbedPane;

import net.alteiar.WaitBeanListener;
import net.alteiar.campaign.CampaignAdapter;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.PanelGlobalMap;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDocument;
import net.alteiar.map.MapBean;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

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
		for (BeanDocument battle : CampaignClient.getInstance().getDocuments()) {
			addBattleDocument(battle);
		}
	}

	private void addBattleDocument(final BeanDocument doc) {
		if (isBattle(doc)) {
			CampaignClient.getInstance().addWaitBeanListener(
					new WaitBeanListener() {
						@Override
						public UniqueID getBeanId() {
							return doc.getBeanId();
						}

						@Override
						public void beanReceived(BasicBean bean) {
							addTabBattle(doc, (MapBean) bean);

						}
					});
		}
	}

	private void addTabBattle(final BeanDocument doc, final MapBean bean) {
		if (doc.isAllowedToSee(CampaignClient.getInstance().getCurrentPlayer())) {
			addTab(doc.getDocumentName(), new PanelGlobalMap(bean));
		}

		final AuthorizationAdapter auth = new AuthorizationAdapter(doc) {
			@Override
			public void authorizationChanged(PropertyChangeEvent evt) {
				authorizationChange(doc, bean);
			}
		};
	}

	private void authorizationChange(final BeanDocument doc, final MapBean bean) {
		int idx = indexOf(doc);
		if (doc.isAllowedToSee(CampaignClient.getInstance().getCurrentPlayer())) {
			// do not add more than once
			if (idx < 0) {
				addTab(doc.getDocumentName(), new PanelGlobalMap(bean));
			}
		} else if (idx >= 0) {
			removeTabAt(idx);
		}
	}

	private void removeBattleDocument(BeanDocument doc) {
		int idx = indexOf(doc);
		if (idx >= 0) {
			removeTabAt(idx);
		}
	}

	private class CampaignListener extends CampaignAdapter {
		@Override
		public void beanAdded(BeanBasicDocument bean) {
			if (Beans.isInstanceOf(bean, BeanDocument.class)) {
				addBattleDocument((BeanDocument) bean);
			}
		}

		@Override
		public void beanRemoved(BeanBasicDocument bean) {
			if (Beans.isInstanceOf(bean, BeanDocument.class)) {
				removeBattleDocument((BeanDocument) bean);
			}
		}
	}

	public BeanDocument getSelectedBattle() {
		BeanDocument battle = null;
		int battleIdx = this.getSelectedIndex() - 1;
		if (battleIdx >= 0) {
			battle = findBattleFromName(this.getTitleAt(battleIdx));
		}
		return battle;
	}

	public void setSelectedBattle(BeanDocument doc) {
		if (isBattle(doc)) {
			int idx = indexOf(doc);
			if (idx >= 0) {
				this.setSelectedIndex(idx);
			}
		}
	}

	private BeanDocument findBattleFromName(String name) {
		BeanDocument finded = null;
		for (BeanDocument battle : CampaignClient.getInstance().getDocuments()) {
			if (isBattle(battle) && battle.getDocumentName().equals(name)) {
				finded = battle;
				break;
			}
		}

		return finded;
	}

	// TODO FIXME move it
	public static final String BATTLE_MAP = "battle-map";

	private boolean isBattle(BeanDocument battle) {
		return battle.getDocumentType().equals(BATTLE_MAP);
	}

	private int indexOf(BeanDocument battle) {
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
