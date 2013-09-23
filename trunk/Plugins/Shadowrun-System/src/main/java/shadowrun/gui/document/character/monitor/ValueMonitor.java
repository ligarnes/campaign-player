package shadowrun.gui.document.character.monitor;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

class ValueMonitor extends JPanel {
	private static final long serialVersionUID = 1L;

	public ValueMonitor(String val) {
		this.setPreferredSize(new Dimension(30, 30));
		this.setMinimumSize(new Dimension(30, 30));
		this.setMaximumSize(new Dimension(30, 30));

		this.add(new JLabel(val));
	}
}