package net.alteiar.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.alteiar.ExceptionTool;
import net.alteiar.ImageUtil;
import net.alteiar.images.SerializableFile;
import net.alteiar.pcgen.PathfinderCharacter;

public class PanelCharacter extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JFileChooser chooser = new JFileChooser(".");
	private static FileNameExtensionFilter FILTER_HTML = new FileNameExtensionFilter(
			"fichier Html", "html", "xhtml", "htm");

	private static FileNameExtensionFilter FILTER_IMAGES = new FileNameExtensionFilter(
			"fichier images", "jpg", "jpeg", "png");

	private File file;
	private SerializableFile imgFile;
	private final JButton btnIcon;
	private final JTextField textFieldName;
	private final JSpinner spinnerModInit;
	private final JSpinner spinnerHpTotal;
	private final JSpinner spinnerHpCurrent;
	private final JSpinner spinnerAc;
	private final JSpinner spinnerAcFlatFooted;
	private final JSpinner spinnerAcTouch;
	private final JSpinner spinnerWidth;
	private final JSpinner spinnerHeight;

	private String htmlVersion;

	public PanelCharacter() {
		setAlignmentY(0.0F);
		setAlignmentX(0.0F);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[7];
		gridBagLayout.rowHeights = new int[6];
		gridBagLayout.columnWeights = new double[] { 0.0D, 0.0D, 0.0D, 0.0D,
				0.0D, 0.0D, 0.0D };
		gridBagLayout.rowWeights = new double[] { 0.0D, 0.0D, 1.0D, 0.0D, 0.0D,
				4.9E-324D };
		setLayout(gridBagLayout);

		this.btnIcon = new JButton("");
		this.btnIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelCharacter.this.selectImage();
			}
		});
		this.btnIcon.setMinimumSize(new Dimension(120, 120));
		this.btnIcon.setMaximumSize(new Dimension(120, 120));
		this.btnIcon.setPreferredSize(new Dimension(120, 120));
		GridBagConstraints gbc_btnIcon = new GridBagConstraints();
		gbc_btnIcon.anchor = 12;
		gbc_btnIcon.gridwidth = 3;
		gbc_btnIcon.gridheight = 4;
		gbc_btnIcon.insets = new Insets(0, 0, 5, 5);
		gbc_btnIcon.gridx = 0;
		gbc_btnIcon.gridy = 0;
		add(this.btnIcon, gbc_btnIcon);

		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.anchor = 13;
		gbc_lblNom.gridx = 3;
		gbc_lblNom.gridy = 0;
		add(lblNom, gbc_lblNom);

		this.textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldNom = new GridBagConstraints();
		gbc_textFieldNom.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNom.fill = 2;
		gbc_textFieldNom.gridx = 4;
		gbc_textFieldNom.gridy = 0;
		add(this.textFieldName, gbc_textFieldNom);
		this.textFieldName.setColumns(8);

		JButton btnSelectionHtml = new JButton("Selection Html");
		GridBagConstraints gbc_btnSelectionHtml = new GridBagConstraints();
		gbc_btnSelectionHtml.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectionHtml.gridx = 6;
		gbc_btnSelectionHtml.gridy = 0;
		add(btnSelectionHtml, gbc_btnSelectionHtml);

		JLabel lblModInit = new JLabel("Mod. Init:");
		GridBagConstraints gbc_lblModInit = new GridBagConstraints();
		gbc_lblModInit.anchor = 13;
		gbc_lblModInit.insets = new Insets(0, 0, 5, 5);
		gbc_lblModInit.gridx = 3;
		gbc_lblModInit.gridy = 1;
		add(lblModInit, gbc_lblModInit);

		this.spinnerModInit = new JSpinner();
		GridBagConstraints gbc_spinnerModInit = new GridBagConstraints();
		gbc_spinnerModInit.fill = 2;
		gbc_spinnerModInit.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerModInit.gridx = 4;
		gbc_spinnerModInit.gridy = 1;
		add(this.spinnerModInit, gbc_spinnerModInit);

		JButton btnVersionHtml = new JButton("Version Html");
		GridBagConstraints gbc_btnVersionHtml = new GridBagConstraints();
		gbc_btnVersionHtml.insets = new Insets(0, 0, 5, 0);
		gbc_btnVersionHtml.gridx = 6;
		gbc_btnVersionHtml.gridy = 1;
		add(btnVersionHtml, gbc_btnVersionHtml);

		btnVersionHtml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHtml();
			}
		});

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(0);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = 1;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);

		JLabel lblWidth = new JLabel("Longueur:");
		panel.add(lblWidth);

		this.spinnerWidth = new JSpinner();
		this.spinnerWidth.setPreferredSize(new Dimension(40, 20));
		this.spinnerWidth.setModel(new SizeSpinnerNumber());
		panel.add(this.spinnerWidth);

		JLabel lblHeight = new JLabel("Largeur:");
		panel.add(lblHeight);

		this.spinnerHeight = new JSpinner();
		this.spinnerHeight.setPreferredSize(new Dimension(40, 20));
		this.spinnerHeight.setModel(new SizeSpinnerNumber());
		panel.add(this.spinnerHeight);

		JPanel panelPv = new JPanel();
		panelPv.setBorder(new TitledBorder(null, "Points de vie", 4, 2, null,
				null));
		GridBagConstraints gbc_panelPv = new GridBagConstraints();
		gbc_panelPv.insets = new Insets(0, 0, 5, 5);
		gbc_panelPv.gridwidth = 2;
		gbc_panelPv.fill = 1;
		gbc_panelPv.gridx = 3;
		gbc_panelPv.gridy = 3;
		add(panelPv, gbc_panelPv);
		GridBagLayout gbl_panelPv = new GridBagLayout();
		gbl_panelPv.columnWidths = new int[] { 0, 60 };
		gbl_panelPv.rowHeights = new int[3];
		gbl_panelPv.columnWeights = new double[] { 0.0D, 0.0D };
		gbl_panelPv.rowWeights = new double[] { 0.0D, 0.0D, 4.9E-324D };
		panelPv.setLayout(gbl_panelPv);

		JLabel lblPvTotal = new JLabel("Pv total:");
		GridBagConstraints gbc_lblPvTotal = new GridBagConstraints();
		gbc_lblPvTotal.anchor = 13;
		gbc_lblPvTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblPvTotal.gridx = 0;
		gbc_lblPvTotal.gridy = 0;
		panelPv.add(lblPvTotal, gbc_lblPvTotal);

		this.spinnerHpTotal = new JSpinner();
		this.spinnerHpTotal.setModel(new SpinnerNumberModel(
				Integer.valueOf(10), Integer.valueOf(1), null, Integer
						.valueOf(1)));
		GridBagConstraints gbc_spinnerPvTotal = new GridBagConstraints();
		gbc_spinnerPvTotal.fill = 2;
		gbc_spinnerPvTotal.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPvTotal.gridx = 1;
		gbc_spinnerPvTotal.gridy = 0;
		panelPv.add(this.spinnerHpTotal, gbc_spinnerPvTotal);

		JLabel lblPvActuel = new JLabel("Pv actuel:");
		GridBagConstraints gbc_lblPvActuel = new GridBagConstraints();
		gbc_lblPvActuel.insets = new Insets(0, 0, 0, 5);
		gbc_lblPvActuel.gridx = 0;
		gbc_lblPvActuel.gridy = 1;
		panelPv.add(lblPvActuel, gbc_lblPvActuel);

		this.spinnerHpCurrent = new JSpinner();
		this.spinnerHpCurrent.setModel(new SpinnerNumberModel(Integer
				.valueOf(0), null, null, Integer.valueOf(1)));
		GridBagConstraints gbc_spinnerPvActuel = new GridBagConstraints();
		gbc_spinnerPvActuel.fill = 2;
		gbc_spinnerPvActuel.gridx = 1;
		gbc_spinnerPvActuel.gridy = 1;
		panelPv.add(this.spinnerHpCurrent, gbc_spinnerPvActuel);

		JPanel panelCa = new JPanel();
		panelCa.setBorder(new TitledBorder(null, "Classe d'armure", 4, 2, null,
				null));
		GridBagConstraints gbc_panelCa = new GridBagConstraints();
		gbc_panelCa.insets = new Insets(0, 0, 5, 0);
		gbc_panelCa.gridwidth = 2;
		gbc_panelCa.fill = 1;
		gbc_panelCa.gridx = 5;
		gbc_panelCa.gridy = 3;
		add(panelCa, gbc_panelCa);
		GridBagLayout gbl_panelCa = new GridBagLayout();
		gbl_panelCa.columnWidths = new int[] { 0, 60 };
		gbl_panelCa.rowHeights = new int[4];
		gbl_panelCa.columnWeights = new double[] { 0.0D, 0.0D };
		gbl_panelCa.rowWeights = new double[] { 0.0D, 0.0D, 0.0D, 4.9E-324D };
		panelCa.setLayout(gbl_panelCa);

		JLabel lblNormal = new JLabel("Normal:");
		GridBagConstraints gbc_lblNormal = new GridBagConstraints();
		gbc_lblNormal.anchor = 13;
		gbc_lblNormal.insets = new Insets(0, 0, 5, 5);
		gbc_lblNormal.gridx = 0;
		gbc_lblNormal.gridy = 0;
		panelCa.add(lblNormal, gbc_lblNormal);

		this.spinnerAc = new JSpinner();
		this.spinnerAc.setModel(new SpinnerNumberModel(Integer.valueOf(10),
				Integer.valueOf(1), null, Integer.valueOf(1)));
		GridBagConstraints gbc_spinnerCa = new GridBagConstraints();
		gbc_spinnerCa.fill = 2;
		gbc_spinnerCa.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerCa.gridx = 1;
		gbc_spinnerCa.gridy = 0;
		panelCa.add(this.spinnerAc, gbc_spinnerCa);

		JLabel lblDpourvu = new JLabel("Dépourvu:");
		GridBagConstraints gbc_lblDpourvu = new GridBagConstraints();
		gbc_lblDpourvu.insets = new Insets(0, 0, 5, 5);
		gbc_lblDpourvu.gridx = 0;
		gbc_lblDpourvu.gridy = 1;
		panelCa.add(lblDpourvu, gbc_lblDpourvu);

		this.spinnerAcFlatFooted = new JSpinner();
		this.spinnerAcFlatFooted.setModel(new SpinnerNumberModel(Integer
				.valueOf(10), Integer.valueOf(1), null, Integer.valueOf(1)));
		GridBagConstraints gbc_spinnerDepourvu = new GridBagConstraints();
		gbc_spinnerDepourvu.fill = 2;
		gbc_spinnerDepourvu.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerDepourvu.gridx = 1;
		gbc_spinnerDepourvu.gridy = 1;
		panelCa.add(this.spinnerAcFlatFooted, gbc_spinnerDepourvu);

		JLabel lblContact = new JLabel("Contact:");
		GridBagConstraints gbc_lblContact = new GridBagConstraints();
		gbc_lblContact.insets = new Insets(0, 0, 0, 5);
		gbc_lblContact.anchor = 13;
		gbc_lblContact.gridx = 0;
		gbc_lblContact.gridy = 2;
		panelCa.add(lblContact, gbc_lblContact);

		this.spinnerAcTouch = new JSpinner();
		this.spinnerAcTouch.setModel(new SpinnerNumberModel(
				Integer.valueOf(10), Integer.valueOf(1), null, Integer
						.valueOf(1)));
		GridBagConstraints gbc_spinnerContact = new GridBagConstraints();
		gbc_spinnerContact.fill = 2;
		gbc_spinnerContact.gridx = 1;
		gbc_spinnerContact.gridy = 2;
		panelCa.add(this.spinnerAcTouch, gbc_spinnerContact);

		btnSelectionHtml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectHtml();
			}
		});
		htmlVersion = "";
	}

	public void showHtml() {
		JDialog dlg = new JDialog();
		JScrollPane scroll = new JScrollPane(
				new PanelHtmlCharacter(htmlVersion));
		scroll.setPreferredSize(new Dimension(920, 600));
		dlg.getContentPane().add(scroll);
		dlg.pack();
		dlg.setVisible(true);
	}

	public void selectHtml() {
		chooser.setFileFilter(FILTER_HTML);
		if (chooser.showOpenDialog(this) == 0) {
			File f = chooser.getSelectedFile();
			try {
				htmlVersion = readFileAsString(f);
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		}
	}

	private String readFileAsString(File filePath) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	public void selectImage() {
		chooser.setFileFilter(FILTER_IMAGES);
		if (chooser.showOpenDialog(this) == 0) {
			File f = chooser.getSelectedFile();

			this.imgFile = new SerializableFile(f);
			try {
				BufferedImage tmp = this.imgFile.restoreImage();

				int width = tmp.getWidth();
				int height = tmp.getHeight();
				if (width > 300 && height > 300) {
					float factor = 300.0f / Math.min(width, height);
					tmp = ImageUtil.resizeImage(tmp, (int) (width * factor),
							(int) (height * factor));
					try {
						String fileFormat = "png";
						if (f.getName().endsWith(".jpg")) {
							fileFormat = "jpg";
						}
						File output = File.createTempFile("image", "."
								+ fileFormat);
						ImageIO.write(tmp, fileFormat, output);
						this.imgFile = new SerializableFile(output);
						output.delete();
					} catch (IOException e) {
						ExceptionTool.showError(e,
								"Impossible de redimensionner l'image");
					}
				}
				BufferedImage img = ImageUtil.resizeImage(
						this.imgFile.restoreImage(), 120, 120);
				this.btnIcon.setIcon(new ImageIcon(img));
			} catch (IOException e1) {
				ExceptionTool.showError(e1, "Impossible d'ouvrir l'image");
			}
		}
	}

	public void setCharacter(PathfinderCharacter character) {
		this.textFieldName.setText(character.getName());
		this.spinnerModInit.setValue(character.getInitMod());

		this.spinnerHpTotal.setValue(character.getHp());
		this.spinnerHpCurrent.setValue(character.getCurrentHp());

		this.spinnerAc.setValue(character.getAc());
		this.spinnerAcFlatFooted.setValue(character.getAcFlatFooted());
		this.spinnerAcTouch.setValue(character.getAcTouch());

		this.spinnerWidth.setValue(character.getWidth());
		this.spinnerHeight.setValue(character.getHeight());

		htmlVersion = character.getHtmlCharacter();

		byte[] b = character.getImage();
		if (b != null) {
			try {
				this.imgFile = new SerializableFile(b);
				BufferedImage img = ImageUtil.resizeImage(
						this.imgFile.restoreImage(), 120, 120);
				this.btnIcon.setIcon(new ImageIcon(img));
			} catch (IOException e1) {
				ExceptionTool.showError(e1, "Impossible d'ouvrir l'image");
			}
		}
	}

	public PathfinderCharacter getCharacter() {
		PathfinderCharacter character = new PathfinderCharacter();

		if (this.imgFile != null) {
			character.setImage(this.imgFile.getBytes());
		}
		character.setName(this.textFieldName.getText());
		character.setInitMod((Integer) this.spinnerModInit.getValue());

		character.setHp((Integer) this.spinnerHpTotal.getValue());
		character.setCurrentHp((Integer) this.spinnerHpCurrent.getValue());

		character.setAc((Integer) this.spinnerAc.getValue());
		character
				.setAcFlatFooted((Integer) this.spinnerAcFlatFooted.getValue());
		character.setAcTouch((Integer) this.spinnerAcTouch.getValue());

		character.setWidth((Float) this.spinnerWidth.getValue());
		character.setHeight((Float) this.spinnerHeight.getValue());

		character.setHtmlCharacter(htmlVersion);

		return character;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return this.file;
	}
}
