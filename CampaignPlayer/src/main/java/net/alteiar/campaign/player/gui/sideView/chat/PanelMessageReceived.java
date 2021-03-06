package net.alteiar.campaign.player.gui.sideView.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import net.alteiar.beans.chat.Message;
import net.alteiar.beans.dice.Dice;
import net.alteiar.beans.dice.DiceListener;
import net.alteiar.beans.dice.visitor.DiceCountVisitor;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.sideView.chat.message.PanelDice;
import net.alteiar.campaign.player.gui.sideView.chat.message.PanelTextMessage;
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

		CampaignClient.getInstance().getDiceRoller()
				.addPropertyChangeListener(new DiceListener() {
					@Override
					public void diceRolled(Dice dices) {
						DiceCountVisitor visitor = new DiceCountVisitor();
						dices.visit(visitor);

						for (Integer dice : visitor.getDices()) {
							List<Integer> results = visitor.getResults(dice);
							Integer total = 0;
							for (Integer res : results) {
								total += res;
							}
							Integer mod = visitor.getModifier();
							total += mod;

							allMessage.add(new PanelDice(maxWidth, total, dice,
									mod, results));
						}
						revalidate();
						repaint();
					}
				});

		this.add(scroll, BorderLayout.CENTER);

	}

	public void appendMessage(Message msg) {
		if (msg.accept(CampaignClient.getInstance().getCurrentPlayer())) {
			allMessage.add(new PanelTextMessage(maxWidth, msg));
			this.revalidate();
			this.repaint();
		}
	}
}
