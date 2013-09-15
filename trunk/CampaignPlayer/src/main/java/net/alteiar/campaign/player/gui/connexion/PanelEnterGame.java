package net.alteiar.campaign.player.gui.connexion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.campaign.CampaignFactoryNew;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.infos.Languages;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.campaign.player.tools.NetworkProperties;

import org.apache.log4j.Logger;

public class PanelEnterGame extends PanelStartGameDialog implements
		ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int MINIMUM_BUTTON_WIDTH = 10;
	private static final int MINIMUM_BUTTON_HEIGHT = 10;
	private static final int MAXIMUM_BUTTON_WIDTH = 200;
	private static final int MAXIMUM_BUTTON_HEIGHT = 50;

	private JButton createButton;
	private JButton loadButton;
	private JButton joinButton;
	private JButton quitButton;

	private enum MenuSelection {
		CREATE, LOAD, JOIN
	}

	private MenuSelection selection;

	public PanelEnterGame(StartGameDialog startGameDialog) {
		super(startGameDialog, null);
		initGui();
	}

	@Override
	protected PanelStartGameDialog getNext() {
		PanelStartGameDialog next = null;
		switch (selection) {
		case CREATE:
			next = new PanelLoading(getDialog(), this);
			break;
		case LOAD:
			next = new PanelLoadGame(getDialog(), this);
			break;
		case JOIN:
			next = new PanelJoinGame(getDialog(), this);
		}
		return next;
	}

	public void initGui() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		createButton = createInitializeButton(Languages.getText("create_game"));
		joinButton = createInitializeButton(Languages.getText("join_game"));
		loadButton = createInitializeButton(Languages.getText("load_game"));
		quitButton = new JButton(Languages.getText("quit"));
		quitButton.setAlignmentX(CENTER_ALIGNMENT);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(BorderFactory.createTitledBorder(Languages
				.getText("choice")));
		buttonPanel.add(createButton);
		buttonPanel.add(joinButton);
		buttonPanel.add(loadButton);

		this.add(buttonPanel);
		this.add(quitButton);

		// Add action listener to buttons
		createButton.addActionListener(this);
		loadButton.addActionListener(this);
		joinButton.addActionListener(this);
		quitButton.addActionListener(this);

	}

	private JButton createInitializeButton(String name) {
		JButton initializeButton = new JButton(name);
		initializeButton.setAlignmentX(CENTER_ALIGNMENT);
		initializeButton.setMinimumSize(new Dimension(MINIMUM_BUTTON_WIDTH,
				MINIMUM_BUTTON_HEIGHT));
		initializeButton.setMaximumSize(new Dimension(MAXIMUM_BUTTON_WIDTH,
				MAXIMUM_BUTTON_HEIGHT));
		return initializeButton;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == createButton) {
			selection = MenuSelection.CREATE;

			NetworkProperties networkProp = new NetworkProperties();
			// Create the game
			try {
				CampaignFactoryNew.startNewCampaign(networkProp.getServerIp(),
						networkProp.getServerPort(), HelpersPath.PATH_SAVE,
						HelpersPath.PATH_DOCUMENT_GLOBAL, PluginSystem
								.getInstance().getKryo());
			} catch (Exception e) {
				Logger.getLogger(getClass()).error(
						"Impossible de créer la partie", e);
			}

		} else if (event.getSource() == loadButton) {
			selection = MenuSelection.LOAD;
		} else if (event.getSource() == joinButton) {
			selection = MenuSelection.JOIN;
		} else if (event.getSource() == quitButton) {
			this.previousState();
			return;
		}
		this.nextState();
	}
}
