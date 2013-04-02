package net.alteiar.campaign.player.gui.documents;

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
import net.alteiar.client.bean.BasicBeans;

public class PanelDocumentDescription extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel lblAvatar;

	public PanelDocumentDescription(BasicBeans bean) {
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

		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.insets = new Insets(0, 0, 0, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		add(lblAvatar, gbc_lblAvatar);

		JLabel lblName = new JLabel(bean.getId().toString());
		lblName.setForeground(UiHelper.TEXT_COLOR);

		lblName.setFont(UiHelper.FONT);

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

	protected void deleteBattle() {
		// CampaignClient.getInstance().removeBean(battle);
	}
}
