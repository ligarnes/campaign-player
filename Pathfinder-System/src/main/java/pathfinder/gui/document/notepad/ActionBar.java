package pathfinder.gui.document.notepad;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import pathfinder.gui.document.notepad.actions.BoldAction;
import pathfinder.gui.document.notepad.actions.NewLineAction;
import pathfinder.gui.document.notepad.actions.NoteEditor;

public class ActionBar extends JPanel {
	private static final long serialVersionUID = 1L;

	public ActionBar(NoteEditor editor) {
		super(new FlowLayout());

		this.add(new JButton(new BoldAction(editor)));
		this.add(new JButton(new NewLineAction(editor)));
	}
}
