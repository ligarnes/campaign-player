package pathfinder.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.component.MyCombobox;
import pathfinder.bean.unit.PathfinderCharacter;
import pathfinder.gui.adapter.CharacterAdapter;
import pathfinder.gui.mapElement.PathfinderCharacterElement;

public class PanelCharacterBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final MyCombobox<CharacterAdapter> characters;

	public PanelCharacterBuilder() {
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Cr\u00E9er un personnage"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

		characters = new MyCombobox<CharacterAdapter>();
		panelCenter.add(characters);

		this.add(panelCenter, BorderLayout.CENTER);
	}

	@Override
	public void refresh() {
		characters.setValues(CharacterAdapter.getCharacters());
	}

	private PathfinderCharacter getCharacter() {
		return characters.getSelectedItem().getCharacter();
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
