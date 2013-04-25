package pathfinder.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import pathfinder.character.PathfinderCharacter;
import pathfinder.gui.adapter.CharacterAdapter;
import pathfinder.gui.mapElement.PathfinderCharacterElement;

public class PanelCharacterBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final JComboBox<CharacterAdapter> characters;

	public PanelCharacterBuilder() {
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Cr\u00E9er un personnage"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

		characters = new JComboBox<CharacterAdapter>(
				CharacterAdapter.getCharacters());
		panelCenter.add(characters);

		this.add(panelCenter, BorderLayout.CENTER);
	}

	private PathfinderCharacter getCharacter() {
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
	public PathfinderCharacterElement buildMapElement(Point position) {
		return new PathfinderCharacterElement(position, getCharacter());
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
