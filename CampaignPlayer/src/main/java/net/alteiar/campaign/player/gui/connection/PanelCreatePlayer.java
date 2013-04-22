package net.alteiar.campaign.player.gui.connection;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.GlobalProperties;
import net.alteiar.campaign.player.Helpers;

public class PanelCreatePlayer extends JPanel {
	private static final long serialVersionUID = 1L;

	JPanel previous;
	StartGameDialog startGameDialog;
	
	JTextField pseudoTextField;
	
	public PanelCreatePlayer(StartGameDialog startGameDialog, JPanel previous) {
		this.previous = previous;
		this.startGameDialog = startGameDialog;

		initUI();
	
	}
	
	public final void initUI(){
		
		GlobalProperties globalProp = Helpers.getGlobalProperties();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		pseudoTextField = new JTextField(10);
		pseudoTextField.setText(globalProp.getPseudo());
		
		JPanel identityPanel = new JPanel(new FlowLayout());
		identityPanel.setBorder(BorderFactory.createTitledBorder("Votre identité"));

		JPanel pseudo = new JPanel(new FlowLayout());
		pseudo.add(new JLabel("Pseudo:"));
		pseudo.add(pseudoTextField);

		identityPanel.add(pseudo);
		
		this.add(identityPanel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		JButton createButton = new JButton("Créer");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createPlayer();
			}
		});
		buttonPanel.add(createButton);

		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelCreatePlayer.this.startGameDialog.changeState(previous);
			}
		});
		buttonPanel.add(cancelButton);
		
		this.add(buttonPanel);
		
	}
	
	public String getPseudo() {
		return pseudoTextField.getText();
	}
	
	public void createPlayer(){
		
		String name = getPseudo();
		Boolean isMj = false;
		
		// Create one player
		CampaignClient.getInstance().createPlayer(name, isMj);
		
		startGameDialog.startApplication();
	}

}
