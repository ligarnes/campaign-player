package pathfinder.gui.general;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.CampaignAdapter;
import net.alteiar.CampaignClient;
import net.alteiar.documents.character.Character;
import pathfinder.character.PathfinderCharacter;
import pathfinder.gui.adapter.CharacterAdapter;

public class PanelCharacterInfo extends JPanel implements
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private PathfinderCharacter character;

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

			this.setPreferredSize(new Dimension(180, 20));
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

			int widthLife = 180;
			int heightLife = 20;

			Float ratio = Math.min(1.0f, currentHp / (float) totalHp);
			if (currentHp > 0) {
				Color hp = new Color(1.0f - ratio, ratio, 0);
				g.setColor(hp);
				g.fillRect(xLife, yLife, (int) (widthLife * ratio), heightLife);
			}
			g.setColor(Color.BLACK);
			g.drawRect(xLife, yLife, widthLife - 1, heightLife - 1);
			g.drawString(currentHp + "/" + totalHp, 80, 15);
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

		CampaignClient.getInstance().addCampaignListener(new CampaignAdapter() {
			@Override
			public void characterAdded(Character character) {
				PathfinderCharacter bean = CampaignClient.getInstance()
						.getBean(character.getId());
				comboBox.addItem(new CharacterAdapter(bean));
			}

			@Override
			public void characterRemoved(Character character) {
				comboBox.removeItem(new CharacterAdapter(
						(PathfinderCharacter) character));
			}
		});

		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				comboboxChange();
			}
		});

		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		add(lblNom, gbc_lblNom);

		// textFieldName = new JTextField();
		// textFieldName.setEditable(false);
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 1;
		add(comboBox, gbc_textFieldName);
		// textFieldName.setColumns(20);

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

		JLabel lblCaDpourvu = new JLabel("CA D\u00E9pourvu:");
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

	private void comboboxChange() {
		if (this.character != null) {
			this.character.removePropertyChangeListener(this);
		}
		if (comboBox.getItemCount() > 0) {
			character = ((CharacterAdapter) comboBox.getSelectedItem())
					.getCharacter();
			this.character.addPropertyChangeListener(this);
			updateCharacterView();
		}
	}

	public void updateCharacterView() {

		// this.textFieldName.setText(character.getName());

		this.healthBar.setCurrentHp(character.getCurrentHp());
		this.healthBar.setTotalHp(character.getTotalHp());

		// this.textFieldAc.setText(character.getAc().toString());
		// this.textFieldAcFlatFooted.setText(character.getAcFlatFooted()
		// .toString());
		// this.textFieldAcTouch.setText(character.getAcTouch().toString());

		this.revalidate();
		this.repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		updateCharacterView();
	}
}
