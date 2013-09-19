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
package net.alteiar.campaign.player.gui.sideView.chat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.alteiar.beans.chat.Chat;
import net.alteiar.beans.chat.Message;
import net.alteiar.beans.chat.MessageFactory;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.sideView.chat.macro.MacroManager;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelChat extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final PanelMessageReceived receive;
	private final JTextArea textSend;

	private final MacroManager macroManager;

	public PanelChat(Dimension dimRec, Dimension dimSend) {
		super();
		macroManager = new MacroManager();

		receive = new PanelMessageReceived(dimRec);

		textSend = new JTextArea();
		textSend.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER
						&& e.getModifiers() != KeyEvent.VK_CONTROL) {
					sendMessage();
					e.consume();
				}
			}
		});

		JScrollPane scrollWrite = new JScrollPane(textSend);
		scrollWrite.setAutoscrolls(true);
		scrollWrite
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollWrite
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollWrite.setPreferredSize(dimSend);
		scrollWrite.setSize(dimSend);
		scrollWrite.setMinimumSize(dimSend);
		scrollWrite.setMaximumSize(dimSend);

		List<Message> allMsg = getChat().getMessages();
		for (Message msg : allMsg) {
			// FIXME TODO do not understand why this happen, we shoud'nt save
			// and load any null message
			if (msg != null) {
				receive.appendMessage(msg);
			}
		}
		getChat().addPropertyChangeListener(this);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_panelScrollRead = new GridBagConstraints();
		gbc_panelScrollRead.insets = new Insets(0, 0, 5, 0);
		gbc_panelScrollRead.fill = GridBagConstraints.BOTH;
		gbc_panelScrollRead.gridx = 0;
		gbc_panelScrollRead.gridy = 0;
		add(receive, gbc_panelScrollRead);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(scrollWrite, gbc_panel);
	}

	protected Chat getChat() {
		return CampaignClient.getInstance().getChat();
	}

	protected void sendMessage() {
		String message = this.textSend.getText();

		if (message.startsWith("/")) {
			Message msg = macroManager.applyMacro(message);

			if (msg != null) {
				getChat().talk(msg);
			}
		} else {
			getChat().talk(
					MessageFactory.textMessage(CampaignClient.getInstance()
							.getCurrentPlayer(), message));
		}
		this.textSend.setText("");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName()
				.equalsIgnoreCase(Chat.METH_ADD_MESSAGE_METHOD)) {
			receive.appendMessage((Message) evt.getNewValue());
		}
	}
}
