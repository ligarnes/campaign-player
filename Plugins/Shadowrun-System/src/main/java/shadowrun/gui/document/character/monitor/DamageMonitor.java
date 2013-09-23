package shadowrun.gui.document.character.monitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class DamageMonitor extends JPanel {
	private static final long serialVersionUID = 1L;

	public DamageMonitor() {
		this.setPreferredSize(new Dimension(30, 30));
		this.setMinimumSize(new Dimension(30, 30));
		this.setMaximumSize(new Dimension(30, 30));

		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(5, 5, 20, 20);
	}

}