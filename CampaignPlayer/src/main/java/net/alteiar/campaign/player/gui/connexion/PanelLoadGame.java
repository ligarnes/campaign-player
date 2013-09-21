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
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.alteiar.campaign.player.infos.Helpers;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.infos.Languages;
import net.alteiar.campaign.player.tools.GlobalProperties;
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.component.MyList;
import net.alteiar.thread.MyRunnable;

public class PanelLoadGame extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;
	private static final int PREFERED_GAME_LIST_HEIGHT = 100;
	private static final int PREFERED_GAME_LIST_WIDTH = 200;

	private MyList<String> savedGameList;

	public PanelLoadGame(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		initGui();
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return new PanelLoading(getDialog(), null);
	}

	private final void initGui() {
		GlobalProperties globalProp = Helpers.getGlobalProperties();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Vector<String> savedGames = getSavedGames();

		savedGameList = new MyList<String>(savedGames);

		savedGameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		savedGameList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScroller = new JScrollPane(savedGameList);
		listScroller.setPreferredSize(new Dimension(PREFERED_GAME_LIST_WIDTH,
				PREFERED_GAME_LIST_HEIGHT));
		this.add(listScroller);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton createButton = new JButton(Languages.getText("load"));
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadCampaign();
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
		buttonPanel.add(createButton);

		String campaignName = globalProp.getLoadCampaign();
		savedGameList.setSelectedValue(campaignName, true);

		this.add(buttonPanel);
	}

	public String getCampaignName() {
		return this.savedGameList.getSelectedValue();
	}

	public void loadCampaign() {
		String campaignName = getCampaignName();
		final String campaignPath = HelpersPath.PATH_SAVE + campaignName;

		Threads.execute(new MyRunnable() {
			@Override
			public void run() {
				MyCampaignFactory.loadCampaign(campaignPath);
			}

			@Override
			public String getTaskName() {
				return "Load campaign";
			}
		});

		nextState();
	}

	public Vector<String> getSavedGames() {
		Vector<String> savedGames = new Vector<String>();

		File folder = new File(HelpersPath.PATH_SAVE);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
				savedGames.add(listOfFiles[i].getName());
			}
		}
		return savedGames;
	}
}
