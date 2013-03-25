package net.alteiar.campaign.player.gui.chat.message;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.chat.message.DiceSender;
import net.alteiar.chat.message.MessageRemote;
import net.miginfocom.swing.MigLayout;

public class PanelDiceMessage extends JPanel {
	private static final long serialVersionUID = 1L;

	private static Color TOTAL_BACKGROUND_COLOR = new Color(115, 53, 28);
	private static Color GENERAL_BACKGROUND_COLOR = new Color(65, 33, 18);
	private static Color TEXT_COLOR = new Color(219, 213, 137);

	// private final DiceSender diceSender;

	public PanelDiceMessage(int maxWidth, MessageRemote msg) {

		this.setLayout(new MigLayout("insets 5 5 0 0, hmin 40, w " + maxWidth
				+ ", hmax " + maxWidth, "[][]", "[]"));

		DiceSender diceSender = new DiceSender(msg.getMessage());
		String total = diceSender.getTotal();
		String diceCount = diceSender.getDiceCount();
		String dice = diceSender.getDiceValue();
		String modifier = diceSender.getModifier();
		String[] vals = diceSender.getResults();

		int resWidth = 20 + total.length() * 10;
		int resHeight = 30;
		add(new LabelResult(total), "cell 0 0, w " + resWidth + ", h "
				+ resHeight + ", growx,aligny center");

		StringBuilder builder = new StringBuilder();

		builder.append("<html>" + diceCount + "d" + dice);
		if (Integer.valueOf(modifier) > 0) {
			builder.append("+" + modifier);
		}
		builder.append(" (");
		for (String val : vals) {
			builder.append(val + ", ");
		}
		builder.delete(builder.length() - 2, builder.length());
		builder.append(")</html>");

		JLabel lblDetails = new JLabel(builder.toString());
		lblDetails.setOpaque(false);
		lblDetails.setForeground(TEXT_COLOR);
		add(lblDetails, "cell 1 0, wmax " + (maxWidth - resWidth)
				+ ",growx,aligny center");

		this.setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(GENERAL_BACKGROUND_COLOR);
		g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 5, 5);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 5, 5);

		super.paint(g2);
	}

	private class LabelResult extends JLabel {
		private static final long serialVersionUID = 1L;

		private final String txt;

		public LabelResult(String txt) {
			this.txt = txt;
		}

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setFont(g2.getFont().deriveFont(Font.BOLD));
			g2.setColor(TOTAL_BACKGROUND_COLOR);
			g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 5, 5);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 5, 5);

			g.setColor(TEXT_COLOR);

			FontMetrics metrics = g.getFontMetrics();
			int textWidth = metrics.stringWidth(txt);

			g2.drawString(txt, (getWidth() - textWidth) / 2,
					(getHeight() / 2) + 5);

		}
	}
}
