package shadowrun.gui.general;

import generic.DocumentTypeConstant;

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

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.CampaignAdapter;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.component.MyCombobox;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDocument;
import shadowrun.bean.unit.ShadowrunCharacter;
import shadowrun.gui.adapter.CharacterAdapter;

public class PanelCharacterInfo extends JPanel implements
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private BeanDocument characterDocument;

	private final HealthBar healthBarPhysical;
	private final HealthBar healthBarStun;

	private final MyCombobox<CharacterAdapter> comboBox;

	private class HealthBar extends JPanel {
		private static final long serialVersionUID = 1L;

		private Integer totalHp;
		private Integer currentHp;

		private final Color maxColor;
		private final Color minColor;

		public HealthBar(Integer total, Integer current, Color maxColor) {
			this.totalHp = total;
			this.currentHp = current;

			this.maxColor = maxColor;
			minColor = Color.RED;

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
				Color hp = generateColor(ratio);
				g.setColor(hp);
				g.fillRect(xLife, yLife, (int) (widthLife * ratio), heightLife);
			}
			g.setColor(Color.BLACK);
			g.drawRect(xLife, yLife, widthLife - 1, heightLife - 1);
			g.drawString(currentHp + "/" + totalHp, 80, 15);
		}

		private Color generateColor(Float ratio) {
			int red = getRatioValue(maxColor.getRed(), minColor.getRed(), ratio);
			int green = getRatioValue(maxColor.getGreen(), minColor.getGreen(),
					ratio);
			int blue = getRatioValue(maxColor.getBlue(), minColor.getBlue(),
					ratio);

			return new Color(red, green, blue);
		}

		private int getRatioValue(int max, int min, Float ratio) {
			int val = (int) ((max - min) * ratio);

			if (val < 0) {
				val = 255 + val;
			}

			return val;
		}
	}

	private Boolean isCharacter(BeanDocument doc) {
		return doc.getDocumentType().equals(DocumentTypeConstant.CHARACTER);
	}

	public void addCharacter(final BeanDocument doc) {
		if (isCharacter(doc)) {
			CharacterAdapter adapter = new CharacterAdapter(doc);
			comboBox.addItem(adapter);

			if (comboBox.getItemCount() < 2) {
				comboBox.setSelectedItem(adapter);
				comboboxChange();
			}
		}
	}

	public void removeCharacter(BeanDocument doc) {
		if (isCharacter(doc)) {
			comboBox.removeItem(new CharacterAdapter(doc));
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

		comboBox = new MyCombobox<CharacterAdapter>(
				CharacterAdapter.getCharacters());

		CampaignClient.getInstance().addCampaignListener(new CampaignAdapter() {

			@Override
			public void beanAdded(BeanBasicDocument bean) {
				if (!bean.isDirectory()) {
					addCharacter((BeanDocument) bean);
				}
			}

			@Override
			public void beanRemoved(BeanBasicDocument bean) {
				if (!bean.isDirectory()) {
					removeCharacter((BeanDocument) bean);
				}
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

		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 1;
		add(comboBox, gbc_textFieldName);

		JLabel lblPhysical = new JLabel("Physique");
		GridBagConstraints gbc_textFieldInit = new GridBagConstraints();
		gbc_textFieldInit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldInit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInit.gridx = 0;
		gbc_textFieldInit.gridy = 2;
		add(lblPhysical, gbc_textFieldInit);

		healthBarPhysical = new HealthBar(0, 0, Color.GREEN);
		gbc_textFieldInit = new GridBagConstraints();
		gbc_textFieldInit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldInit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInit.gridx = 1;
		gbc_textFieldInit.gridy = 2;
		add(healthBarPhysical, gbc_textFieldInit);

		JLabel lblStun = new JLabel("Étourdissant");
		gbc_textFieldInit = new GridBagConstraints();
		gbc_textFieldInit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldInit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInit.gridx = 0;
		gbc_textFieldInit.gridy = 3;
		add(lblStun, gbc_textFieldInit);

		healthBarStun = new HealthBar(0, 0, Color.BLUE);
		gbc_textFieldInit = new GridBagConstraints();
		gbc_textFieldInit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldInit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInit.gridx = 1;
		gbc_textFieldInit.gridy = 3;
		add(healthBarStun, gbc_textFieldInit);

		comboboxChange();
	}

	private void comboboxChange() {
		if (this.characterDocument != null) {
			this.characterDocument.getBean().removePropertyChangeListener(this);
		}

		if (comboBox.getItemCount() > 0 && comboBox.getSelectedItem() != null) {
			characterDocument = comboBox.getSelectedItem().getCharacter();
			this.characterDocument.getBean().addPropertyChangeListener(this);
			updateCharacterView();
		}
	}

	private ShadowrunCharacter getCharacter() {
		return CampaignClient.getInstance().getBean(
				characterDocument.getBeanId());
	}

	public void updateCharacterView() {
		ShadowrunCharacter character = getCharacter();
		if (character != null) {
			int totalPhysical = character.getPhysicalPoint();
			int totalStun = character.getStunPoint();

			this.healthBarPhysical.setTotalHp(totalPhysical);
			this.healthBarStun.setTotalHp(totalStun);

			this.healthBarPhysical.setCurrentHp(totalPhysical
					- character.getPhysicalDamage());
			this.healthBarStun.setCurrentHp(totalStun
					- character.getStunDamage());
			this.revalidate();
			this.repaint();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		updateCharacterView();
	}
}
