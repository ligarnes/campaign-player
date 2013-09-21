import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.alteiar.beans.notepad.Notepad;

public class TextEditorTest {

	private static JTextArea textArea;
	private static JEditorPane lbl;

	private static JFrame frm;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		frm = new JFrame();

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

		textArea = new JTextArea();
		lbl = new JEditorPane("text/html", "");
		lbl.setMinimumSize(new Dimension(300, 150));

		panelCenter.add(textArea);

		panelCenter.add(lbl);

		JPanel panelSouth = new JPanel();
		JButton btn = new JButton("Previsualiser");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previsualize();
			}
		});
		panelSouth.add(btn);

		frm.add(panelCenter, BorderLayout.CENTER);
		frm.add(panelSouth, BorderLayout.SOUTH);

		frm.pack();
		frm.setVisible(true);
	}

	public static void previsualize() {
		String txt = textArea.getText();

		Notepad notepad = new Notepad(txt);
		lbl.setText(notepad.getHtmlFormat());

		lbl.revalidate();
	}
}
