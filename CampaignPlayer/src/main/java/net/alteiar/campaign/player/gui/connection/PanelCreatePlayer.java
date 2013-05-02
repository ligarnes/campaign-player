package net.alteiar.campaign.player.gui.connection;

import java.awt.Color;
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
import net.alteiar.panel.PanelSelectColor;

public class PanelCreatePlayer extends PanelStartGameDialog {
	private static final long serialVersionUID = 1L;

	// private static final Color DEFAULT_PLAYER_COLOR = Color.BLUE;

	private JTextField pseudoTextField;
	PanelSelectColor playerColorSelector;

	// private JButton playerColorButton;
	// private Color playerColor;

	public PanelCreatePlayer(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		// this.playerColor = DEFAULT_PLAYER_COLOR;

		initUI();

	}

	@Override
	protected PanelStartGameDialog getNext() {
		return null;
	}

	public final void initUI() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		pseudoTextField = new JTextField(10);

		// Player Info Panel
		JPanel identityPanel = new JPanel(new FlowLayout());
		identityPanel.setBorder(BorderFactory
				.createTitledBorder("Votre identit\u00E9"));
		JPanel pseudo = new JPanel(new FlowLayout());
		pseudo.add(new JLabel("Pseudo:"));
		pseudo.add(pseudoTextField);

		identityPanel.add(pseudo);
		playerColorSelector = new PanelSelectColor();
		identityPanel.add(playerColorSelector);

		this.add(identityPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton createButton = new JButton("Cr\u00E9er");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				createPlayer();
			}
		});

		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				previousState();
			}
		});
		buttonPanel.add(cancelButton);
		buttonPanel.add(createButton);

		this.add(buttonPanel);

	}

	public String getPseudo() {
		return pseudoTextField.getText();
	}

	private Color getPlayerColor() {
		return playerColorSelector.getColor();
	}

	public void createPlayer() {
		// Create a non-MJ player
		CampaignClient.getInstance().createPlayer(getPseudo(), false,
				getPlayerColor());

		nextState();
	}

}
