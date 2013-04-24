package net.alteiar.campaign.player.gui.documents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.UiHelper;
import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.factory.PluginSystem;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.player.Player;
import net.alteiar.shared.ImageUtil;

public class PanelDocumentDescription extends JPanel {
	private static final long serialVersionUID = 1L;

	private static String IMAGE_SHARED = "share-32.png";
	private static String IMAGE_NO_SHARED = "no-share-32.png";
	private static String IMAGE_PARTIALLY_SHARED = "share-someone-32.png";

	private final JLabel lblAvatar;

	private final AuthorizationBean bean;

	public PanelDocumentDescription(AuthorizationBean bean) {
		this.setBackground(UiHelper.BACKGROUND_COLOR);
		this.bean = bean;

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

		BufferedImage icon = ImageUtil.resizeImage(PluginSystem.getInstance()
				.getDocumentIcon(bean), 30, 30);
		if (icon != null) {
			// add the color to each icons
			Graphics2D g2 = (Graphics2D) icon.getGraphics();
			drawPlayerColor(g2, bean);
			g2.dispose();

			lblAvatar.setIcon(new ImageIcon(icon));
		}

		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.insets = new Insets(0, 0, 0, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		add(lblAvatar, gbc_lblAvatar);

		JLabel lblName = new JLabel(bean.getDocumentName());
		lblName.setForeground(UiHelper.TEXT_COLOR);

		lblName.setFont(UiHelper.FONT);

		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 0, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);

		final JLabel btnShared = new JLabel();
		btnShared.setPreferredSize(new Dimension(32, 32));
		btnShared.setMinimumSize(new Dimension(32, 32));
		btnShared.setMaximumSize(new Dimension(32, 32));
		btnShared.setIcon(getCurrentState());
		GridBagConstraints gbc_btnShared = new GridBagConstraints();
		gbc_btnShared.insets = new Insets(0, 0, 0, 5);
		gbc_btnShared.gridx = 2;
		gbc_btnShared.gridy = 0;
		add(btnShared, gbc_btnShared);

		JLabel btnDelete = new JLabel();
		btnDelete.setMaximumSize(new Dimension(32, 32));
		btnDelete.setMinimumSize(new Dimension(32, 32));
		btnDelete.setPreferredSize(new Dimension(32, 32));
		btnDelete.setIcon(Helpers.getIcon("delete.png", 32, 32));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 0;
		add(btnDelete, gbc_btnDelete);

		lblName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showDocument();
			}
		});

		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeBean();
			}
		});

		btnShared.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeState();
			}
		});

		this.bean.addPropertyChangeListener(new AuthorizationAdapter() {
			@Override
			public void authorizationChanged(PropertyChangeEvent evt) {
				btnShared.setIcon(getCurrentState());
			}
		});
	}

	protected void drawDocumentIcon() {
		BufferedImage icon = PluginSystem.getInstance().getDocumentIcon(bean);
		if (icon != null) {
			// resize
			icon = ImageUtil.resizeImage(icon, 30, 30);
			// add the color to each icons
			Graphics2D g2 = (Graphics2D) icon.getGraphics();
			drawPlayerColor(g2, bean);
			g2.dispose();

			lblAvatar.setIcon(new ImageIcon(icon));
		}
	}

	protected void drawPlayerColor(Graphics2D g2, AuthorizationBean bean) {
		Player player = CampaignClient.getInstance().getBean(bean.getOwner());
		g2.setColor(player.getColor());
		g2.fillOval(19, 19, 9, 9);

		g2.setColor(Color.BLACK);
		g2.drawOval(19, 19, 9, 9);
	}

	protected void showDocument() {
		PanelViewDocument<?> comp = PluginSystem.getInstance().getViewPanel(
				bean);
		if (comp != null) {
			JDialog dlg = new JDialog(MainFrame.FRAME, bean.getDocumentName(),
					false);
			dlg.add(comp);
			dlg.setPreferredSize(new Dimension(800, 600));
			dlg.pack();
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
		}
	}

	protected void changeState() {
		if (this.bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer())) {
			this.bean.setPublic(!this.bean.getPublic());
		}
	}

	protected ImageIcon getCurrentState() {
		ImageIcon currentStateIcon = Helpers.getIcon(IMAGE_NO_SHARED);
		if (this.bean.getPublic()) {
			currentStateIcon = Helpers.getIcon(IMAGE_SHARED);
		} else if (!this.bean.getUsers().isEmpty()) {
			currentStateIcon = Helpers.getIcon(IMAGE_PARTIALLY_SHARED);
		}
		return currentStateIcon;
	}

	protected void removeBean() {
		CampaignClient.getInstance().removeBean(bean);
	}
}
