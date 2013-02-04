package net.alteiar.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class PanelHtmlCharacter extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelHtmlCharacter(String htmlCharacter) {
		final int MAXWIDTH = 900;
		JTextPane editorPane = new JTextPane() {
			private static final long serialVersionUID = 1L;

			private Rectangle rect(javax.swing.text.Position p)
					throws javax.swing.text.BadLocationException {
				int off = p.getOffset();
				Rectangle r = modelToView(off > 0 ? off - 1 : off);
				return r;
			}

			@Override
			public Dimension getPreferredSize() {
				try {
					Rectangle start = rect(getDocument().getStartPosition());
					Rectangle end = rect(getDocument().getEndPosition());
					if (start == null || end == null) {
						return super.getPreferredSize();
					}
					int height = end.y + end.height - start.y + 4;
					return new Dimension(MAXWIDTH, height);
				} catch (javax.swing.text.BadLocationException e) {
					return super.getPreferredSize();
				}
			}
		};

		editorPane.setEditable(false);
		final HTMLEditorKit kit = new HTMLEditorKit();
		editorPane.setEditorKitForContentType("text/html", kit);
		editorPane.setContentType("text/html");

		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(
					htmlCharacter.getBytes("UTF-8"));
			editorPane.read(inputStream, new HTMLDocument());
		} catch (IOException e) {
			e.printStackTrace();
		}

		editorPane.setPreferredSize(new Dimension(MAXWIDTH, 32767));
		this.setLayout(new BorderLayout());
		this.add(editorPane, BorderLayout.CENTER);
		this.doLayout();
		editorPane.setPreferredSize(null);
	}

}
