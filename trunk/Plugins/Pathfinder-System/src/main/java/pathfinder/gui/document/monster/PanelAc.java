package pathfinder.gui.document.monster;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

public class PanelAc extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JSpinner spinnerAc;
	private final JSpinner spinnerAcFlat;
	private final JSpinner spinnerAcTouch;

	public PanelAc() {
		setBorder(new TitledBorder(null, "Classe d'armure",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));

		JLabel lblCa = new JLabel("CA:");
		add(lblCa);

		spinnerAc = new JSpinner();
		spinnerAc.setModel(new SpinnerNumberModel(10, 0, 110, 1));
		add(spinnerAc);

		JLabel lblCaDpourvu = new JLabel("Ca Dépourvu:");
		add(lblCaDpourvu);

		spinnerAcFlat = new JSpinner();
		spinnerAcFlat.setModel(new SpinnerNumberModel(10, 0, 110, 1));
		add(spinnerAcFlat);

		JLabel lblCaContact = new JLabel("Ca Contact:");
		add(lblCaContact);

		spinnerAcTouch = new JSpinner();
		spinnerAcTouch.setModel(new SpinnerNumberModel(10, 0, 110, 1));
		add(spinnerAcTouch);
	}

	public void reset() {
		spinnerAc.setValue(10);
		spinnerAcFlat.setValue(10);
		spinnerAcTouch.setValue(10);
	}

	public Integer getAc() {
		return (Integer) spinnerAc.getValue();
	}

	public Integer getAcFlatFooted() {
		return (Integer) spinnerAcFlat.getValue();
	}

	public Integer getAcTouch() {
		return (Integer) spinnerAcTouch.getValue();
	}
}
