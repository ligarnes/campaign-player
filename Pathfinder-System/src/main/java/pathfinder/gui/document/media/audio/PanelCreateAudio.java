package pathfinder.gui.document.media.audio;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.tools.selector.image.ImageSelectorStrategy;
import net.alteiar.campaign.player.gui.tools.selector.image.LocalImageSelector;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.media.AudioBean;
import net.alteiar.utils.files.video.SerializableAudio;
import pathfinder.DocumentTypeConstant;

public class PanelCreateAudio extends PanelDocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final PanelMp3Player panelPlayer;
	private SerializableAudio selectedAudio;
	private final JTextField textFieldName;

	public PanelCreateAudio() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 39, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblName = new JLabel("Nom:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);

		textFieldName = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textFieldName, gbc_textField);
		textFieldName.setColumns(10);

		JLabel lblChooseFile = new JLabel("Choisir une musique:");
		GridBagConstraints gbc_lblChooseFile = new GridBagConstraints();
		gbc_lblChooseFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseFile.gridx = 0;
		gbc_lblChooseFile.gridy = 1;
		add(lblChooseFile, gbc_lblChooseFile);

		JButton btnLocal = new JButton("Local");
		btnLocal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectAudio(new LocalImageSelector());
			}
		});
		GridBagConstraints gbc_btnLocal = new GridBagConstraints();
		gbc_btnLocal.insets = new Insets(0, 0, 5, 5);
		gbc_btnLocal.gridx = 1;
		gbc_btnLocal.gridy = 1;
		add(btnLocal, gbc_btnLocal);

		panelPlayer = new PanelMp3Player();
		GridBagConstraints gbc_panelPlayer = new GridBagConstraints();
		gbc_panelPlayer.gridwidth = 5;
		gbc_panelPlayer.insets = new Insets(0, 0, 0, 5);
		gbc_panelPlayer.fill = GridBagConstraints.BOTH;
		gbc_panelPlayer.gridx = 0;
		gbc_panelPlayer.gridy = 2;
		add(panelPlayer, gbc_panelPlayer);
	}

	private void selectAudio(ImageSelectorStrategy selector) {
		File audioFile = StaticDialog.getSelectedAudioFile(null);

		if (audioFile != null) {
			try {
				selectedAudio = new SerializableAudio(audioFile);
				panelPlayer.setAudioBean(new AudioBean(selectedAudio));
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		}
	}

	@Override
	public String getDocumentBuilderName() {
		return "Musique";
	}

	@Override
	public String getDocumentBuilderDescription() {
		return "Cr\u00E9er une musique";
	}

	@Override
	public Boolean isDataValid() {
		boolean emptyText = textFieldName.getText().isEmpty();

		return !emptyText && selectedAudio != null;
	}

	@Override
	public String getInvalidMessage() {
		String errorMsg = "Aucune musique selectionn\u00E9e";

		boolean emptyText = textFieldName.getText().isEmpty();
		if (emptyText) {
			errorMsg = "Aucun nom pour le document";
		}
		return errorMsg;
	}

	@Override
	public String getDocumentName() {
		return textFieldName.getText();
	}

	@Override
	public String getDocumentType() {
		return DocumentTypeConstant.AUDIO;
	}

	@Override
	public BasicBean buildDocument() {
		panelPlayer.stop();
		return new AudioBean(selectedAudio);
	}

	@Override
	public void reset() {
		textFieldName.setText("");
	}
}
