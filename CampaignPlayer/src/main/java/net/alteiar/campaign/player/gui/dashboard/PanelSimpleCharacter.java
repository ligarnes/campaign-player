package net.alteiar.campaign.player.gui.dashboard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.UiHelper;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.character.ICharacterClientObserver;
import net.alteiar.shared.ImageUtil;

public class PanelSimpleCharacter extends JPanel implements
		ICharacterClientObserver {
	private static final long serialVersionUID = 1L;

	private final JLabel lblAvatar;
	private final CharacterClient character;
	private final Boolean isMonster;

	/**
	 * Create the panel.
	 */
	public PanelSimpleCharacter(CharacterClient character, Boolean isMonster) {
		this.isMonster = isMonster;
		this.character = character;
		this.character.addCharacterListener(this);

		this.setBackground(UiHelper.BACKGROUND_COLOR);

		setMinimumSize(new Dimension(250, 55));
		setPreferredSize(new Dimension(250, 55));
		setMaximumSize(new Dimension(32767, 55));
		setBorder(new LineBorder(UiHelper.BORDER_COLOR, 2));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 0, 40, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 50, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblAvatar = new JLabel();

		lblAvatar.setIcon(new ImageIcon(ImageUtil.resizeImage(
				character.getImage(), 50, 50)));
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.insets = new Insets(0, 0, 0, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		add(lblAvatar, gbc_lblAvatar);

		JLabel lblName = new JLabel(character.getName());
		lblName.setForeground(UiHelper.TEXT_COLOR);

		lblName.setFont(UiHelper.FONT);

		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 0, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);

		/*
		 * if (!isMonster && CampaignClient.getInstance().canAccess(character))
		 * { JLabel btnAcces = new JLabel();
		 * btnAcces.setIcon(Helpers.getIcon("PlayerAccessIcon.png", 40, 40));
		 * GridBagConstraints gbc_btnAcces = new GridBagConstraints();
		 * gbc_btnAcces.insets = new Insets(0, 0, 0, 5); gbc_btnAcces.gridx = 2;
		 * gbc_btnAcces.gridy = 0; add(btnAcces, gbc_btnAcces);
		 * 
		 * btnAcces.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) {
		 * editPlayerAccess(); } }); }
		 */

		JLabel btnDelete = new JLabel();
		btnDelete.setIcon(Helpers.getIcon("delete.png", 40, 40));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 0;
		add(btnDelete, gbc_btnDelete);

		/*
		 * btnDelete.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { deleteCharacter();
		 * } });
		 */
	}

	/*
	 * protected void editPlayerAccess() { PanelPlayerAccess panelAccess = new
	 * PanelPlayerAccess(); DialogOkCancel<PanelPlayerAccess> dialog = new
	 * DialogOkCancel<PanelPlayerAccess>( null, "Changer les acces", true,
	 * panelAccess);
	 * 
	 * panelAccess.setAccess(this.character.getAccess());
	 * 
	 * dialog.setLocation(MouseInfo.getPointerInfo().getLocation());
	 * dialog.setVisible(true);
	 * 
	 * if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
	 * this.character.setAccess(panelAccess.getPlayerAccess()); } }
	 */

	/*
	 * protected void deleteCharacter() { if (isMonster) {
	 * CampaignClient.INSTANCE.removeMonster(character); } else {
	 * CampaignClient.INSTANCE.removeCharacter(character); } }
	 */

	@Override
	public void characterChanged(CharacterClient character) {
		// Do not care
	}

	@Override
	public void imageLoaded(CharacterClient character) {
		lblAvatar.setIcon(new ImageIcon(ImageUtil.resizeImage(
				character.getImage(), 50, 50)));
	}
}
