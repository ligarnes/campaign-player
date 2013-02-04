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
package net.alteiar.campaign.player.gui.character;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.chat.PanelChatFactory;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelGlobalCharacter extends JPanel {
	private static final long serialVersionUID = 1L;

	private final PanelHtmlCharacter panelHtml;
	private final PanelCharacterLocal panelDefault;

	private final JToggleButton simpleButton;
	private final JToggleButton fullButton;

	public PanelGlobalCharacter(ICharacterSheetClient client) {
		super(new BorderLayout());

		this.add(PanelChatFactory.buildChatMedium(), BorderLayout.WEST);

		panelHtml = new PanelHtmlCharacter(client);

		panelDefault = new PanelCharacterLocal(client);

		JPanel paneCharacterSheet = new JPanel(new BorderLayout());
		paneCharacterSheet.add(panelHtml, BorderLayout.NORTH);
		paneCharacterSheet.add(panelDefault, BorderLayout.CENTER);

		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		simpleButton = new JToggleButton("Simple");
		simpleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simpleClick();
			}
		});
		fullButton = new JToggleButton("Complète");
		fullButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fullClick();
			}
		});
		panelButton.add(simpleButton);
		panelButton.add(fullButton);

		JPanel panelChoice = new JPanel(new BorderLayout());
		panelChoice.add(panelButton, BorderLayout.NORTH);
		panelChoice.add(paneCharacterSheet, BorderLayout.CENTER);

		JScrollPane scroll = new JScrollPane(panelChoice);
		this.add(scroll, BorderLayout.CENTER);

		simpleClick();
	}

	public ICharacterSheetClient getCharacter() {
		return panelHtml.getCharacter();
	}

	private void simpleClick() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				simpleButton.setEnabled(false);
				fullButton.setEnabled(true);

				panelDefault.setVisible(true);
				panelHtml.setVisible(false);
			}
		});
	}

	private void fullClick() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				simpleButton.setEnabled(true);
				fullButton.setEnabled(false);

				panelDefault.setVisible(false);
				panelHtml.setVisible(true);
			}
		});
	}
}
