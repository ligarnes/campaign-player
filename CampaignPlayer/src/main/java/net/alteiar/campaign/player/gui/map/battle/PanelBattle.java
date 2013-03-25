package net.alteiar.campaign.player.gui.map.battle;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.alteiar.map.battle.Battle;

public class PanelBattle extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelBattle() {
		this.setLayout(new BorderLayout());
	}

	public void setBattle(Battle battle) {
		this.removeAll();
		this.add(new PanelGeneraBattle(battle), BorderLayout.CENTER);
	}
}
