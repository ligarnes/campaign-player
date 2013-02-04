package net.alteiar.campaign.player.gui.chat;

import java.awt.Dimension;

public class PanelChatFactory {
	private static Dimension DIMENSION_REC_SMALL = new Dimension(190, 200);
	private static Dimension DIMENSION_SEND_SMALL = new Dimension(200, 50);

	private static Dimension DIMENSION_REC_LARGE = new Dimension(340, 300);
	private static Dimension DIMENSION_SEND_LARGE = new Dimension(350, 200);

	private static Dimension DIMENSION_REC_MEDIUM = new Dimension(190, 300);
	private static Dimension DIMENSION_SEND_MEDIUM = new Dimension(200, 200);

	public static PanelChat buildChatSmall() {
		return new PanelChat(DIMENSION_REC_SMALL, DIMENSION_SEND_SMALL);
	}

	public static PanelChat buildChatMedium() {
		return new PanelChat(DIMENSION_REC_MEDIUM, DIMENSION_SEND_MEDIUM);
	}

	public static PanelChat buildChatLarge() {
		return new PanelChat(DIMENSION_REC_LARGE, DIMENSION_SEND_LARGE);
	}
}
