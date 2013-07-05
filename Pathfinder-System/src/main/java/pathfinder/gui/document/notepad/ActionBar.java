package pathfinder.gui.document.notepad;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.notepad.Tokens;
import pathfinder.gui.document.notepad.actions.NewLineAction;
import pathfinder.gui.document.notepad.actions.NoteEditor;
import pathfinder.gui.document.notepad.actions.TokenActions;

public class ActionBar extends JPanel {
	private static final long serialVersionUID = 1L;

	public ActionBar(NoteEditor editor) {
		super(new FlowLayout());

		Tokens allTokens = Tokens.INSTANCE;
		this.add(new JButton(new TokenActions(editor, allTokens
				.getToken(Tokens.BOLD), "Gras", "votre texte en gras")));
		this.add(new JButton(
				new TokenActions(editor, allTokens.getToken(Tokens.ITALIC),
						"Italique", "votre texte en italique")));
		this.add(new JButton(new TokenActions(editor, allTokens
				.getToken(Tokens.UNDERLINE), "Soulignez",
				"votre texte souligne")));
		this.add(new JButton(new NewLineAction(editor)));
	}
}
