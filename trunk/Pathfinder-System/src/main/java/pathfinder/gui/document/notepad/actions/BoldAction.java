package pathfinder.gui.document.notepad.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class BoldAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final NoteEditor editor;

	public BoldAction(NoteEditor editor) {
		this.editor = editor;

		this.putValue(Action.NAME, "Gras");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.editor.insertBalise("**", "your bold text here");
	}
}
