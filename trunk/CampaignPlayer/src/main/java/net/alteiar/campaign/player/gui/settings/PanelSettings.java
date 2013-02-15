package net.alteiar.campaign.player.gui.settings;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.client.shared.campaign.CampaignClient;

public class PanelSettings extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JPanel panelChoice;
	private final JPanel panelSetting;

	private final JTextField textfieldName;

	public PanelSettings() {
		super(new BorderLayout());

		panelChoice = new JPanel();
		panelSetting = new PanelBattleParameters();

		textfieldName = new JTextField(20);
		JButton buttonSave = new JButton("save");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});

		this.add(panelChoice, BorderLayout.WEST);
		// this.add(panelSetting, BorderLayout.CENTER);
		this.add(new PanelLoadCampaign(), BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.add(textfieldName);
		south.add(buttonSave);
		this.add(south, BorderLayout.SOUTH);
	}

	private String getSaveName() {
		return textfieldName.getText();
	}

	private void save() {
		File current = new File("./ressources/sauvegarde/");
		CampaignClient.INSTANCE.save(new File(current, getSaveName()));
	}
}
