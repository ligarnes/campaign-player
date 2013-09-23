package shadowrun.gui.document.character.monitor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class NoDamageMonitor extends JPanel {
	private static final long serialVersionUID = 1L;

	public NoDamageMonitor() {
		this.setPreferredSize(new Dimension(30, 30));
		this.setMinimumSize(new Dimension(30, 30));
		this.setMaximumSize(new Dimension(30, 30));

		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
}