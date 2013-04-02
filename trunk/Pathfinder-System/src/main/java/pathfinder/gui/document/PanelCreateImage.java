package pathfinder.gui.document;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.UrlOkCancel;
import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.shared.ImageUtil;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.images.TransfertImage;
import net.alteiar.utils.images.WebImage;

public class PanelCreateImage extends PanelDocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final JLabel lblPreview;

	private TransfertImage transfertImage;

	public PanelCreateImage() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblChoisirUneImage = new JLabel("Choisir une image:");
		GridBagConstraints gbc_lblChoisirUneImage = new GridBagConstraints();
		gbc_lblChoisirUneImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblChoisirUneImage.gridx = 0;
		gbc_lblChoisirUneImage.gridy = 0;
		add(lblChoisirUneImage, gbc_lblChoisirUneImage);

		JButton btnLocal = new JButton("Local");
		btnLocal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectFile();
			}
		});
		GridBagConstraints gbc_btnLocal = new GridBagConstraints();
		gbc_btnLocal.insets = new Insets(0, 0, 5, 5);
		gbc_btnLocal.gridx = 1;
		gbc_btnLocal.gridy = 0;
		add(btnLocal, gbc_btnLocal);

		JButton btnInternet = new JButton("Internet");
		btnInternet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectUrl();
			}
		});
		GridBagConstraints gbc_btnInternet = new GridBagConstraints();
		gbc_btnInternet.insets = new Insets(0, 0, 5, 5);
		gbc_btnInternet.gridx = 2;
		gbc_btnInternet.gridy = 0;
		add(btnInternet, gbc_btnInternet);

		JLabel lblApercu = new JLabel("Apercu:");
		GridBagConstraints gbc_lblApercu = new GridBagConstraints();
		gbc_lblApercu.insets = new Insets(0, 0, 5, 5);
		gbc_lblApercu.gridx = 0;
		gbc_lblApercu.gridy = 1;
		add(lblApercu, gbc_lblApercu);

		lblPreview = new JLabel("");
		lblPreview.setPreferredSize(new Dimension(300, 300));
		lblPreview.setMinimumSize(new Dimension(300, 300));
		lblPreview.setMaximumSize(new Dimension(300, 300));
		GridBagConstraints gbc_lblPreview = new GridBagConstraints();
		gbc_lblPreview.insets = new Insets(0, 0, 0, 5);
		gbc_lblPreview.fill = GridBagConstraints.BOTH;
		gbc_lblPreview.gridwidth = 4;
		gbc_lblPreview.gridx = 0;
		gbc_lblPreview.gridy = 2;
		add(lblPreview, gbc_lblPreview);
	}

	private void selectUrl() {
		DialogOkCancel<UrlOkCancel> dialogUrl = new DialogOkCancel<UrlOkCancel>(
				null, "Choisir une image sur internet", true, new UrlOkCancel());

		dialogUrl.setOkText("Choisir");
		dialogUrl.setCancelText("Annuler");
		dialogUrl.setLocationRelativeTo(null);
		dialogUrl.pack();
		dialogUrl.setVisible(true);

		if (dialogUrl.getReturnStatus() == DialogOkCancel.RET_OK) {
			transfertImage = new WebImage(dialogUrl.getMainPanel().getUrl());
		}
		revalidateImage();
	}

	private void selectFile() {
		File imageFile = StaticDialog.getSelectedImageFile(this);
		try {
			transfertImage = new SerializableImage(imageFile);
		} catch (IOException e) {
			ExceptionTool.showError(e);
		}

		revalidateImage();
	}

	private void revalidateImage() {
		if (transfertImage != null) {
			BufferedImage img;
			try {
				img = transfertImage.restoreImage();
				if (img != null) {
					this.lblPreview.setIcon(new ImageIcon(ImageUtil
							.resizeImage(img, 300, 300)));
				}
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		} else {
			this.lblPreview.setIcon(null);
		}
	}

	@Override
	public String getDocumentName() {
		return "Image";
	}

	@Override
	public String getDocumentDescription() {
		return "Créer une image";
	}

	@Override
	public void buildDocument() {
		CampaignClient.getInstance().addBean(
				new DocumentImageBean(transfertImage));
		transfertImage = null;
	}

	@Override
	public Boolean isDataValid() {
		return transfertImage != null;
	}

	@Override
	public String getInvalidMessage() {
		return "Aucune image selectionnée";
	}
}
