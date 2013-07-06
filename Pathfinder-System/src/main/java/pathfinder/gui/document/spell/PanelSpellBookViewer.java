package pathfinder.gui.document.spell;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.documents.BeanDocument;
import net.alteiar.shared.UniqueID;
import pathfinder.bean.spell.DocumentSpellBook;
import pathfinder.bean.spell.Spell;

public class PanelSpellBookViewer extends PanelViewDocument {

	private static final long serialVersionUID = 1L;

	private PanelSpellListSource panel;

	public PanelSpellBookViewer(BeanDocument bean) {
		super();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblSpellBookName = new JLabel(bean.getDocumentName());
		lblSpellBookName.setFont(new Font("Segoe UI", Font.BOLD, 22));
		GridBagConstraints gbc_lblSpellBookName = new GridBagConstraints();
		gbc_lblSpellBookName.insets = new Insets(0, 0, 5, 0);
		gbc_lblSpellBookName.gridx = 0;
		gbc_lblSpellBookName.gridy = 0;
		add(lblSpellBookName, gbc_lblSpellBookName);

		PanelSpellListSource panel = new PanelSpellListSource();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
	}

	@Override
	public void setDocument(BeanDocument document) {
		DocumentSpellBook spellBook = document.getBean();
		ArrayList<Spell> spells = new ArrayList<Spell>();
		for (UniqueID id : spellBook.getSpells()) {
			Spell spell = CampaignClient.getInstance().getBean(id);

			if (spell != null) {
				spells.add(spell);
			} else {
				System.out.println("enable to find: " + id);
			}
		}

		panel.setSpells(spellBook.getClasseName(), spells);
	}
}
