package net.alteiar.campaign.player.gui;

import java.awt.Dimension;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelLog extends JPanel {

	private final JTextArea area;

	private static final long serialVersionUID = 1L;

	public PanelLog(Logger log) {
		area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(600, 400));
		scroll.setMaximumSize(new Dimension(600, 400));
		scroll.setMinimumSize(new Dimension(600, 400));
		this.add(scroll);

		log.addHandler(new PanelHandler());
	}

	private class PanelHandler extends Handler {
		@Override
		public void publish(LogRecord record) {
			record.getLoggerName();
			record.getLevel().getLocalizedName();
			record.getMessage();

			area.append(record.getLevel().getLocalizedName() + ": "
					+ record.getMessage());
			area.append(System.getProperty("line.separator"));
		}

		@Override
		public void flush() {
		}

		@Override
		public void close() throws SecurityException {
		}

	}

}
