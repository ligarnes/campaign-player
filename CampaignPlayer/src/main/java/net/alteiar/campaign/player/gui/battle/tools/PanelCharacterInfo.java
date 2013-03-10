package net.alteiar.campaign.player.gui.battle.tools;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.element.character.MapElementCharacterClient;

public class PanelCharacterInfo extends JPanel /*
												 * implements
												 * ICharacterCombatObserver,
												 * IBattleObserver
												 */{
	private static final long serialVersionUID = 1L;

	private final JTextField textFieldName;
	private final JTextField textFieldAc;
	private final JTextField textFieldAcFlatFooted;
	private final JTextField textFieldAcTouch;

	private final JLabel labelInit;
	private final JTextField textFieldInit;

	private MapElementCharacterClient current;

	public PanelCharacterInfo(final BattleClient battle) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		add(lblNom, gbc_lblNom);

		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(20);

		labelInit = new JLabel("Init:");
		GridBagConstraints gbc_labelInit = new GridBagConstraints();
		gbc_labelInit.anchor = GridBagConstraints.EAST;
		gbc_labelInit.insets = new Insets(0, 0, 5, 5);
		gbc_labelInit.gridx = 0;
		gbc_labelInit.gridy = 1;
		add(labelInit, gbc_labelInit);

		textFieldInit = new JTextField();
		textFieldInit.setEditable(false);
		textFieldInit.setColumns(20);
		GridBagConstraints gbc_textFieldInit = new GridBagConstraints();
		gbc_textFieldInit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldInit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInit.gridx = 1;
		gbc_textFieldInit.gridy = 1;
		add(textFieldInit, gbc_textFieldInit);

		JLabel lblCa = new JLabel("CA:");
		GridBagConstraints gbc_lblCa = new GridBagConstraints();
		gbc_lblCa.anchor = GridBagConstraints.EAST;
		gbc_lblCa.insets = new Insets(0, 0, 5, 5);
		gbc_lblCa.gridx = 0;
		gbc_lblCa.gridy = 3;
		add(lblCa, gbc_lblCa);

		textFieldAc = new JTextField();
		textFieldAc.setEditable(false);
		GridBagConstraints gbc_textFieldCa = new GridBagConstraints();
		gbc_textFieldCa.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldCa.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCa.gridx = 1;
		gbc_textFieldCa.gridy = 3;
		add(textFieldAc, gbc_textFieldCa);
		textFieldAc.setColumns(10);

		JLabel lblCaDpourvu = new JLabel("CA Dépourvu:");
		GridBagConstraints gbc_lblCaDpourvu = new GridBagConstraints();
		gbc_lblCaDpourvu.anchor = GridBagConstraints.EAST;
		gbc_lblCaDpourvu.insets = new Insets(0, 0, 5, 5);
		gbc_lblCaDpourvu.gridx = 0;
		gbc_lblCaDpourvu.gridy = 4;
		add(lblCaDpourvu, gbc_lblCaDpourvu);

		textFieldAcFlatFooted = new JTextField();
		textFieldAcFlatFooted.setEditable(false);
		GridBagConstraints gbc_textFieldCaFlatFooted = new GridBagConstraints();
		gbc_textFieldCaFlatFooted.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldCaFlatFooted.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCaFlatFooted.gridx = 1;
		gbc_textFieldCaFlatFooted.gridy = 4;
		add(textFieldAcFlatFooted, gbc_textFieldCaFlatFooted);
		textFieldAcFlatFooted.setColumns(10);

		JLabel lblCaContact = new JLabel("CA Contact:");
		GridBagConstraints gbc_lblCaContact = new GridBagConstraints();
		gbc_lblCaContact.insets = new Insets(0, 0, 0, 5);
		gbc_lblCaContact.anchor = GridBagConstraints.EAST;
		gbc_lblCaContact.gridx = 0;
		gbc_lblCaContact.gridy = 5;
		add(lblCaContact, gbc_lblCaContact);

		textFieldAcTouch = new JTextField();
		textFieldAcTouch.setEditable(false);
		GridBagConstraints gbc_textFieldCaTouch = new GridBagConstraints();
		gbc_textFieldCaTouch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCaTouch.gridx = 1;
		gbc_textFieldCaTouch.gridy = 5;
		add(textFieldAcTouch, gbc_textFieldCaTouch);
		textFieldAcTouch.setColumns(10);

		/*
		 * battle.addBattleListener(this); for (final ICharacterCombatClient
		 * characterCombat : battle .getAllCharacter()) {
		 * characterCombat.addCharacterCombatListener(this); }
		 */
	}

	public void updateCharacterView() {
		CharacterClient character = current.getCharacter();
		this.textFieldName.setText(character.getName());

		// this.textFieldInit.setText(current.getInitiative().toString());

		this.textFieldAc.setText(character.getAc().toString());
		this.textFieldAcFlatFooted.setText(character.getAcFlatFooted()
				.toString());
		this.textFieldAcTouch.setText(character.getAcTouch().toString());

		this.revalidate();
		this.repaint();
	}

	/*
	 * @Override public void characterChange(CharacterCombatClient character) {
	 * if (character.isHighlighted() &&
	 * CampaignClient.INSTANCE.canAccess(character.getCharacter())) { current =
	 * character; updateCharacterView(); } }
	 * 
	 * @Override public void highLightChange(CharacterCombatClient character,
	 * Boolean isHighlighted) { if (character.isHighlighted() &&
	 * CampaignClient.INSTANCE.canAccess(character.getCharacter())) { current =
	 * character; updateCharacterView(); } }
	 * 
	 * @Override public void visibilityChange(ICharacterCombatClient character)
	 * { // Do not care (for the moment) }
	 * 
	 * @Override public void initiativeChange(CharacterCombatClient character) {
	 * 
	 * if (character.isHighlighted() &&
	 * CampaignClient.INSTANCE.canAccess(character.getCharacter())) { current =
	 * character; updateCharacterView(); } }
	 * 
	 * @Override public void characterAdded(BattleClient battle,
	 * ICharacterCombatClient character) {
	 * character.addCharacterCombatListener(this); }
	 * 
	 * @Override public void characterRemove(BattleClient battle,
	 * ICharacterCombatClient character) {
	 * character.removeCharacterCombatListener(this); }
	 * 
	 * @Override public void turnChanged(BattleClient battle) { // Do not care }
	 * 
	 * @Override public void positionChanged(ICharacterCombatClient character) {
	 * // Do not care }
	 * 
	 * @Override public void rotationChanged(ICharacterCombatClient character) {
	 * // Do not care }
	 */
}
