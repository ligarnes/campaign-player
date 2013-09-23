package shadowrun.gui.document.character;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.alteiar.beans.media.ImageBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.documents.BeanDocument;
import net.alteiar.shared.ImageUtil;
import shadowrun.bean.unit.ShadowrunCharacter;
import shadowrun.gui.document.character.monitor.PanelMonitorPhysical;
import shadowrun.gui.document.character.monitor.PanelMonitorStun;

public class PanelViewCharacter extends PanelViewDocument {
	private static final long serialVersionUID = 1L;

	private final JLabel lblAvatar;
	private final JLabel textFieldName;
	private final PanelAttributes panelAttribut;
	private final JPanel panelMonitors;
	private final PanelMonitorPhysical panelPhysical;
	private final PanelMonitorStun panelStun;

	public PanelViewCharacter() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 127, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblAvatar = new JLabel("");
		lblAvatar.setBorder(new LineBorder(Color.BLACK, 2));
		lblAvatar.setMinimumSize(new Dimension(64, 64));
		lblAvatar.setMaximumSize(new Dimension(64, 64));
		lblAvatar.setPreferredSize(new Dimension(64, 64));
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.fill = GridBagConstraints.BOTH;
		gbc_lblAvatar.gridheight = 3;
		gbc_lblAvatar.gridwidth = 2;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		add(lblAvatar, gbc_lblAvatar);

		textFieldName = new JLabel();
		textFieldName.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 3;
		gbc_textFieldName.gridy = 0;
		add(textFieldName, gbc_textFieldName);

		panelAttribut = new PanelAttributes();
		GridBagConstraints gbc_panelAttribut = new GridBagConstraints();
		gbc_panelAttribut.insets = new Insets(0, 0, 5, 0);
		gbc_panelAttribut.gridwidth = 5;
		gbc_panelAttribut.fill = GridBagConstraints.BOTH;
		gbc_panelAttribut.gridx = 0;
		gbc_panelAttribut.gridy = 3;
		add(panelAttribut, gbc_panelAttribut);

		this.setMinimumSize(new Dimension(PanelAttributesEditor.MIN_WIDTH,
				PanelAttributesEditor.MIN_HEIGHT));

		panelMonitors = new JPanel();
		GridBagConstraints gbc_panelMonitors = new GridBagConstraints();
		gbc_panelMonitors.gridwidth = 5;
		gbc_panelMonitors.insets = new Insets(0, 0, 0, 5);
		gbc_panelMonitors.fill = GridBagConstraints.BOTH;
		gbc_panelMonitors.gridx = 0;
		gbc_panelMonitors.gridy = 4;
		add(panelMonitors, gbc_panelMonitors);

		panelPhysical = new PanelMonitorPhysical();
		panelMonitors.add(panelPhysical);

		panelStun = new PanelMonitorStun();
		panelMonitors.add(panelStun);
	}

	@Override
	public void setDocument(BeanDocument document) {

		ShadowrunCharacter character = document.getBean();

		textFieldName.setText(character.getName());

		ImageBean bean = CampaignClient.getInstance().getBean(
				character.getImage());

		BufferedImage img;
		try {
			img = bean.getImage().restoreImage();
			if (img != null) {
				this.lblAvatar.setIcon(new ImageIcon(ImageUtil.resizeImage(img,
						64, 64)));
			}
		} catch (IOException e) {
			ExceptionTool.showError(e);
		}

		panelAttribut.setCharacter(character);
		panelPhysical.setCharacter(character);
		panelStun.setCharacter(character);
	}
}
