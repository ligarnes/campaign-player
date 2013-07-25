package pathfinder.gui.document.battle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.color.CMMException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.tools.selector.image.ImageSelectorStrategy;
import net.alteiar.campaign.player.gui.tools.selector.image.LocalImageSelector;
import net.alteiar.campaign.player.gui.tools.selector.image.WebImageSelector;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.factory.MapFactory;
import net.alteiar.image.ImageBean;
import net.alteiar.map.MapBean;
import net.alteiar.shared.ImageUtil;
import net.alteiar.utils.images.TransfertImage;

import org.apache.log4j.Logger;

import pathfinder.DocumentTypeConstant;

public class PanelCreateBattle extends PanelDocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final JTextField textFieldMapName;
	private final JLabel lblMapImage;

	private TransfertImage transfertImage;

	public PanelCreateBattle() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 75, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblMapName = new JLabel("Nom:");
		GridBagConstraints gbc_lblMapName = new GridBagConstraints();
		gbc_lblMapName.insets = new Insets(0, 0, 5, 5);
		gbc_lblMapName.anchor = GridBagConstraints.EAST;
		gbc_lblMapName.gridx = 0;
		gbc_lblMapName.gridy = 1;
		add(lblMapName, gbc_lblMapName);

		textFieldMapName = new JTextField(12);
		GridBagConstraints gbc_textFieldMapName = new GridBagConstraints();
		gbc_textFieldMapName.gridwidth = 2;
		gbc_textFieldMapName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMapName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldMapName.gridx = 1;
		gbc_textFieldMapName.gridy = 1;
		add(textFieldMapName, gbc_textFieldMapName);
		textFieldMapName.setColumns(10);

		JLabel lblMap = new JLabel("Carte:");
		GridBagConstraints gbc_lblMap = new GridBagConstraints();
		gbc_lblMap.insets = new Insets(0, 0, 5, 5);
		gbc_lblMap.gridx = 0;
		gbc_lblMap.gridy = 2;
		add(lblMap, gbc_lblMap);

		lblMapImage = new JLabel();
		lblMapImage.setPreferredSize(new Dimension(300, 300));
		lblMapImage.setMinimumSize(new Dimension(300, 300));
		lblMapImage.setMaximumSize(new Dimension(300, 300));
		lblMapImage.setBorder(new LineBorder(Color.BLACK, 2));
		lblMapImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectImage(new LocalImageSelector());
			}
		});

		JButton btnLocal = new JButton("Local");
		GridBagConstraints gbc_btnLocal = new GridBagConstraints();
		gbc_btnLocal.insets = new Insets(0, 0, 5, 5);
		gbc_btnLocal.gridx = 1;
		gbc_btnLocal.gridy = 2;
		btnLocal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectImage(new LocalImageSelector());
			}
		});
		add(btnLocal, gbc_btnLocal);

		JButton btnInternet = new JButton("Internet");
		GridBagConstraints gbc_btnInternet = new GridBagConstraints();
		gbc_btnInternet.insets = new Insets(0, 0, 5, 5);
		gbc_btnInternet.gridx = 2;
		gbc_btnInternet.gridy = 2;
		btnInternet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectImage(new WebImageSelector());
			}
		});
		add(btnInternet, gbc_btnInternet);

		JButton btnDocument = new JButton("document");
		GridBagConstraints gbc_btnDocument = new GridBagConstraints();
		gbc_btnDocument.insets = new Insets(0, 0, 5, 5);
		gbc_btnDocument.gridx = 3;
		gbc_btnDocument.gridy = 2;
		add(btnDocument, gbc_btnDocument);

		GridBagConstraints gbc_lblMapImage = new GridBagConstraints();
		gbc_lblMapImage.fill = GridBagConstraints.BOTH;
		gbc_lblMapImage.gridwidth = 5;
		gbc_lblMapImage.gridx = 0;
		gbc_lblMapImage.gridy = 3;
		add(lblMapImage, gbc_lblMapImage);
	}

	private void selectImage(ImageSelectorStrategy selector) {
		TransfertImage tmp = selector.selectImage();

		if (tmp != null) {
			transfertImage = tmp;
			try {
				BufferedImage img = transfertImage.restoreImage();
				lblMapImage.setIcon(new ImageIcon(ImageUtil.resizeImage(img,
						300, 300)));
			} catch (IOException e) {
				ExceptionTool.showError(e);
			} catch (CMMException e) {
				ExceptionTool.showError(e);
			}
		}
	}

	@Override
	public Boolean isDataValid() {
		boolean emptyText = textFieldMapName.getText().isEmpty();
		return transfertImage != null && !emptyText;
	}

	@Override
	public String getInvalidMessage() {
		String errorMsg = "Aucune image sélectionnée";
		boolean emptyText = textFieldMapName.getText().isEmpty();
		if (emptyText) {
			errorMsg = "Aucun nom pour le document";
		}
		return errorMsg;
	}

	@Override
	public String getDocumentBuilderName() {
		return "Combat";
	}

	@Override
	public String getDocumentBuilderDescription() {
		return "Cr\u00E9e une carte de combat masqu\u00E9e";
	}

	@Override
	public BasicBean buildDocument() {
		MapBean map = null;

		ImageBean image = new ImageBean(transfertImage);
		CampaignClient.getInstance().addBean(image);

		try {
			map = MapFactory.createMap(textFieldMapName.getText(),
					transfertImage);
		} catch (IOException e) {
			Logger.getLogger(getClass()).warn("Impossible de créer le combat",
					e);
		}
		return map;
	}

	@Override
	public String getDocumentName() {
		return textFieldMapName.getText();
	}

	@Override
	public String getDocumentType() {
		return DocumentTypeConstant.BATTLE_MAP;
	}

	@Override
	public void reset() {
		textFieldMapName.setText("");
		transfertImage = null;
		lblMapImage.setIcon(null);
	}
}
