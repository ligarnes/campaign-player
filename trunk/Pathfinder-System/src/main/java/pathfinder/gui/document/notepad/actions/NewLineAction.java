package pathfinder.gui.document.notepad.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class NewLineAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final NoteEditor editor;

	public NewLineAction(NoteEditor editor) {
		this.editor = editor;

		this.putValue(Action.NAME, "New line");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eof = System.getProperty("line.separator");
		this.editor.insertText("\\\\" + eof);
	}
}
