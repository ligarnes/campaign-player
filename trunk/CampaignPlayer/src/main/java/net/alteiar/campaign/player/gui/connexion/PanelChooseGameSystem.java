/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg. All rights reserved.
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
package net.alteiar.campaign.player.gui.connexion;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.alteiar.campaign.player.infos.Helpers;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.infos.Languages;
import net.alteiar.campaign.player.plugin.PluginInfo;
import net.alteiar.campaign.player.plugin.PluginCellRenderer;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.component.MyList;

import org.apache.log4j.Logger;

public class PanelChooseGameSystem extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;
	private static final int PREFERED_PLAYER_LIST_HEIGHT = 100;
	private static final int PREFERED_PLAYER_LIST_WIDTH = 200;

	public static List<PluginInfo> getAvaibleGameSystem() {
		ArrayList<PluginInfo> plugins = new ArrayList<>();

		File pluginsDir = new File(HelpersPath.PATH_PLUGIN);

		File[] files = pluginsDir.listFiles();
		for (File pluginDir : files) {
			try {
				PluginInfo plugin = new PluginInfo(pluginDir);
				plugins.add(plugin);
			} catch (IOException e) {
				Logger.getLogger(PanelChooseGameSystem.class).warn(
						"impossible de charger le plugin "
								+ pluginDir.getName(), e);
			}

		}
		return plugins;
	}

	private MyList<PluginInfo> gameSystemList;

	public PanelChooseGameSystem(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		refreshGui();
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return new PanelEnterGame(getDialog());
	}

	private final void refreshGui() {
		this.removeAll();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		List<PluginInfo> gameSystems = getAvaibleGameSystem();

		gameSystemList = new MyList<PluginInfo>(gameSystems);
		gameSystemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameSystemList.setLayoutOrientation(JList.VERTICAL);
		gameSystemList.setCellRenderer(new PluginCellRenderer());
		gameSystemList.setSelectedIndex(0);

		JScrollPane listScroller = new JScrollPane(gameSystemList);
		listScroller.setPreferredSize(new Dimension(PREFERED_PLAYER_LIST_WIDTH,
				PREFERED_PLAYER_LIST_HEIGHT));

		this.add(listScroller);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton chooseButton = new JButton(Languages.getText("select_player"));
		chooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				choosePlayer();
			}
		});

		JButton cancelButton = new JButton(Languages.getText("cancel"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				previousState();
			}
		});
		buttonPanel.add(cancelButton);
		buttonPanel.add(chooseButton);

		String gameSystem = Helpers.getGlobalProperties().getGameSystem();

		for (PluginInfo game : gameSystems) {
			if (gameSystem.equals(game.getName())) {
				gameSystemList.setSelectedValue(game, true);
				break;
			}
		}

		this.add(buttonPanel);
	}

	private void choosePlayer() {
		PluginInfo plugin = gameSystemList.getSelectedValue();

		try {
			PluginSystem.buildPluginSystem(plugin);

			Helpers.getGlobalProperties().setGameSystem(plugin.getName());
			Helpers.getGlobalProperties().save();
			nextState();
		} catch (IOException e) {
			Logger.getLogger(getClass()).error(
					"Impossible de charger le plugin", e);
		}
	}
}
