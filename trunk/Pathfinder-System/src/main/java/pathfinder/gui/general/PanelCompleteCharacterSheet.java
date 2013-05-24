package pathfinder.gui.general;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.documents.character.Character;
import net.alteiar.shared.ImageUtil;
import pathfinder.bean.unit.PathfinderCharacter;

public class PanelCompleteCharacterSheet extends JPanel implements
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final PathfinderCharacter character;

	private final JLabel btnIcon;
	private final JTextField textFieldName;
	private final JSpinner spinnerModInit;

	private final JSpinner spinnerHpTotal;
	private final JSpinner spinnerHpCurrent;

	private final JSpinner spinnerAc;
	private final JSpinner spinnerAcFlatFooted;
	private final JSpinner spinnerAcTouch;

	public PanelCompleteCharacterSheet(PathfinderCharacter character) {
		this.character = character;

		setAlignmentY(Component.TOP_ALIGNMENT);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		btnIcon = new JLabel("");
		btnIcon.setMinimumSize(new Dimension(120, 120));
		btnIcon.setMaximumSize(new Dimension(120, 120));
		btnIcon.setPreferredSize(new Dimension(120, 120));
		GridBagConstraints gbc_btnIcon = new GridBagConstraints();
		gbc_btnIcon.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnIcon.gridwidth = 3;
		gbc_btnIcon.gridheight = 3;
		gbc_btnIcon.insets = new Insets(0, 0, 5, 5);
		gbc_btnIcon.gridx = 0;
		gbc_btnIcon.gridy = 0;
		add(btnIcon, gbc_btnIcon);

		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.gridx = 3;
		gbc_lblNom.gridy = 0;
		add(lblNom, gbc_lblNom);

		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldNom = new GridBagConstraints();
		gbc_textFieldNom.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNom.gridx = 4;
		gbc_textFieldNom.gridy = 0;
		add(textFieldName, gbc_textFieldNom);
		textFieldName.setColumns(8);

		JButton btnEditAccess = new JButton();
		btnEditAccess.setIcon(Helpers.getIcon("PlayerAccessIcon.png", 40, 40));
		btnEditAccess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editPlayerAccess();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridheight = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 0;
		add(btnEditAccess, gbc_btnNewButton);

		JLabel lblModInit = new JLabel("Mod. Init:");
		GridBagConstraints gbc_lblModInit = new GridBagConstraints();
		gbc_lblModInit.insets = new Insets(0, 0, 5, 5);
		gbc_lblModInit.gridx = 3;
		gbc_lblModInit.gridy = 1;
		add(lblModInit, gbc_lblModInit);

		spinnerModInit = new JSpinner();
		spinnerModInit.setEnabled(false);
		GridBagConstraints gbc_spinnerModInit = new GridBagConstraints();
		gbc_spinnerModInit.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerModInit.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerModInit.gridx = 4;
		gbc_spinnerModInit.gridy = 1;
		add(spinnerModInit, gbc_spinnerModInit);

		JPanel panelPv = new JPanel();
		panelPv.setBorder(new TitledBorder(null, "Points de vie",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelPv = new GridBagConstraints();
		gbc_panelPv.insets = new Insets(0, 0, 5, 5);
		gbc_panelPv.gridwidth = 2;
		gbc_panelPv.fill = GridBagConstraints.BOTH;
		gbc_panelPv.gridx = 3;
		gbc_panelPv.gridy = 2;
		add(panelPv, gbc_panelPv);
		GridBagLayout gbl_panelPv = new GridBagLayout();
		gbl_panelPv.columnWidths = new int[] { 0, 60, 0 };
		gbl_panelPv.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelPv.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPv.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelPv.setLayout(gbl_panelPv);

		JLabel lblPvTotal = new JLabel("Pv total:");
		GridBagConstraints gbc_lblPvTotal = new GridBagConstraints();
		gbc_lblPvTotal.anchor = GridBagConstraints.EAST;
		gbc_lblPvTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblPvTotal.gridx = 0;
		gbc_lblPvTotal.gridy = 0;
		panelPv.add(lblPvTotal, gbc_lblPvTotal);

		spinnerHpTotal = new JSpinner();
		spinnerHpTotal.setModel(new SpinnerNumberModel(new Integer(10),
				new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerPvTotal = new GridBagConstraints();
		gbc_spinnerPvTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerPvTotal.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPvTotal.gridx = 1;
		gbc_spinnerPvTotal.gridy = 0;
		panelPv.add(spinnerHpTotal, gbc_spinnerPvTotal);

		JLabel lblPvActuel = new JLabel("Pv actuel:");
		GridBagConstraints gbc_lblPvActuel = new GridBagConstraints();
		gbc_lblPvActuel.insets = new Insets(0, 0, 0, 5);
		gbc_lblPvActuel.gridx = 0;
		gbc_lblPvActuel.gridy = 1;
		panelPv.add(lblPvActuel, gbc_lblPvActuel);

		spinnerHpCurrent = new JSpinner();
		spinnerHpCurrent.setModel(new SpinnerNumberModel(new Integer(0), null,
				null, new Integer(1)));
		GridBagConstraints gbc_spinnerPvActuel = new GridBagConstraints();
		gbc_spinnerPvActuel.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerPvActuel.gridx = 1;
		gbc_spinnerPvActuel.gridy = 1;
		panelPv.add(spinnerHpCurrent, gbc_spinnerPvActuel);

		JPanel panelCa = new JPanel();
		panelCa.setBorder(new TitledBorder(null, "Classe d'armure",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelCa = new GridBagConstraints();
		gbc_panelCa.insets = new Insets(0, 0, 5, 0);
		gbc_panelCa.gridwidth = 2;
		gbc_panelCa.fill = GridBagConstraints.BOTH;
		gbc_panelCa.gridx = 5;
		gbc_panelCa.gridy = 2;
		add(panelCa, gbc_panelCa);
		GridBagLayout gbl_panelCa = new GridBagLayout();
		gbl_panelCa.columnWidths = new int[] { 0, 60, 0 };
		gbl_panelCa.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelCa.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelCa.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCa.setLayout(gbl_panelCa);

		JLabel lblNormal = new JLabel("Normal:");
		GridBagConstraints gbc_lblNormal = new GridBagConstraints();
		gbc_lblNormal.anchor = GridBagConstraints.EAST;
		gbc_lblNormal.insets = new Insets(0, 0, 5, 5);
		gbc_lblNormal.gridx = 0;
		gbc_lblNormal.gridy = 0;
		panelCa.add(lblNormal, gbc_lblNormal);

		spinnerAc = new JSpinner();
		spinnerAc.setModel(new SpinnerNumberModel(new Integer(10), new Integer(
				1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerCa = new GridBagConstraints();
		gbc_spinnerCa.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerCa.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerCa.gridx = 1;
		gbc_spinnerCa.gridy = 0;
		panelCa.add(spinnerAc, gbc_spinnerCa);

		JLabel lblDpourvu = new JLabel("D\u00E9pourvu:");
		GridBagConstraints gbc_lblDpourvu = new GridBagConstraints();
		gbc_lblDpourvu.insets = new Insets(0, 0, 5, 5);
		gbc_lblDpourvu.gridx = 0;
		gbc_lblDpourvu.gridy = 1;
		panelCa.add(lblDpourvu, gbc_lblDpourvu);

		spinnerAcFlatFooted = new JSpinner();
		spinnerAcFlatFooted.setModel(new SpinnerNumberModel(new Integer(10),
				new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerDepourvu = new GridBagConstraints();
		gbc_spinnerDepourvu.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerDepourvu.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerDepourvu.gridx = 1;
		gbc_spinnerDepourvu.gridy = 1;
		panelCa.add(spinnerAcFlatFooted, gbc_spinnerDepourvu);

		JLabel lblContact = new JLabel("Contact:");
		GridBagConstraints gbc_lblContact = new GridBagConstraints();
		gbc_lblContact.insets = new Insets(0, 0, 0, 5);
		gbc_lblContact.anchor = GridBagConstraints.EAST;
		gbc_lblContact.gridx = 0;
		gbc_lblContact.gridy = 2;
		panelCa.add(lblContact, gbc_lblContact);

		spinnerAcTouch = new JSpinner();
		spinnerAcTouch.setModel(new SpinnerNumberModel(new Integer(10),
				new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerContact = new GridBagConstraints();
		gbc_spinnerContact.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerContact.gridx = 1;
		gbc_spinnerContact.gridy = 2;
		panelCa.add(spinnerAcTouch, gbc_spinnerContact);

		this.character.addPropertyChangeListener(this);
		characterChange();
	}

	protected void characterChange() {
		this.textFieldName.setText(character.getName());
		// this.spinnerModInit.setValue(character.getInitModifier());

		this.spinnerHpTotal.setValue(character.getTotalHp());
		this.spinnerHpCurrent.setValue(character.getCurrentHp());

		// this.spinnerAc.setValue(character.getAc());
		// this.spinnerAcFlatFooted.setValue(character.getAcFlatFooted());
		// this.spinnerAcTouch.setValue(character.getAcTouch());

		BufferedImage img = ImageUtil.resizeImage(
				character.getCharacterImage(), 120, 120);
		this.btnIcon.setIcon(new ImageIcon(img));
	}

	protected void editPlayerAccess() {
		/*
		 * PanelPlayerAccess panelAccess = new PanelPlayerAccess();
		 * DialogOkCancel<PanelPlayerAccess> dialog = new
		 * DialogOkCancel<PanelPlayerAccess>( null, "Changer les access", true,
		 * panelAccess);
		 * 
		 * panelAccess.setAccess(this.character.getAccess());
		 * 
		 * dialog.setVisible(true);
		 * 
		 * if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
		 * this.character.setAccess(panelAccess.getPlayerAccess()); }
		 */
	}

	public Character getCharacter() {
		return this.character;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		characterChange();
	}
}
