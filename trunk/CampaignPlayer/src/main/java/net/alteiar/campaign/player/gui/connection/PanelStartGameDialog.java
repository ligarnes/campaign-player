package net.alteiar.campaign.player.gui.connection;

import javax.swing.JPanel;

public abstract class PanelStartGameDialog extends JPanel {
	private static final long serialVersionUID = -6625096424819076011L;

	private final StartGameDialog startGameDialog;
	private final PanelStartGameDialog previous;

	public PanelStartGameDialog(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		this.startGameDialog = startGameDialog;
		this.previous = previous;
	}

	protected StartGameDialog getDialog() {
		return startGameDialog;
	}

	protected abstract PanelStartGameDialog getNext();

	protected void previousState() {
		if (previous != null) {
			this.startGameDialog.changeState(previous);
		} else {
			stopApplication();
		}
	}

	protected void nextState() {
		PanelStartGameDialog next = getNext();
		if (next != null) {
			this.startGameDialog.changeState(next);
		} else {
			this.startApplication();
		}
	}

	private void stopApplication() {
		this.startGameDialog.quitApplication();
	}

	private void startApplication() {
		this.startGameDialog.startApplication();
	}
}
