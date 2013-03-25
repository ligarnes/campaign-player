package net.alteiar.campaign.player.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.chat.PanelChat;
import net.alteiar.campaign.player.gui.chat.PanelChatFactory;
import net.alteiar.campaign.player.gui.factory.PluginSystem;
import net.alteiar.panel.PanelShowHide;

public class PanelWest extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelWest() {
		Integer width = 250;
		JPanel inside = new JPanel();
		inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));

		PanelChat chat = PanelChatFactory.buildChatSmall();
		JPanel infoCharacter = PluginSystem.getPluginSystemGui()
				.getGuiCharacterFactory().buildSmallCharacterSheet();

		inside.add(new PanelShowHide("Chat", chat, width));
		inside.add(new PanelShowHide("Personnages", infoCharacter, width));

		this.add(inside);
	}
}
