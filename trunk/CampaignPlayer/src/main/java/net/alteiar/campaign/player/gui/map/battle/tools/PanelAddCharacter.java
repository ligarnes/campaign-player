package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.alteiar.campaign.player.gui.tools.PanelAlwaysValidOkCancel;
import net.alteiar.character.CharacterBean;

public class PanelAddCharacter extends PanelAlwaysValidOkCancel {
	private static final long serialVersionUID = 1L;
	private static Random RANDOM = new Random(new Date().getTime());

	private final JComboBox<CharacterAdapter> comboBoxCharacter;
	private final JLabel lblInitValue;
	private final JLabel lblTotal;
	private final JSpinner spinnerDice;
	private final JCheckBox chckbxVisible;

	private class CharacterAdapter {
		private final CharacterBean character;

		public CharacterAdapter(CharacterBean character) {
			this.character = character;
		}

		public CharacterBean getCharacter() {
			return this.character;
		}

		@Override
		public String toString() {
			return this.character.getVisibleName();
		}
	}

	public PanelAddCharacter(CharacterBean[] lstCharacter, Boolean isMonster) {
		CharacterAdapter[] lst = new CharacterAdapter[lstCharacter.length];
		int i = 0;
		for (CharacterBean character : lstCharacter) {
			lst[i] = new CharacterAdapter(character);
			++i;
		}

		comboBoxCharacter = new JComboBox<CharacterAdapter>(lst);
		add(comboBoxCharacter);

		JLabel lblInit = new JLabel("Initiative:");
		add(lblInit);

		spinnerDice = new JSpinner();
		spinnerDice.setModel(new SpinnerNumberModel(RANDOM.nextInt(20) + 1,
				null, null, 1));
		add(spinnerDice);

		JLabel label = new JLabel("+");
		add(label);

		lblInitValue = new JLabel("0");
		add(lblInitValue);

		JLabel label1 = new JLabel("=");
		add(label1);

		lblTotal = new JLabel("total");
		add(lblTotal);

		chckbxVisible = new JCheckBox("Visible");
		if (isMonster) {
			add(chckbxVisible);
		}

		this.comboBoxCharacter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setInitiative();
			}
		});

		this.spinnerDice.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				setInitiative();
			}
		});
		setInitiative();
	}

	private void setInitiative() {
		CharacterAdapter character = (CharacterAdapter) comboBoxCharacter
				.getSelectedItem();
		Integer initMod = character.getCharacter().getInitModifier();
		Integer total = (Integer) spinnerDice.getValue() + initMod;
		this.lblInitValue.setText(initMod.toString());
		this.lblTotal.setText(total.toString());
	}

	public CharacterBean getCharacter() {
		return ((CharacterAdapter) this.comboBoxCharacter.getSelectedItem())
				.getCharacter();
	}

	public Integer getInitiative() {
		return (Integer) this.spinnerDice.getValue();
	}

	public Boolean isVisibleForAllPlayer() {
		return chckbxVisible.isSelected();
	}
}
