package pathfinder.gui.document.notepad.actions;

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

	public void insertBalise(String balise, String description) {
		StringBuilder alltext = new StringBuilder(textArea.getText());

		int caretPos = textArea.getCaretPosition();

		alltext.insert(caretPos, balise + description + balise);

		textArea.requestFocus();
		textArea.setText(alltext.toString());
		textArea.setSelectionStart(caretPos + balise.length());
		textArea.setSelectionEnd(caretPos + balise.length()
				+ description.length());
	}
}
