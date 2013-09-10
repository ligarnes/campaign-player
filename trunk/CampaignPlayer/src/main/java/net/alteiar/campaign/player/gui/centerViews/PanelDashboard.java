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
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.explorer.PanelDocumentExplorer;
import net.alteiar.campaign.player.gui.players.PanelViewPlayers;
import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.panel.MyPanel;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelDashboard extends MyPanel {
	private static final long serialVersionUID = 1L;

	// nice font for fantasy
	// private static Font PLAYER_FONT = new Font("French Script MT",
	// Font.PLAIN,
	// 32);

	private static BufferedImage getBackgroundTexture() {
		return HelpersImages.getImage(HelpersPath.getPathTexture("parchemin.jpg"), 500,
				500);
	}

	public PanelDashboard() {
		super(getBackgroundTexture(), new BorderLayout());

		JPanel center = new JPanel();
		center.setOpaque(false);

		center.add(new PanelViewPlayers());
		// center.add(new PanelDocumentManager());

		center.add(new PanelDocumentExplorer());

		this.add(center, BorderLayout.CENTER);
	}

}