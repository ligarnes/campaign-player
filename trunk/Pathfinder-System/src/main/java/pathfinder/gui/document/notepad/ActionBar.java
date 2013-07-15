package pathfinder.gui.document.notepad;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.textTokenized.TokenManager;
import pathfinder.gui.document.notepad.actions.NewLineAction;
import pathfinder.gui.document.notepad.actions.NoteEditor;
import pathfinder.gui.document.notepad.actions.TokenActions;

public class ActionBar extends JPanel {
	private static final long serialVersionUID = 1L;

	public ActionBar(NoteEditor editor) {
		super(new FlowLayout());

		TokenManager allTokens = TokenManager.INSTANCE;
		this.add(new JButton(new TokenActions(editor, allTokens
				.getToken(TokenManager.BOLD), "Gras", "votre texte en gras")));
		this.add(new JButton(
				new TokenActions(editor, allTokens.getToken(TokenManager.ITALIC),
						"Italique", "votre texte en italique")));
		this.add(new JButton(new TokenActions(editor, allTokens
				.getToken(TokenManager.UNDERLINE), "Soulignez",
				"votre texte souligne")));
		this.add(new JButton(new NewLineAction(editor)));
	}
}
