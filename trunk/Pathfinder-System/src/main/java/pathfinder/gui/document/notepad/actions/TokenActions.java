package pathfinder.gui.document.notepad.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.alteiar.notepad.Token;

public class TokenActions extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final NoteEditor editor;
	private final Token token;
	private final String description;

	public TokenActions(NoteEditor editor, Token token, String description) {
		this(editor, token, token.getName(), description);
	}

	public TokenActions(NoteEditor editor, Token token, String name,
			String description) {
		this.editor = editor;

		this.token = token;
		this.description = description;

		this.putValue(Action.NAME, name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.editor.insertBalise(token.getToken(), description);
	}
}
