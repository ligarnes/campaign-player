package shadowrun.gui.document.character.monitor;

import java.awt.Dimension;

import javax.swing.JPanel;

class NoMonitor extends JPanel {
	private static final long serialVersionUID = 1L;

	public NoMonitor() {
		this.setPreferredSize(new Dimension(30, 30));
		this.setMinimumSize(new Dimension(30, 30));
		this.setMaximumSize(new Dimension(30, 30));

	}
}