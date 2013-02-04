package net.alteiar.campaign.player.gui.battle;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.battle.plan.PanelGeneraBattle;
import net.alteiar.client.shared.campaign.battle.IBattleClient;

public class PanelBattle extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelBattle() {
		this.setLayout(new BorderLayout());
	}

	public void setBattle(IBattleClient battle) {
		this.removeAll();
		this.add(new PanelGeneraBattle(battle), BorderLayout.CENTER);
	}
}
