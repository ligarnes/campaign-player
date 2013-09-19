package pathfinder.gui.document.notepad;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.alteiar.beans.notepad.Notepad;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.documents.BeanDocument;

public class PanelViewNote extends PanelViewDocument {
	private static final long serialVersionUID = 1L;

	private final JLabel lblNote;

	public PanelViewNote() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 142, 0 };
		gridBagLayout.rowHeights = new int[] { 91, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblNote = new JLabel();
		lblNote.setVerticalAlignment(SwingConstants.TOP);
		lblNote.setHorizontalTextPosition(SwingConstants.LEADING);
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.fill = GridBagConstraints.BOTH;
		gbc_lblNote.gridx = 0;
		gbc_lblNote.gridy = 0;
		add(lblNote, gbc_lblNote);

	}

	@Override
	public void setDocument(BeanDocument document) {
		Notepad note = document.getBean();

		lblNote.setText(note.getHtmlFormat());
	}

}
