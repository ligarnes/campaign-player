package pathfinder.gui.document.builder.spell;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.alteiar.component.MyCombobox;
import net.alteiar.tools.CompositeListFilter;
import net.alteiar.tools.ListFilter;
import pathfinder.bean.spell.Spell;
import pathfinder.bean.spell.SpellManager;
import pathfinder.bean.spell.filter.MaxLevelListFilter;
import pathfinder.bean.spell.filter.MinLevelListFilter;

public class PanelSpellFilter extends JPanel {

	private static final long serialVersionUID = 1L;

	private final MyCombobox<String> comboBoxClasses;
	private final JSpinner spinnerMin;
	private final JSpinner spinnerMax;

	public PanelSpellFilter() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblClasse = new JLabel("Classe");
		GridBagConstraints gbc_lblClasse = new GridBagConstraints();
		gbc_lblClasse.insets = new Insets(0, 0, 5, 5);
		gbc_lblClasse.anchor = GridBagConstraints.EAST;
		gbc_lblClasse.gridx = 0;
		gbc_lblClasse.gridy = 0;
		add(lblClasse, gbc_lblClasse);

		comboBoxClasses = new MyCombobox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(comboBoxClasses, gbc_comboBox);

		JLabel lblNiveauMinimum = new JLabel("Niveau minimum:");
		GridBagConstraints gbc_lblNiveauMinimum = new GridBagConstraints();
		gbc_lblNiveauMinimum.insets = new Insets(0, 0, 5, 5);
		gbc_lblNiveauMinimum.gridx = 0;
		gbc_lblNiveauMinimum.gridy = 1;
		add(lblNiveauMinimum, gbc_lblNiveauMinimum);

		spinnerMin = new JSpinner();
		spinnerMin.setModel(new SpinnerNumberModel(0, 0, 9, 1));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 1;
		add(spinnerMin, gbc_spinner);

		JLabel lblNiveauMaximum = new JLabel("Niveau maximum:");
		GridBagConstraints gbc_lblNiveauMaximum = new GridBagConstraints();
		gbc_lblNiveauMaximum.insets = new Insets(0, 0, 0, 5);
		gbc_lblNiveauMaximum.gridx = 0;
		gbc_lblNiveauMaximum.gridy = 2;
		add(lblNiveauMaximum, gbc_lblNiveauMaximum);

		spinnerMax = new JSpinner();
		spinnerMax.setModel(new SpinnerNumberModel(9, 0, 9, 1));
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 2;
		add(spinnerMax, gbc_spinner_1);

		comboBoxClasses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireFilterChanged();
			}
		});

		ChangeListener listener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				fireFilterChanged();
			}
		};
		spinnerMin.addChangeListener(listener);
		spinnerMax.addChangeListener(listener);
	}

	public void reset() {
		for (String classe : SpellManager.getInstance().getClasses()) {
			comboBoxClasses.addItem(classe);
		}

		spinnerMin.setValue(0);
		spinnerMax.setValue(9);
	}

	public String getSelectedClasse() {
		return comboBoxClasses.getSelectedItem();
	}

	public ListFilter<Spell> getFilter() {
		String className = getSelectedClasse();
		Integer minLevel = (Integer) spinnerMin.getValue();
		Integer maxLevel = (Integer) spinnerMax.getValue();

		CompositeListFilter<Spell> filter = new CompositeListFilter<Spell>();
		filter.addFilter(new MinLevelListFilter(className, minLevel));
		filter.addFilter(new MaxLevelListFilter(className, maxLevel));

		return filter;
	}

	public void addActionListener(ActionListener listener) {
		listenerList.add(ActionListener.class, listener);
	}

	public void removeActionListener(ActionListener listener) {
		listenerList.remove(ActionListener.class, listener);
	}

	protected void fireFilterChanged() {
		ActionListener[] listeners = listenerList
				.getListeners(ActionListener.class);

		for (ActionListener actionListener : listeners) {
			actionListener.actionPerformed(new ActionEvent(this,
					ActionEvent.ACTION_FIRST, "filterChanged"));
		}
	}

}
