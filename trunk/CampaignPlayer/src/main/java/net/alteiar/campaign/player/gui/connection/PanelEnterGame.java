package net.alteiar.campaign.player.gui.connection;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelEnterGame extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	StartGameDialog startGameDialog;
	
	private JButton createButton;
	private JButton loadButton;
	private JButton joinButton;
	private JButton quitButton;

	public PanelEnterGame(StartGameDialog startGameDialog) {
		this.startGameDialog = startGameDialog;
		initUI();
	}
	
	public void initUI(){
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		createButton = new JButton("Créer un partie");
		createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(createButton);

		loadButton = new JButton("Charger un partie");
		loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(loadButton);

		joinButton = new JButton("Joindre un partie");
		joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(joinButton);

		quitButton = new JButton("Quitter");
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(quitButton);
		
		// Add action listener to buttons
		createButton.addActionListener(this);
		loadButton.addActionListener(this);
		joinButton.addActionListener(this);
		quitButton.addActionListener(this);
		
	}

	public void changeState(JPanel newPanel) {
		this.startGameDialog.changeState(newPanel);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == createButton){
			changeState(new PanelCreate(this.startGameDialog, this));
		}
		else if (event.getSource() == loadButton){
			changeState(new PanelLoad(this.startGameDialog, this));
		}
		else if (event.getSource() == joinButton){
			changeState(new PanelJoin(this.startGameDialog, this));
		}
		else if (event.getSource() == quitButton){
			this.startGameDialog.quitApplication();
		}
		
	}

}
