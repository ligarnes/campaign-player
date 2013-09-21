package pathfinder.gui.document.monster;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class PanelSaveRoll extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JSpinner spinnerAc;
	private final JSpinner spinnerAcFlat;
	private final JSpinner spinnerAcTouch;

	public PanelSaveRoll() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Jet de sauvegarde", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));

		JLabel lblCa = new JLabel("Réflexe:");
		add(lblCa);

		spinnerAc = new JSpinner();
		spinnerAc.setModel(new SpinnerNumberModel(0, -10, 110, 1));
		add(spinnerAc);

		JLabel lblCaDpourvu = new JLabel("Vigueur");
		add(lblCaDpourvu);

		spinnerAcFlat = new JSpinner();
		spinnerAcFlat.setModel(new SpinnerNumberModel(0, -10, 110, 1));
		add(spinnerAcFlat);

		JLabel lblCaContact = new JLabel("Volonté");
		add(lblCaContact);

		spinnerAcTouch = new JSpinner();
		spinnerAcTouch.setModel(new SpinnerNumberModel(0, -10, 110, 1));
		add(spinnerAcTouch);
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
