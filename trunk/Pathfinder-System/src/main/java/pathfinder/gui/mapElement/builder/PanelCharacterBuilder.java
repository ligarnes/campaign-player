package pathfinder.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pathfinder.mapElement.character.PathfinderCharacter;

import net.alteiar.campaign.player.gui.adapter.CharacterAdapter;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.DocumentMapElementBuilder;

public class PanelCharacterBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final JComboBox<CharacterAdapter> characters;

	public PanelCharacterBuilder() {
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Créer un personnage"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

		characters = new JComboBox<CharacterAdapter>(
				CharacterAdapter.getCharacters());
		panelCenter.add(characters);

		this.add(panelCenter, BorderLayout.CENTER);
	}

	private CharacterClient getCharacter() {
		return ((CharacterAdapter) characters.getSelectedItem()).getCharacter();
	}

	@Override
	public Boolean isAvailable() {
		characters.removeAllItems();
		for (CharacterAdapter adapter : CharacterAdapter.getCharacters()) {
			characters.addItem(adapter);
		}

		return characters.getItemCount() > 0;
	}

	@Override
	public DocumentMapElementBuilder buildMapElement(MapClient<?> map,
			Point position) {
		return new DocumentMapElementBuilder(map, position,
				new PathfinderCharacter(getCharacter()));
	}

	@Override
	public String getElementName() {
		return "Personnage";
	}

	@Override
	public String getElementDescription() {
		return "Dessine un personnage";
	}
}
