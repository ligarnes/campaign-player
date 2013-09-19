package pathfinder.gui.document.notepad;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.alteiar.beans.notepad.Notepad;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.newversion.shared.bean.BasicBean;
import pathfinder.DocumentTypeConstant;
import pathfinder.gui.document.notepad.actions.NoteEditor;

public class PanelCreateNote extends PanelDocumentBuilder {
	private static final long serialVersionUID = 1L;
	private final JTextField textFieldName;
	private final JTextArea textAreaContent;
	private final JPanel panelActions;

	public PanelCreateNote() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblName = new JLabel("Nom");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);

		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);

		textAreaContent = new JTextArea();
		panelActions = new ActionBar(new NoteEditor(textAreaContent));
		GridBagConstraints gbc_panelEditor = new GridBagConstraints();
		gbc_panelEditor.gridwidth = 3;
		gbc_panelEditor.insets = new Insets(0, 0, 5, 5);
		gbc_panelEditor.fill = GridBagConstraints.BOTH;
		gbc_panelEditor.gridx = 0;
		gbc_panelEditor.gridy = 1;
		add(panelActions, gbc_panelEditor);

		JScrollPane scrollPane = new JScrollPane(textAreaContent);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);
	}

	@Override
	public String getDocumentBuilderName() {
		return "notes";
	}

	@Override
	public String getDocumentBuilderDescription() {
		return "Créer une note";
	}

	@Override
	public void reset() {
		this.textFieldName.setText("");
		this.textAreaContent.setText("");
	}

	@Override
	public String getDocumentName() {
		return this.textFieldName.getText();
	}

	@Override
	public BasicBean buildDocument() {
		return new Notepad(this.textAreaContent.getText());
	}

	@Override
	public String getDocumentType() {
		return DocumentTypeConstant.NOTE;
	}
}
