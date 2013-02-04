package net.alteiar.campaign.player.gui.dashboard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.xml.bind.JAXBException;

import net.alteiar.CharacterIO;
import net.alteiar.ExceptionTool;
import net.alteiar.campaign.player.UiHelper;
import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.client.shared.campaign.CampaignClient;

public class PanelCreateCharacter extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelCreateCharacter(final Boolean isMonster) {
		setBackground(UiHelper.BACKGROUND_COLOR);

		setMinimumSize(new Dimension(200, 35));
		setPreferredSize(new Dimension(200, 35));
		setMaximumSize(new Dimension(32767, 35));
		setBorder(new LineBorder(UiHelper.BORDER_COLOR, 2));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 35, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblAvatar = new JLabel();

		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.insets = new Insets(0, 0, 0, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		add(lblAvatar, gbc_lblAvatar);

		JButton lblName = new JButton("Ajouter");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);
		lblName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = StaticDialog
						.getSelectedCharacter(PanelCreateCharacter.this);
				if (f != null) {
					try {
						if (isMonster) {
							CampaignClient.INSTANCE.createMonster(CharacterIO
									.readFile(f));
						} else {
							CampaignClient.INSTANCE.createCharacter(CharacterIO
									.readFile(f));
						}
					} catch (JAXBException ex) {
						ExceptionTool.showError(ex);
					} catch (IOException ex) {
						ExceptionTool.showError(ex);
					}
				}
			}
		});
	}
}
