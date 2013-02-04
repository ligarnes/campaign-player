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
package net.alteiar.campaign.player.gui;

import javax.swing.JTabbedPane;

import net.alteiar.campaign.player.gui.battle.TabbedPaneListAllBattle;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MainPanel extends JTabbedPane {
	private static final long serialVersionUID = -2168295011453847687L;

	public static final int TAB_DASHBOARD = 0;
	public static final int TAB_BATTLE = 1;

	private final TabbedPaneListAllBattle allBattle;

	// JTabbedPane
	// first pane dashboard
	// first pane battles
	// second pane players
	public MainPanel() {
		super();
		allBattle = new TabbedPaneListAllBattle();

		// battle = new PanelBattle();

		this.addTab("Tableau de bord", new PanelDashboard());
		this.addTab("Combats", allBattle);

		// this.addTab("Debug-Test", new PanelRoleplay());
	}

	public TabbedPaneListAllBattle getPanelBattle() {
		return allBattle;
	}
}
