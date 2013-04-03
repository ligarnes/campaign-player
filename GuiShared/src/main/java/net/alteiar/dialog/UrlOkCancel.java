package net.alteiar.dialog;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class UrlOkCancel extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private final JTextField textFieldUrl;

	public UrlOkCancel() {
		textFieldUrl = new JTextField(60);
		this.add(textFieldUrl);
	}

	public URL getUrl() {
		URL url = null;
		try {
			url = new URL(textFieldUrl.getText());
		} catch (MalformedURLException e) {
			// Should never happen because of data validations
		}
		return url;
	}

	@Override
	public Boolean isDataValid() {
		Boolean isDataValid = true;
		try {
			URL url = new URL(textFieldUrl.getText());
		} catch (MalformedURLException e) {
			isDataValid = false;
		}
		return isDataValid ? !textFieldUrl.getText().isEmpty() : isDataValid;
	}

	@Override
	public String getInvalidMessage() {
		String msg = "le chemin est invalide";
		if (textFieldUrl.getText().isEmpty()) {
			msg = "Le chemin de l'image ne peut pas être vide";
		}
		return msg;
	}

}
