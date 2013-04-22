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
package net.alteiar.campaign.player;

import java.awt.Dimension;
import java.rmi.RMISecurityManager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.connection.StartGameDialog;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("java.security.policy", "./ressources/security.txt");
		// Create and install a security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		StartGameDialog enterDialog = new StartGameDialog(
				MainFrame.FRAME, "Choix de la partie", true);

		// enterDialog.getContentPane().add(new MainPanelStartGame());

		// TODO : ajuster la taille du panel
		enterDialog.setPreferredSize(new Dimension(400, 400));
		enterDialog.pack();
		enterDialog.setLocationRelativeTo(null);
		enterDialog.setVisible(true);

		if (enterDialog.getStartApplication()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					MainFrame.FRAME.initComponent();
					// CampaignClient.createClientCampaign(5, MainFrame.FRAME);
					MainFrame.FRAME.setVisible(true);
				}
			});
		} else {
			System.exit(0);
		}
	}
}
