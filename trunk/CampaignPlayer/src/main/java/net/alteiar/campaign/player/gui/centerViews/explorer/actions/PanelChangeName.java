package net.alteiar.campaign.player.gui.centerViews.explorer.actions;

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.dialog.PanelOkCancel;

public class PanelChangeName extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private final JTextField TextFieldDocumentName;

	public PanelChangeName(String documentName) {
		this.TextFieldDocumentName = new JTextField(25);
		this.TextFieldDocumentName.setText(documentName);

		this.add(this.TextFieldDocumentName);
	}

	public String getDocumentName() {
		return TextFieldDocumentName.getText();
	}

	@Override
	public Boolean isDataValid() {
		return true;
	}

	@Override
	public String getInvalidMessage() {
		return null;
	}

}