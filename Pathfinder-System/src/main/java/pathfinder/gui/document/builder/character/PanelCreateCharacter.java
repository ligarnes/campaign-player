package pathfinder.gui.document.builder.character;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.documents.DocumentType;
import net.alteiar.image.ImageBean;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.shared.ImageUtil;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.images.TransfertImage;
import pathfinder.bean.unit.PathfinderCharacter;
import pathfinder.gui.document.builder.monster.PanelAc;

public class PanelCreateCharacter extends PanelDocumentBuilder {
	private static final long serialVersionUID = 1L;

	private TransfertImage transfertImage;

	private final JLabel lblAvatar;
	private final JTextField textFieldName;
	private final JLabel lblHp;
	private final JTextField textFieldHp;
	private final PanelAc panelAc;

	public PanelCreateCharacter() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblAvatar = new JLabel("");
		lblAvatar.setBorder(new LineBorder(Color.BLACK, 2));
		lblAvatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectAvatar();
			}
		});
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

		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 2;
		gbc_lblNom.gridy = 0;
		add(lblNom, gbc_lblNom);

		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 3;
		gbc_textFieldName.gridy = 0;
		add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);

		lblHp = new JLabel("PV:");
		GridBagConstraints gbc_lblHp = new GridBagConstraints();
		gbc_lblHp.anchor = GridBagConstraints.EAST;
		gbc_lblHp.insets = new Insets(0, 0, 5, 5);
		gbc_lblHp.gridx = 2;
		gbc_lblHp.gridy = 1;
		add(lblHp, gbc_lblHp);

		textFieldHp = new JTextField();
		GridBagConstraints gbc_textFieldHp = new GridBagConstraints();
		gbc_textFieldHp.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldHp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHp.gridx = 3;
		gbc_textFieldHp.gridy = 1;
		add(textFieldHp, gbc_textFieldHp);
		textFieldHp.setColumns(10);

		panelAc = new PanelAc();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 2;
		add(panelAc, gbc_panel);
	}

	@Override
	public void reset() {
		textFieldName.setText("");
		textFieldHp.setText("");
		panelAc.reset();
		transfertImage = null;
		this.lblAvatar.setIcon(null);
	}

	private void selectAvatar() {
		File imageFile = StaticDialog.getSelectedImageFile(this);
		if (imageFile != null) {
			try {
				transfertImage = new SerializableImage(imageFile);
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		}
		revalidateImage();
	}

	private void revalidateImage() {
		if (transfertImage != null) {
			BufferedImage img;
			try {
				img = transfertImage.restoreImage();
				if (img != null) {
					this.lblAvatar.setIcon(new ImageIcon(ImageUtil.resizeImage(
							img, 64, 64)));
				}
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		} else {
			this.lblAvatar.setIcon(null);
		}
	}

	@Override
	public String getDocumentBuilderName() {
		return "Personnage";
	}

	@Override
	public String getDocumentBuilderDescription() {
		return "Cr\u00E9er un personnage";
	}

	@Override
	public Boolean isDataValid() {
		return transfertImage != null;
	}

	@Override
	public String getInvalidMessage() {
		return "Aucun avatar selectionn\u00E9e";
	}

	@Override
	public BasicBean buildDocument() {
		ImageBean image = new ImageBean(transfertImage);
		CampaignClient.getInstance().addBean(image);

		String name = textFieldName.getText();
		Integer hp = Integer.valueOf(textFieldHp.getText());

		Integer ac = panelAc.getAc();
		Integer acFlat = panelAc.getAcFlatFooted();
		Integer acTouch = panelAc.getAcTouch();

		PathfinderCharacter character = new PathfinderCharacter(name, hp, hp,
				ac, acTouch, acFlat, 0, image.getId());

		return character;
	}

	@Override
	public String getDocumentName() {
		return textFieldName.getText();
	}

	@Override
	public DocumentType getDocumentType() {
		return DocumentType.CHARACTER;
	}

}
