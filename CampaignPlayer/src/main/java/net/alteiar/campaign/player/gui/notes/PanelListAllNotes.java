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
package net.alteiar.campaign.player.gui.notes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.campaign.player.gui.chat.PanelChatFactory;
import net.alteiar.client.shared.campaign.CampaignClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelListAllNotes extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField noteNameTextField;

	public PanelListAllNotes() {
		super(new BorderLayout());
		// Add battle button
		JButton addButton = new JButton("Ajouter une note");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNotes();
			}
		});

		noteNameTextField = new JTextField(20);
		JPanel center = new JPanel(new FlowLayout());
		center.add(noteNameTextField);
		center.add(addButton);

		this.add(PanelChatFactory.buildChatLarge(), BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
	}

	protected void addNotes() {
		CampaignClient.INSTANCE.createNotes(noteNameTextField.getText(), "");
	}
}
