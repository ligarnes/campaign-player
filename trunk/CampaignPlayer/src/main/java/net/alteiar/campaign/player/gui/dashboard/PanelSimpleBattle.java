package net.alteiar.campaign.player.gui.dashboard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.UiHelper;
import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.MainPanel;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;

public class PanelSimpleBattle extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel lblAvatar;
	private final IBattleClient battle;

	/**
	 * Create the panel.
	 */
	public PanelSimpleBattle(IBattleClient battle) {
		this.battle = battle;
		// this.battle.addObserver(this);

		this.setBackground(UiHelper.BACKGROUND_COLOR);

		setMinimumSize(new Dimension(250, 35));
		setPreferredSize(new Dimension(250, 35));
		setMaximumSize(new Dimension(32767, 35));
		setBorder(new LineBorder(UiHelper.BORDER_COLOR, 2));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 0, 40, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 35, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblAvatar = new JLabel();

		/*
		lblAvatar.setIcon(new ImageIcon(ImageUtil.resizeImage(battle
				.getBattleMap().getBackground(), 50, 50)));*/
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.insets = new Insets(0, 0, 0, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		add(lblAvatar, gbc_lblAvatar);

		JLabel lblName = new JLabel(battle.getName());
		lblName.setForeground(UiHelper.TEXT_COLOR);

		lblName.setFont(UiHelper.FONT);
		lblName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showBattle();
			}
		});

		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 0, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);

		JLabel btnDelete = new JLabel();
		btnDelete.setIcon(Helpers.getIcon("delete.png", 40, 40));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 0;
		add(btnDelete, gbc_btnDelete);

		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteBattle();
			}
		});
	}

	private void showBattle() {
		MainFrame.FRAME.getMainPanel().getPanelBattle()
				.setSelectedBattle(battle);
		MainFrame.FRAME.getMainPanel().setSelectedIndex(MainPanel.TAB_BATTLE);
	}

	protected void deleteBattle() {
		CampaignClient.INSTANCE.removeBattle(battle);
	}
}
