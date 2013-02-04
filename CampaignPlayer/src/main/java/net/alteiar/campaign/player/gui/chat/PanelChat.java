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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.logging.Level;

import javax.swing.BoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.chat.ChatRoomClient;
import net.alteiar.client.shared.observer.campaign.chat.IChatRoomObserver;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.server.shared.campaign.chat.MessageRemote;
import net.alteiar.shared.tool.Randomizer;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelChat extends JPanel implements IChatRoomObserver {
	private static final long serialVersionUID = 1L;

	private final ChatEditor textMessage;
	private final ChatEditor textSend;

	private final ChatRoomClient chatRoom;

	public PanelChat(Dimension dimRec, Dimension dimSend) {
		super();
		textMessage = new ChatEditor();
		textMessage.setAutoscrolls(true);
		textMessage.setEditable(false);

		textSend = new ChatEditor();
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

		final JScrollPane scrollRead = new JScrollPane(textMessage);
		scrollRead.setAutoscrolls(true);
		scrollRead
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollRead
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollRead.setPreferredSize(dimRec);
		scrollRead.setSize(dimRec);
		scrollRead.setMinimumSize(dimRec);
		scrollRead.setMaximumSize(dimRec);
		scrollRead.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {

					BoundedRangeModel brm = scrollRead.getVerticalScrollBar()
							.getModel();
					boolean wasAtBottom = true;

					@Override
					public void adjustmentValueChanged(AdjustmentEvent e) {
						if (!brm.getValueIsAdjusting()) {
							if (wasAtBottom)
								brm.setValue(brm.getMaximum());
						} else
							wasAtBottom = ((brm.getValue() + brm.getExtent()) == brm
									.getMaximum());

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

		chatRoom = CampaignClient.INSTANCE.getChatRoom();

		List<MessageRemote> allMsg = CampaignClient.INSTANCE.getChatRoom()
				.getAllMessage();
		for (MessageRemote msg : allMsg) {
			this.talk(msg);
		}
		chatRoom.addChatRoomListener(this);
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
		add(scrollRead, gbc_panelScrollRead);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(scrollWrite, gbc_panel);
	}

	protected void sendMessage() {
		String pseudo = CampaignClient.INSTANCE.getCurrentPlayer().getName();
		// TODO insert here a macro function
		String message = applyMacro(this.textSend.getText());
		chatRoom.talk(new MessageRemote(pseudo, message));

		this.textSend.setText("");
		LoggerConfig.showStat();
	}

	protected String applyMacro(String message) {
		try {
			if (message.startsWith("!")) {
				String diceCntStr = message.substring(1, message.indexOf("d"));

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

				int total = mod;
				int[] results = new int[diceCount];
				for (int i = 0; i < diceCount; i++) {
					results[i] = Randomizer.dice(diceValue);
					total += results[i];
				}

				StringBuilder builder = new StringBuilder();
				builder.append("Vous avez obtenu " + total + " [" + diceCount
						+ "d" + diceValue + " (");
				for (int i = 0; i < results.length; i++) {
					builder.append(results[i] + ",");
				}
				builder.deleteCharAt(builder.length() - 1);
				builder.append(")");
				if (modif != -1) {
					builder.append(" + " + mod);
				}
				builder.append("]");

				message = builder.toString();
			}
		} catch (Exception e) {
			// do not care about exception
		}
		return message;
	}

	@Override
	public void join(MessageRemote message) {
		LoggerConfig.CLIENT_LOGGER.log(Level.INFO, "join : "
				+ formatMessage(message));
	}

	@Override
	public void leave(MessageRemote message) {
		LoggerConfig.CLIENT_LOGGER.log(Level.INFO, "leave : "
				+ formatMessage(message));
	}

	@Override
	public void talk(MessageRemote message) {
		textMessage.appendText(formatMessage(message));
	}

	public static String formatMessage(MessageRemote message) {
		return "<b>" + message.getExpediteur() + " : </b>"
				+ message.getMessage() + "<br />";
	}
}
