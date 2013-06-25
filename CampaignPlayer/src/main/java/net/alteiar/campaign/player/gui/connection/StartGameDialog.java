package net.alteiar.campaign.player.gui.connection;

import java.awt.Frame;

import javax.swing.JDialog;

public class StartGameDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private Boolean readyToStart;

	public StartGameDialog(Frame owner, String title, Boolean modal) {
		super(owner, title, modal);
		this.readyToStart = false;
		this.getContentPane().add(new PanelEnterGame(this));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
	}

	public void changeState(PanelStartGameDialog newPanel) {
		getContentPane().removeAll();
		getContentPane().add(newPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.invalidate();
		this.validate();
		this.repaint();
	}

	public void startApplication() {
		readyToStart = true;
		this.dispose();
	}

	public void quitApplication() {
		readyToStart = false;
		this.dispose();
	}

	public Boolean isReadyToStart() {
		return readyToStart;
	}

}
