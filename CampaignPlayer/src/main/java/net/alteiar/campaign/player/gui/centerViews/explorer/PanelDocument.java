package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;

public class PanelDocument extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel lblIcon;
	private final JLabel lblName;
	private final JLabel lblShared;
	private final JLabel lblOwner;

	public PanelDocument() {
		setOpaque(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblIcon = new JLabel();
		lblIcon.setPreferredSize(new Dimension(32, 32));
		lblIcon.setMaximumSize(new Dimension(32, 32));
		lblIcon.setMinimumSize(new Dimension(32, 32));
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.gridheight = 2;
		gbc_lblIcon.insets = new Insets(0, 0, 0, 5);
		gbc_lblIcon.gridx = 0;
		gbc_lblIcon.gridy = 0;
		add(lblIcon, gbc_lblIcon);

		lblName = new JLabel("name");
		lblName.setPreferredSize(new Dimension(152, 14));
		lblName.setMinimumSize(new Dimension(152, 14));
		lblName.setMaximumSize(new Dimension(152, 14));
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		add(lblName, gbc_label);

		lblShared = new JLabel("");
		lblShared.setMaximumSize(new Dimension(32, 32));
		lblShared.setMinimumSize(new Dimension(32, 32));
		lblShared.setPreferredSize(new Dimension(32, 32));
		GridBagConstraints gbc_lblShared = new GridBagConstraints();
		gbc_lblShared.gridheight = 2;
		gbc_lblShared.gridx = 2;
		gbc_lblShared.gridy = 0;
		add(lblShared, gbc_lblShared);

		lblOwner = new JLabel("(cody)");
		lblOwner.setMaximumSize(new Dimension(31, 10));
		lblOwner.setMinimumSize(new Dimension(31, 10));
		lblOwner.setPreferredSize(new Dimension(31, 10));
		lblOwner.setHorizontalAlignment(SwingConstants.LEFT);
		lblOwner.setFont(new Font("Tahoma", Font.PLAIN, 8));
		GridBagConstraints gbc_lblOwner = new GridBagConstraints();
		gbc_lblOwner.insets = new Insets(0, 0, 0, 5);
		gbc_lblOwner.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblOwner.gridx = 1;
		gbc_lblOwner.gridy = 1;
		add(lblOwner, gbc_lblOwner);
	}

	public void setIcon(Icon icon) {
		lblIcon.setIcon(icon);
	}

	public void setBean(BeanBasicDocument doc) {
		lblName.setText(doc.getDocumentName());

		lblShared.setIcon(ExplorerIconUtils.getLockIcon(doc));

		StringBuilder owners = new StringBuilder("<html>");
		if (doc.getPublic()) {
			owners.append("<span style=\"color:#088A08\">Public</span>");
		} else {
			Player owner = CampaignClient.getInstance().getBean(doc.getOwner());
			owners.append("<span style=\"color:#FF0000\">" + owner.getName()
					+ "</span> ");

			for (UniqueID modifier : doc.getModifiers()) {
				Player mod = CampaignClient.getInstance().getBean(modifier);
				owners.append("<span style=\"color:#FF8000\">" + mod.getName()
						+ "</span> ");
			}

			for (UniqueID users : doc.getUsers()) {
				Player user = CampaignClient.getInstance().getBean(users);
				owners.append("<span style=\"color:#FFFF00\">" + user.getName()
						+ "</span> ");
			}
		}
		owners.append("</html>");

		lblOwner.setText(owners.toString());
	}
}
