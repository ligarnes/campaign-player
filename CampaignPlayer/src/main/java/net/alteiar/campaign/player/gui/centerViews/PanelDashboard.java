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

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.explorer.PanelDocumentExplorer;
import net.alteiar.campaign.player.gui.players.PanelViewPlayers;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelDashboard extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelDashboard() {
		super(/* getBackgroundTexture(), */new BorderLayout());

		JPanel center = new JPanel();
		center.setOpaque(false);

		center.add(new PanelViewPlayers());

		center.add(new PanelDocumentExplorer());

		this.add(center, BorderLayout.CENTER);
	}

}
