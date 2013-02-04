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

import javax.swing.JPanel;

import net.alteiar.client.shared.campaign.notes.NoteClient;
import net.alteiar.client.shared.observer.campaign.notes.INoteObserver;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelNote extends JPanel implements INoteObserver {
	private static final long serialVersionUID = 1L;

	/*
		private final PanelChat chat;
		private final PanelDescription textEditor;
		*/

	public PanelNote(NoteClient note) {
		super(new BorderLayout());
		/*
		note.addObserver(this);

		chat = PanelChatFactory.buildChatLarge();
		textEditor = new PanelDescription();
		textEditor.setText(note.getText());
		JScrollPane scroll = new JScrollPane(textEditor);

		JPanel panelCenter = new JPanel(new BorderLayout());
		panelCenter.add(new ToolBarText(textEditor, note), BorderLayout.NORTH);
		panelCenter.add(scroll, BorderLayout.CENTER);
		this.add(chat, BorderLayout.WEST);
		this.add(panelCenter, BorderLayout.CENTER);
		*/
	}

	@Override
	public void notesModify(String newNote) {
		// textEditor.setText(newNote);
	}
}
