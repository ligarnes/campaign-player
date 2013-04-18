package net.alteiar.campaign.player.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelEnterGame extends JPanel {

	private static final long serialVersionUID = 1L;

	MainPanelStartGame mainPanelStartGame;

	public PanelEnterGame(MainPanelStartGame mainPanelStartGame) {

		this.mainPanelStartGame = mainPanelStartGame;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton createButton = new JButton("Créer un partie");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeState(new PanelCreate(
						PanelEnterGame.this.mainPanelStartGame,
						PanelEnterGame.this));
			}
		});
		add(createButton);

		JButton loadButton = new JButton("Charger un partie");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeState(new PanelLoad(
						PanelEnterGame.this.mainPanelStartGame,
						PanelEnterGame.this));
			}
		});
		add(loadButton);

		JButton joinButton = new JButton("Joindre un partie");
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeState(new PanelJoin());
			}
		});
		add(joinButton);

		JButton quitButton = new JButton("Quitter");
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelEnterGame.this.mainPanelStartGame.cancelApplication();
			}
		});
		add(quitButton);

	}

	public void changeState(JPanel newPanel) {
		this.mainPanelStartGame.changeState(newPanel);
	}

}
