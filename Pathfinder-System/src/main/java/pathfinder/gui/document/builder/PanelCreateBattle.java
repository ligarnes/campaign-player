package pathfinder.gui.document.builder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.factory.MapFactory;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.shared.ImageUtil;

public class PanelCreateBattle extends PanelDocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final JTextField textFieldMapName;
	private final JButton btnMapImage;

	private File imageFile;

	public PanelCreateBattle() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
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

		textFieldMapName = new JTextField();
		GridBagConstraints gbc_textFieldMapName = new GridBagConstraints();
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

		btnMapImage = new JButton("");
		btnMapImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectImage();
			}
		});

		GridBagConstraints gbc_lblMapImage = new GridBagConstraints();
		gbc_lblMapImage.fill = GridBagConstraints.BOTH;
		gbc_lblMapImage.gridwidth = 2;
		gbc_lblMapImage.gridheight = 2;
		gbc_lblMapImage.insets = new Insets(0, 0, 0, 5);
		gbc_lblMapImage.gridx = 1;
		gbc_lblMapImage.gridy = 2;
		add(btnMapImage, gbc_lblMapImage);
	}

	private void selectImage() {
		imageFile = StaticDialog.getSelectedImageFile(this);
		try {

			btnMapImage.setIcon(new ImageIcon(ImageUtil.resizeImage(
					ImageIO.read(imageFile), 300, 300)));
		} catch (IOException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public String getDocumentName() {
		return "Combat";
	}

	@Override
	public String getDocumentDescription() {
		return "Crée une carte de combat masquée";
	}

	@Override
	public void buildDocument() {
		try {
			MapFactory.createMap(textFieldMapName.getText(), new Battle(
					textFieldMapName.getText()), imageFile);
		} catch (IOException e) {
			ExceptionTool.showError(e);
		}
	}
}
