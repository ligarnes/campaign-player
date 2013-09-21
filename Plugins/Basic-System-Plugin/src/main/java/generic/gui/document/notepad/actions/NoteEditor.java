package generic.gui.document.notepad.actions;

import javax.swing.JTextArea;

public class NoteEditor {

	private final JTextArea textArea;

	public NoteEditor(JTextArea textArea) {
		this.textArea = textArea;
	}

	public void insertText(String text) {
		StringBuilder alltext = new StringBuilder(textArea.getText());

		int caretPos = textArea.getCaretPosition();

		alltext.insert(caretPos, text);
		textArea.setText(alltext.toString());
		textArea.requestFocus();
	}

	public String getSelectedText() {
		int selectedBegin = textArea.getSelectionStart();
		int selectedEnd = textArea.getSelectionEnd();

		return textArea.getText().substring(selectedBegin, selectedEnd);
	}

	public void insertBalise(String balise, String description) {
		StringBuilder alltext = new StringBuilder(textArea.getText());

		int caretPos = textArea.getCaretPosition();

		boolean insertDescription = true;
		if (getSelectedText().length() > 0) {
			description = getSelectedText();
			insertDescription = false;
			caretPos = textArea.getSelectionStart();
		}

		int currentPos = caretPos;
		alltext.insert(currentPos, balise);
		currentPos += balise.length();
		if (insertDescription) {
			alltext.insert(currentPos, description);
		}

		currentPos += description.length();
		alltext.insert(currentPos, balise);
		currentPos += balise.length();

		textArea.setText(alltext.toString());
		textArea.requestFocus();
		if (insertDescription) {
			textArea.setSelectionStart(caretPos + balise.length());
			textArea.setSelectionEnd(currentPos - balise.length());
		} else {
			textArea.setCaretPosition(currentPos);
		}
	}
}
