package net.alteiar.campaign.player.gui.players;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.UiHelper;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.player.Player;

public class PlayerPanel extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private static String IMAGE_SHARED = "share-32.png";
	private static String IMAGE_NO_SHARED = "no-share-32.png";
	private static String IMAGE_ICON = "Male.png";

	private final JLabel lblAvatar;

	private final Player player;
	private final JLabel btnPresence;

	public PlayerPanel(Player bean) {
		this.setBackground(UiHelper.BACKGROUND_COLOR);
		this.player = bean;
		player.addPropertyChangeListener(Player.PROP_CONNECTED_PROPERTY, this);

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
		lblAvatar.setIcon(Helpers.getIcon(IMAGE_ICON));

		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.insets = new Insets(0, 0, 0, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		add(lblAvatar, gbc_lblAvatar);

		JLabel lblName = new JLabel(player.getName());
		lblName.setForeground(UiHelper.TEXT_COLOR);

		lblName.setFont(UiHelper.FONT);

		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 0, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);

		btnPresence = new JLabel();
		btnPresence.setPreferredSize(new Dimension(32, 32));
		btnPresence.setMinimumSize(new Dimension(32, 32));
		btnPresence.setMaximumSize(new Dimension(32, 32));
		btnPresence.setIcon(getCurrentState());
		GridBagConstraints gbc_btnShared = new GridBagConstraints();
		gbc_btnShared.insets = new Insets(0, 0, 0, 5);
		gbc_btnShared.gridx = 2;
		gbc_btnShared.gridy = 0;
		add(btnPresence, gbc_btnShared);

		this.player.addPropertyChangeListener(new AuthorizationAdapter() {
			@Override
			public void authorizationChanged(PropertyChangeEvent evt) {
				btnPresence.setIcon(getCurrentState());
			}
		});
	}

	protected ImageIcon getCurrentState() {
		ImageIcon currentStateIcon = Helpers.getIcon(IMAGE_NO_SHARED);
		if (this.player.getConnected()) {
			currentStateIcon = Helpers.getIcon(IMAGE_SHARED);
		}
		return currentStateIcon;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		btnPresence.setIcon(getCurrentState());
	}
}