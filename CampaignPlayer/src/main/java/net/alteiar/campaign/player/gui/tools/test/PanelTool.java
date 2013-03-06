package net.alteiar.campaign.player.gui.tools.test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.alteiar.campaign.player.gui.battle.plan.MapEditableInfo;
import net.alteiar.campaign.player.gui.battle.tools.PanelCharacterInfo;
import net.alteiar.campaign.player.gui.chat.PanelChat;
import net.alteiar.campaign.player.gui.chat.PanelChatFactory;
import net.alteiar.server.document.map.battle.BattleClient;

public class PanelTool extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelTool(MapEditableInfo info, BattleClient battle) {
		JPanel inside = new JPanel();
		inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));

		PanelChat chat = PanelChatFactory.buildChatSmall();
		PanelCharacterInfo infoCharacter = new PanelCharacterInfo(battle);
		inside.add(chat);
		inside.add(new PanelZoom(info));
		inside.add(infoCharacter);

		JScrollPane scroll = new JScrollPane(inside);
		this.add(scroll, BorderLayout.CENTER);

		this.setSize(new Dimension(250, Short.MAX_VALUE));
		this.setMinimumSize(new Dimension(250, Short.MAX_VALUE));
		this.setMaximumSize(new Dimension(250, Short.MAX_VALUE));
		this.setPreferredSize(new Dimension(250, Short.MAX_VALUE));
	}
}
