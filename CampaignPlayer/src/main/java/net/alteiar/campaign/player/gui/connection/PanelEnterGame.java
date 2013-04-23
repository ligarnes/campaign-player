package net.alteiar.campaign.player.gui.connection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelEnterGame extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int MINIMUM_BUTTON_WIDTH = 10;
	private static final int MINIMUM_BUTTON_HEIGHT = 10;
	private static final int MAXIMUM_BUTTON_WIDTH = 200;
	private static final int MAXIMUM_BUTTON_HEIGHT = 50;

	StartGameDialog startGameDialog;
	
	private JButton createButton;
	private JButton loadButton;
	private JButton joinButton;
	private JButton quitButton;

	public PanelEnterGame(StartGameDialog startGameDialog) {
		this.startGameDialog = startGameDialog;
		initGui();
	}
	
	public void initGui(){
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		createButton = createInitializeButton("Créer une partie");
		loadButton = createInitializeButton("Charger un partie");
		joinButton = createInitializeButton("Joindre un partie");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Choix d'initialisation"));
		buttonPanel.add(createButton);
		buttonPanel.add(joinButton);
		buttonPanel.add(loadButton);

		quitButton = new JButton("Quitter");
		quitButton.setAlignmentX(CENTER_ALIGNMENT);
		
		this.add(buttonPanel);
		this.add(quitButton);
		
		// Add action listener to buttons
		createButton.addActionListener(this);
		loadButton.addActionListener(this);
		joinButton.addActionListener(this);
		quitButton.addActionListener(this);
		
	}

	public void changeState(JPanel newPanel) {
		this.startGameDialog.changeState(newPanel);
	}
	
	private JButton createInitializeButton(String name){
		JButton initializeButton = new JButton(name);
		initializeButton.setAlignmentX(CENTER_ALIGNMENT);
		initializeButton.setMinimumSize(new Dimension(MINIMUM_BUTTON_WIDTH, MINIMUM_BUTTON_HEIGHT));
		initializeButton.setMaximumSize(new Dimension(MAXIMUM_BUTTON_WIDTH, MAXIMUM_BUTTON_HEIGHT));
		return initializeButton;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == createButton){
			changeState(new PanelCreateGame(this.startGameDialog, this));
		}
		else if (event.getSource() == loadButton){
			changeState(new PanelLoadGame(this.startGameDialog, this));
		}
		else if (event.getSource() == joinButton){
			changeState(new PanelJoinGame(this.startGameDialog, this));
		}
		else if (event.getSource() == quitButton){
			this.startGameDialog.quitApplication();
		}
		
	}

}
