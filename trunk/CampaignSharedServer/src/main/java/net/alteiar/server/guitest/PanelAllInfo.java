package net.alteiar.server.guitest;

import javax.swing.JPanel;

public class PanelAllInfo extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelAllInfo() {
		add(new PanelListCharacters());

		add(new PanelListMonster());

		add(new PanelListPlayers());
		add(new PanelListBattles());

		add(new PanelDetailsBattle());
	}

}
