/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.campaign.player.gui.chat;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import net.alteiar.ExceptionTool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BasicHTMLTextEditor extends JEditorPane {
	private static final long serialVersionUID = 1L;

	private final HTMLEditorKit editorKit;

	public BasicHTMLTextEditor() {
		super();
		editorKit = new HTMLEditorKit();
		HTMLDocument document = (HTMLDocument) editorKit
				.createDefaultDocument();
		this.setDocument(document);
		this.setContentType(editorKit.getContentType());
		DefaultCaret caret = (DefaultCaret) this.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

	}

	@Override
	public String getText() {
		String text = super.getText();
		HTMLDocument document = (HTMLDocument) this.getDocument();
		// #0 is the HTML element, #1 the bidi-root
		Element[] roots = document.getRootElements();
		Element body = findElement(roots[0], HTML.Tag.BODY);
		Element p = findElement(body, HTML.Tag.P);

		Document realText = p.getDocument();
		try {
			text = realText.getText(0, realText.getLength());
		} catch (BadLocationException ex) {
			ExceptionTool.showError(ex);
		}
		return text;
	}

	public String getAllHtmlText() {
		return super.getText();
	}

	@Override
	public void setText(String text) {
		try {
			HTMLDocument document = (HTMLDocument) editorKit
					.createDefaultDocument();
			document.insertString(0, text, null);
			super.setDocument(document);
		} catch (BadLocationException ex) {
			ExceptionTool.showError(ex);
		}
	}

	private Element findElement(Element root, Tag tag) {
		Element body = null;
		for (int i = 0; i < root.getElementCount(); i++) {
			Element element = root.getElement(i);
			if (element.getAttributes().getAttribute(
					StyleConstants.NameAttribute) == tag) {
				body = element;
				break;
			}
		}

		return body;
	}

	public void appendText(String text) {
		try {
			HTMLDocument document = (HTMLDocument) this.getDocument();
			// #0 is the HTML element, #1 the bidi-root
			Element[] roots = document.getRootElements();
			Element body = findElement(roots[0], HTML.Tag.BODY);
			Element p = findElement(body, HTML.Tag.P);

			document.insertBeforeEnd(p, text);
			// textPane.setDocument(document);
			super.setDocument(document);
		} catch (BadLocationException ex) {
			ExceptionTool.showError(ex);
		} catch (IOException ex) {
			ExceptionTool.showError(ex);
		}
	}
}
