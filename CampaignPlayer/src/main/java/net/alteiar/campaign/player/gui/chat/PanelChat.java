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
package net.alteiar.campaign.player.gui.chat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.alteiar.CampaignClient;
import net.alteiar.chat.Chat;
import net.alteiar.chat.message.ChatObject;
import net.alteiar.chat.message.DiceSender;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.chat.message.MjSender;
import net.alteiar.chat.message.PrivateSender;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelChat extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final PanelMessageReceived receive;
	private final JTextArea textSend;

	private final Chat chatRoom;

	public PanelChat(Dimension dimRec, Dimension dimSend) {
		super();
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

		chatRoom = CampaignClient.getInstance().getChat();

		List<MessageRemote> allMsg = CampaignClient.getInstance().getChat()
				.getMessages();
		for (MessageRemote msg : allMsg) {
			receive.appendMessage(msg);
		}
		chatRoom.addPropertyChangeListener(this);
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

	protected void sendMessage() {
		String message = this.textSend.getText();

		String command = MessageRemote.TEXT_MESSAGE;
		if (message.startsWith("/")) {
			String[] args = message.split(" ");
			command = args[0];
			ChatObject send = applyMacro(command,
					Arrays.copyOfRange(args, 1, args.length));

			if (send != null) {
				chatRoom.talk(send, command);
			}
		} else {
			chatRoom.talk(message, command);
		}
		this.textSend.setText("");
		// LoggerConfig.showStat();
	}

	private String concat(String[] vals, String separator) {
		StringBuilder builder = new StringBuilder();
		for (String name : vals) {
			builder.append(name + " ");
		}

		return builder.toString();
	}

	protected ChatObject applyMacro(String command, String... args) {
		ChatObject message = null;

		if (command.equals("/dice") || command.equals("/d")) {
			message = applyDice(args[0]);
		} else if (command.equals("/name")) {
			CampaignClient.getInstance().getChat().setPseudo(concat(args, " "));
		} else if (command.equals("/to")) {
			message = applyPrivateMessage(args);
		} else if (command.equals("/mj")) {
			String newCommand = MessageRemote.TEXT_MESSAGE;
			String msg = concat(args, " ");
			if (args[0].startsWith("/")) {
				newCommand = args[0];
				msg = applyMacro(args[0],
						Arrays.copyOfRange(args, 1, args.length))
						.stringFormat();
			}

			message = new MjSender(newCommand, msg);
		}

		return message;
	}

	private PrivateSender applyPrivateMessage(String[] args) {
		StringBuilder builder = new StringBuilder();

		for (int i = 1; i < args.length; i++) {
			builder.append(args[i] + " ");
		}
		builder.deleteCharAt(builder.length() - 1);

		return new PrivateSender(args[0], builder.toString());
	}

	private DiceSender applyDice(String message) {
		// Parse the chat standard
		String diceCntStr = message.substring(0, message.indexOf("d"));

		int modif = message.indexOf("+");
		int diceValEnd = modif == -1 ? message.length() : modif;
		String diceValStr = message.substring(message.indexOf("d") + 1,
				diceValEnd);

		String modStr = "0";
		if (modif != -1) {
			modStr = message.substring(modif + 1);
		}

		int diceCount = Integer.valueOf(diceCntStr);
		int diceValue = Integer.valueOf(diceValStr);
		int mod = Integer.valueOf(modStr);

		return new DiceSender(diceCount, diceValue, mod);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName()
				.equalsIgnoreCase(Chat.METH_ADD_MESSAGE_METHOD)) {
			receive.appendMessage((MessageRemote) evt.getNewValue());
		}
	}
}
