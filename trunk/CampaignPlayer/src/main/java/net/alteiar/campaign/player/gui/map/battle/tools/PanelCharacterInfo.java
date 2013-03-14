package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.campaign.player.gui.adapter.CharacterAdapter;
import net.alteiar.client.CampaignAdapter;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.character.ICharacterClientListener;

public class PanelCharacterInfo extends JPanel implements
		ICharacterClientListener {
	private static final long serialVersionUID = 1L;

	private final JTextField textFieldName;
	private final JTextField textFieldAc;
	private final JTextField textFieldAcFlatFooted;
	private final JTextField textFieldAcTouch;

	private final JLabel labelInit;
	private final HealthBar healthBar;

	private final JComboBox<CharacterAdapter> comboBox;

	private class HealthBar extends JPanel {
		private static final long serialVersionUID = 1L;

		private Integer totalHp;
		private Integer currentHp;

		public HealthBar(Integer total, Integer current) {
			this.totalHp = total;
			this.currentHp = current;

			this.setPreferredSize(new Dimension(80, 20));
		}

		public void setCurrentHp(Integer currentHp) {
			this.currentHp = currentHp;
			this.revalidate();
			this.repaint();
		}

		public void setTotalHp(Integer totalHp) {
			this.totalHp = totalHp;
			this.revalidate();
			this.repaint();
		}

		@Override
		public void paint(Graphics g) {
			int xLife = 0;
			int yLife = 0;

			int widthLife = 80;
			int heightLife = 20;

			Float ratio = Math.min(1.0f, currentHp / (float) totalHp);
			if (currentHp > 0) {
				Color hp = new Color(1.0f - ratio, ratio, 0);
				g.setColor(hp);
				g.fillRect(xLife, yLife, (int) (widthLife * ratio), heightLife);
			}
			g.setColor(Color.BLACK);
			g.drawRect(xLife, yLife, widthLife - 1, heightLife - 1);
			g.drawString(currentHp + "/" + totalHp, 40, 10);
		}
	}

	public PanelCharacterInfo() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		comboBox = new JComboBox<CharacterAdapter>(
				CharacterAdapter.getCharacters());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(comboBox, gbc_comboBox);

		CampaignClient.getInstance().addCampaignListener(new CampaignAdapter() {
			@Override
			public void characterAdded(CharacterClient character) {
				comboBox.addItem(new CharacterAdapter(character));
			}

			@Override
			public void characterRemoved(CharacterClient character) {
				comboBox.removeItem(new CharacterAdapter(character));
			}
		});

		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateCharacterView();
			}
		});

		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		add(lblNom, gbc_lblNom);

		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 1;
		add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(20);

		labelInit = new JLabel("PV:");
		GridBagConstraints gbc_labelInit = new GridBagConstraints();
		gbc_labelInit.anchor = GridBagConstraints.EAST;
		gbc_labelInit.insets = new Insets(0, 0, 5, 5);
		gbc_labelInit.gridx = 0;
		gbc_labelInit.gridy = 2;
		add(labelInit, gbc_labelInit);

		healthBar = new HealthBar(0, 0);
		GridBagConstraints gbc_textFieldInit = new GridBagConstraints();
		gbc_textFieldInit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldInit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInit.gridx = 1;
		gbc_textFieldInit.gridy = 2;
		add(healthBar, gbc_textFieldInit);

		JLabel lblCa = new JLabel("CA:");
		GridBagConstraints gbc_lblCa = new GridBagConstraints();
		gbc_lblCa.anchor = GridBagConstraints.EAST;
		gbc_lblCa.insets = new Insets(0, 0, 5, 5);
		gbc_lblCa.gridx = 0;
		gbc_lblCa.gridy = 4;
		add(lblCa, gbc_lblCa);

		textFieldAc = new JTextField();
		textFieldAc.setEditable(false);
		GridBagConstraints gbc_textFieldCa = new GridBagConstraints();
		gbc_textFieldCa.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldCa.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCa.gridx = 1;
		gbc_textFieldCa.gridy = 4;
		add(textFieldAc, gbc_textFieldCa);
		textFieldAc.setColumns(10);

		JLabel lblCaDpourvu = new JLabel("CA Dépourvu:");
		GridBagConstraints gbc_lblCaDpourvu = new GridBagConstraints();
		gbc_lblCaDpourvu.anchor = GridBagConstraints.EAST;
		gbc_lblCaDpourvu.insets = new Insets(0, 0, 5, 5);
		gbc_lblCaDpourvu.gridx = 0;
		gbc_lblCaDpourvu.gridy = 5;
		add(lblCaDpourvu, gbc_lblCaDpourvu);

		textFieldAcFlatFooted = new JTextField();
		textFieldAcFlatFooted.setEditable(false);
		GridBagConstraints gbc_textFieldCaFlatFooted = new GridBagConstraints();
		gbc_textFieldCaFlatFooted.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldCaFlatFooted.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCaFlatFooted.gridx = 1;
		gbc_textFieldCaFlatFooted.gridy = 5;
		add(textFieldAcFlatFooted, gbc_textFieldCaFlatFooted);
		textFieldAcFlatFooted.setColumns(10);

		JLabel lblCaContact = new JLabel("CA Contact:");
		GridBagConstraints gbc_lblCaContact = new GridBagConstraints();
		gbc_lblCaContact.insets = new Insets(0, 0, 0, 5);
		gbc_lblCaContact.anchor = GridBagConstraints.EAST;
		gbc_lblCaContact.gridx = 0;
		gbc_lblCaContact.gridy = 6;
		add(lblCaContact, gbc_lblCaContact);

		textFieldAcTouch = new JTextField();
		textFieldAcTouch.setEditable(false);
		GridBagConstraints gbc_textFieldCaTouch = new GridBagConstraints();
		gbc_textFieldCaTouch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCaTouch.gridx = 1;
		gbc_textFieldCaTouch.gridy = 6;
		add(textFieldAcTouch, gbc_textFieldCaTouch);
		textFieldAcTouch.setColumns(10);
	}

	public void updateCharacterView() {
		CharacterClient character = ((CharacterAdapter) comboBox
				.getSelectedItem()).getCharacter();

		character.addCharacterListener(this);
		this.textFieldName.setText(character.getName());

		this.healthBar.setCurrentHp(character.getCurrentHp());
		this.healthBar.setTotalHp(character.getTotalHp());

		this.textFieldAc.setText(character.getAc().toString());
		this.textFieldAcFlatFooted.setText(character.getAcFlatFooted()
				.toString());
		this.textFieldAcTouch.setText(character.getAcTouch().toString());

		this.revalidate();
		this.repaint();
	}

	@Override
	public void characterChanged(CharacterClient character) {
		updateCharacterView();
	}

	@Override
	public void imageLoaded(CharacterClient character) {

	}
}
