package net.alteiar.campaign.player.gui.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import net.alteiar.campaign.player.gui.chat.message.PanelDiceMessage;
import net.alteiar.campaign.player.gui.chat.message.PanelEnterMessage;
import net.alteiar.campaign.player.gui.chat.message.PanelLeaveMessage;
import net.alteiar.campaign.player.gui.chat.message.PanelPrivateTextMessage;
import net.alteiar.campaign.player.gui.chat.message.PanelTextMessage;
import net.alteiar.client.shared.campaign.chat.MjSender;
import net.alteiar.client.shared.campaign.chat.PrivateSender;
import net.alteiar.server.shared.campaign.chat.MessageRemote;
import net.miginfocom.swing.MigLayout;

public class PanelMessageReceived extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JPanel allMessage;

	private final int maxWidth;

	public PanelMessageReceived(Dimension dim) {
		super(new BorderLayout());

		allMessage = new JPanel();

		final JScrollPane scroll = new JScrollPane(allMessage);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		int scrollWidth = scroll.getVerticalScrollBar().getWidth();

		maxWidth = dim.width - scrollWidth;
		allMessage.setLayout(new MigLayout(
				"insets 5 5 5 5, flowy, novisualpadding, wmax " + maxWidth,
				"[grow]", "[fill]"));

		// Do not work
		// scroll.setAutoscrolls(true);
		// make autoscroll
		scroll.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					boolean wasAtBottom = true;

					JScrollBar scrollBar = scroll.getVerticalScrollBar();
					int currentHeight = allMessage.getHeight();

					@Override
					public void adjustmentValueChanged(AdjustmentEvent e) {
						BoundedRangeModel brm = scrollBar.getModel();

						int newHeight = allMessage.getHeight();
						if (currentHeight != newHeight) {
							currentHeight = newHeight;

							if (!brm.getValueIsAdjusting()) {
								if (wasAtBottom)
									brm.setValue(brm.getMaximum());
							} else {
								wasAtBottom = ((brm.getValue() + brm
										.getExtent()) == brm.getMaximum());
							}
						} else {
							wasAtBottom = ((brm.getValue() + brm.getExtent()) == brm
									.getMaximum());
						}
					}
				});
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setMaximumSize(dim);
		scroll.setMinimumSize(dim);
		scroll.setPreferredSize(dim);
		scroll.setSize(dim);

		this.add(scroll, BorderLayout.CENTER);

	}

	public void appendMessage(MessageRemote msg) {
		String command = msg.getCommand();

		if (command.equals(MessageRemote.TEXT_MESSAGE)) {
			allMessage.add(new PanelTextMessage(maxWidth, msg));

		} else if (command.equals(MessageRemote.SYSTEM_CONNECT_MESSAGE)) {
			allMessage.add(new PanelEnterMessage(maxWidth, msg));

		} else if (command.equals(MessageRemote.SYSTEM_DISCONNECT_MESSAGE)) {
			allMessage.add(new PanelLeaveMessage(maxWidth, msg));

		} else if (command.equals("/dice") || command.equals("/d")) {
			allMessage.add(new PanelDiceMessage(maxWidth, msg));

		} else if (command.equals("/to")) {
			PrivateSender realMsg = new PrivateSender(msg.getMessage());
			if (realMsg.canAccess()) {
				allMessage.add(new PanelPrivateTextMessage(maxWidth, msg,
						realMsg));
			}

		} else if (command.equals("/mj")) {
			MjSender mjSender = new MjSender(msg.getMessage());

			if (mjSender.canAccess()) {
				MessageRemote newMsg = new MessageRemote(msg.getExpediteur(),
						mjSender.getMessage(), mjSender.getCommand());

				appendMessage(newMsg);
			}
		} else {
			System.out.println("unrecognized command: " + command);
		}

		this.revalidate();
		this.repaint();
	}
}
