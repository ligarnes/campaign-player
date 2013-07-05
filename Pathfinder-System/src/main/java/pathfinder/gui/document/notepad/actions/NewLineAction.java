package pathfinder.gui.document.notepad.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.alteiar.notepad.Token;
import net.alteiar.notepad.Tokens;

public class NewLineAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final NoteEditor editor;
	private final Token token;

	public NewLineAction(NoteEditor editor) {
		this.editor = editor;

		token = Tokens.INSTANCE.getToken(Tokens.NEW_LINE);
		this.putValue(Action.NAME, "Nouvelle ligne");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eof = System.getProperty("line.separator");
		this.editor.insertText(token.getToken() + eof);
	}
}
