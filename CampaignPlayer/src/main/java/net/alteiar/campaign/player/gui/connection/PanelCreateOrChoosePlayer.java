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
package net.alteiar.campaign.player.gui.connection;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelCreateOrChoosePlayer extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;

	private enum MenuSelection {
		CREATE, CHOOSE
	}

	private MenuSelection selection;

	public PanelCreateOrChoosePlayer(StartGameDialog startGameDialog) {
		super(startGameDialog, null);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton createButton = new JButton("Créer un nouveau joueur");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selection = MenuSelection.CREATE;
				nextState();
			}
		});
		buttonPanel.add(createButton);

		JButton chooseExistingButton = new JButton("Choisir un joueur existant");
		chooseExistingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selection = MenuSelection.CHOOSE;
				nextState();
			}
		});
		buttonPanel.add(chooseExistingButton);

		this.add(buttonPanel);
	}

	@Override
	protected PanelStartGameDialog getNext() {
		PanelStartGameDialog next = null;
		switch (selection) {
		case CHOOSE:
			next = new PanelChoosePlayer(getDialog(), this);
			break;
		case CREATE:
			next = new PanelCreatePlayer(getDialog(), this);
			break;
		}
		return next;
	}
}
