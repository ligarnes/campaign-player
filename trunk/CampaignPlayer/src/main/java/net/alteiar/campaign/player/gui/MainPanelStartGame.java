package net.alteiar.campaign.player.gui;

import javax.swing.JPanel;

public class MainPanelStartGame extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public MainPanelStartGame() {
		add(new PanelEnterGame(this));
	}
	
	public void changeState(JPanel newPanel){
		removeAll();
		add(newPanel);
		this.revalidate();
		this.repaint();
	}
	
	

}
