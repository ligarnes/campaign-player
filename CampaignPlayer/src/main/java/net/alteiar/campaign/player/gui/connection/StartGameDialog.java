package net.alteiar.campaign.player.gui.connection;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class StartGameDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private Boolean startApplication;

	public StartGameDialog(Frame owner, String title, Boolean modal) {
		super(owner, title, modal);
		getContentPane().add(new PanelEnterGame(this));
		startApplication = false;
	}

	public void changeState(JPanel newPanel) {
		getContentPane().removeAll();
		getContentPane().add(newPanel);
		this.revalidate();
		this.repaint();
	}

	public void startApplication() {
		startApplication = true;
		this.dispose();
	}

	public void quitApplication() {
		startApplication = false;
		this.dispose();
	}

	public Boolean getStartApplication() {
		return startApplication;
	}

}
