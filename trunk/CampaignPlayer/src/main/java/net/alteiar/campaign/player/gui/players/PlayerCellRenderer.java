package net.alteiar.campaign.player.gui.players;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.alteiar.campaign.player.infos.Languages;
import net.alteiar.player.Player;

public class PlayerCellRenderer implements ListCellRenderer<Player> {

	private final DefaultListCellRenderer renderer;

	public PlayerCellRenderer() {
		renderer = new DefaultListCellRenderer();
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Player> list,
			Player value, int index, boolean isSelected, boolean cellHasFocus) {

		String cellText = "";

		cellText += value.getName();
		if (value.isDm()) {
			cellText += " " + Languages.getText("game_master");
		}
		return renderer.getListCellRendererComponent(list, cellText, index,
				isSelected, cellHasFocus);
	}

}