package net.alteiar.campaign.player.gui.sideView.chat;

import javax.swing.JEditorPane;
import javax.swing.SizeRequirements;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;

import net.alteiar.shared.ExceptionTool;

public class ChatEditor extends JEditorPane {
	private static final long serialVersionUID = 1L;

	public ChatEditor() {
		super();

		setContentType("text/html");
		setEditorKit(new HTMLEditorKit() {
			private static final long serialVersionUID = 1L;

			@Override
			public ViewFactory getViewFactory() {

				return new HTMLFactory() {
					@Override
					public View create(Element e) {
						View v = super.create(e);
						if (v instanceof InlineView) {
							// FOR br TAG
							if (v.getBreakWeight(InlineView.X_AXIS, 0f, 0f) == InlineView.ForcedBreakWeight) {
								return v;
							}

							return new InlineView(e) {
								@Override
								public int getBreakWeight(int axis, float pos,
										float len) {
									return GoodBreakWeight;
								}

								@Override
								public View breakView(int axis, int p0,
										float pos, float len) {
									if (axis == View.X_AXIS) {
										checkPainter();
										int p1 = getGlyphPainter()
												.getBoundedPosition(this, p0,
														pos, len);
										if (p0 == getStartOffset()
												&& p1 == getEndOffset()) {
											return this;
										}
										return createFragment(p0, p1);
									}
									return this;
								}
							};
						} else if (v instanceof ParagraphView) {
							return new ParagraphView(e) {
								@Override
								protected SizeRequirements calculateMinorAxisRequirements(
										int axis, SizeRequirements r) {
									if (r == null) {
										r = new SizeRequirements();
									}
									float pref = layoutPool
											.getPreferredSpan(axis);
									float min = layoutPool.getMinimumSpan(axis);
									// Don't include insets, Box.getXXXSpan will
									// include them.
									r.minimum = (int) min;
									r.preferred = Math.max(r.minimum,
											(int) pref);
									r.maximum = Integer.MAX_VALUE;
									r.alignment = 0.5f;
									return r;
								}

							};
						}
						return v;
					}
				};
			}
		});
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

	public void appendText(String appendText) {
		String text = super.getText();
		int idx = text.lastIndexOf("</p>");

		setText(text.substring(0, idx) + appendText + text.substring(idx));
		this.revalidate();
		this.repaint();
	}
}
