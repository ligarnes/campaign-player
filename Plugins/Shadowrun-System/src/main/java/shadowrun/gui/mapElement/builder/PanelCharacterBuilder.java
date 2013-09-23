package shadowrun.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Point;
import java.beans.Beans;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.component.MyCombobox;
import net.alteiar.documents.BeanDocument;
import net.alteiar.shared.UniqueID;
import shadowrun.gui.adapter.CharacterAdapter;
import shadowrun.gui.mapElement.ShadowrunCharacterElement;

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
	public void refresh(MapBean map) {

		ArrayList<UniqueID> ignoreList = new ArrayList<UniqueID>();

		for (UniqueID mapId : map.getElements()) {
			MapElement element = CampaignClient.getInstance().getBean(mapId);

			if (Beans.isInstanceOf(element, ShadowrunCharacterElement.class)) {
				ShadowrunCharacterElement characterElement = (ShadowrunCharacterElement) Beans
						.getInstanceOf(element,
								ShadowrunCharacterElement.class);

				ignoreList.add(characterElement.getCharactedId());
			}
		}
		characters.setValues(CharacterAdapter.getCharacters(ignoreList));
	}

	private BeanDocument getCharacter() {
		return characters.getSelectedItem().getCharacter();
	}

	@Override
	public Boolean isAvailable() {
		return characters.getItemCount() > 0;
	}

	@Override
	public ShadowrunCharacterElement buildMapElement(Point position) {
		return new ShadowrunCharacterElement(position, getCharacter());
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
