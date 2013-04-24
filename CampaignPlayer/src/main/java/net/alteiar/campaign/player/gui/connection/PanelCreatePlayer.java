package net.alteiar.campaign.player.gui.connection;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.CampaignClient;

public class PanelCreatePlayer extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final Color DEFAULT_PLAYER_COLOR = Color.BLUE;

	JPanel previous;
	StartGameDialog startGameDialog;

	private JTextField pseudoTextField;
	private JButton playerColorButton;
	private Color playerColor;

	public PanelCreatePlayer(StartGameDialog startGameDialog, JPanel previous) {
		this.previous = previous;
		this.startGameDialog = startGameDialog;
		this.playerColor = DEFAULT_PLAYER_COLOR;

		initUI();

	}

	public final void initUI() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		pseudoTextField = new JTextField(10);

		// Player Info Panel
		JPanel identityPanel = new JPanel(new FlowLayout());
		identityPanel.setBorder(BorderFactory
				.createTitledBorder("Votre identité"));
		JPanel pseudo = new JPanel(new FlowLayout());
		pseudo.add(new JLabel("Pseudo:"));
		pseudo.add(pseudoTextField);
		playerColorButton = new JButton("Couleur");
		// playerColorButton.setForeground(Color.BLUE);
		playerColorButton.setBackground(playerColor);
		playerColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color chosenColor = JColorChooser
						.showDialog(PanelCreatePlayer.this,
								"Choisissez la couleur de votre personnage",
								Color.BLUE);
				if (chosenColor != null) {
					// playerColorButton.setForeground(playerColor);
					playerColor = chosenColor;
					playerColorButton.setBackground(chosenColor);
				}
			}
		});
		identityPanel.add(pseudo);
		identityPanel.add(playerColorButton);

		this.add(identityPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton createButton = new JButton("Créer");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				createPlayer();
			}
		});
		buttonPanel.add(createButton);

		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				PanelCreatePlayer.this.startGameDialog.changeState(previous);
			}
		});
		buttonPanel.add(cancelButton);

		this.add(buttonPanel);

	}

	public String getPseudo() {
		return pseudoTextField.getText();
	}

	private Color getPlayerColor() {
		return playerColor;
	}

	public void createPlayer() {
		// Create a non-MJ player
		CampaignClient.getInstance().createPlayer(getPseudo(), false,
				getPlayerColor());

		startGameDialog.startApplication();
	}

}