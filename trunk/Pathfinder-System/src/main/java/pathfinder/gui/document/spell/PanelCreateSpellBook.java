package pathfinder.gui.document.spell;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.tools.ListFilter;
import pathfinder.DocumentTypeConstant;
import pathfinder.bean.spell.DocumentSpellBook;
import pathfinder.bean.spell.Spell;
import pathfinder.bean.spell.SpellManager;

public class PanelCreateSpellBook extends PanelDocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final PanelSpellFilter panelFilter;
	private final PanelSpellListSource panelListSource;
	private final PanelSpellListSource panelListResult;
	private final JTextField textFieldSpellBookName;

	public PanelCreateSpellBook() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 8, 0, 0, 8, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 6, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblTitle = new JLabel("Grimoire");
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 4;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);

		JPanel panelName = new JPanel();
		FlowLayout fl_panelName = (FlowLayout) panelName.getLayout();
		fl_panelName.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelName = new GridBagConstraints();
		gbc_panelName.gridwidth = 2;
		gbc_panelName.insets = new Insets(0, 0, 5, 5);
		gbc_panelName.fill = GridBagConstraints.BOTH;
		gbc_panelName.gridx = 1;
		gbc_panelName.gridy = 1;
		add(panelName, gbc_panelName);

		JLabel lblName = new JLabel("Nom du grimoire:");
		panelName.add(lblName);

		textFieldSpellBookName = new JTextField();
		panelName.add(textFieldSpellBookName);
		textFieldSpellBookName.setColumns(10);

		panelFilter = new PanelSpellFilter();
		GridBagConstraints gbc_panelFilter = new GridBagConstraints();
		gbc_panelFilter.gridwidth = 2;
		gbc_panelFilter.insets = new Insets(0, 0, 5, 5);
		gbc_panelFilter.fill = GridBagConstraints.BOTH;
		gbc_panelFilter.gridx = 1;
		gbc_panelFilter.gridy = 2;
		add(panelFilter, gbc_panelFilter);

		panelListSource = new PanelSpellListSource();
		GridBagConstraints gbc_panelList = new GridBagConstraints();
		gbc_panelList.insets = new Insets(0, 0, 5, 5);
		gbc_panelList.fill = GridBagConstraints.BOTH;
		gbc_panelList.gridx = 1;
		gbc_panelList.gridy = 3;
		add(panelListSource, gbc_panelList);

		panelListResult = new PanelSpellListSource();
		GridBagConstraints gbc_panelListResult = new GridBagConstraints();
		gbc_panelListResult.insets = new Insets(0, 0, 5, 5);
		gbc_panelListResult.fill = GridBagConstraints.BOTH;
		gbc_panelListResult.gridx = 2;
		gbc_panelListResult.gridy = 3;
		add(panelListResult, gbc_panelListResult);

		panelFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFilter();
			}
		});
	}

	public void updateFilter() {
		String className = panelFilter.getSelectedClasse();
		ListFilter<Spell> filter = panelFilter.getFilter();

		panelListSource.setSpells(className, SpellManager.getInstance()
				.getSpells(filter));

		panelListResult.setSpells(className, new ArrayList<Spell>());
	}

	@Override
	public Boolean isDataValid() {
		boolean emptyText = textFieldSpellBookName.getText().isEmpty();
		return !emptyText;
	}

	@Override
	public String getInvalidMessage() {
		String errorMsg = "";
		boolean emptyText = textFieldSpellBookName.getText().isEmpty();
		if (emptyText) {
			errorMsg = "Aucun nom pour le livre de sort";
		}
		return errorMsg;
	}

	@Override
	public String getDocumentBuilderName() {
		return "Grimoire";
	}

	@Override
	public String getDocumentBuilderDescription() {
		return "Cr\u00E9e un grimoire";
	}

	@Override
	public BasicBean buildDocument() {
		DocumentSpellBook book = new DocumentSpellBook(
				textFieldSpellBookName.getText(),
				panelFilter.getSelectedClasse(), panelListResult.getSpells());

		System.out.println("bookname: " + book.getClasseName());
		System.out.println("spells: " + book.getSpells());
		return book;
	}

	@Override
	public String getDocumentName() {
		return textFieldSpellBookName.getText();
	}

	@Override
	public String getDocumentType() {
		return DocumentTypeConstant.SPELL_BOOK;
	}

	@Override
	public void reset() {
		textFieldSpellBookName.setText("");
		panelFilter.reset();
		updateFilter();
	}
}
