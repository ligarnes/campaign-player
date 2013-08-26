package pathfinder.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.component.MyCombobox;
import net.alteiar.map.MapBean;
import pathfinder.bean.unit.monster.MonsterBuilder;
import pathfinder.bean.unit.monster.PathfinderMonster;
import pathfinder.gui.adapter.MonsterBuilderAdapter;
import pathfinder.gui.mapElement.PathfinderMonsterElement;

public class PanelMonsterBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final MyCombobox<MonsterBuilderAdapter> monsters;

	public PanelMonsterBuilder() {
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Cr\u00E9er un Monstre"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

		monsters = new MyCombobox<MonsterBuilderAdapter>();
		panelCenter.add(monsters);

		this.add(panelCenter, BorderLayout.CENTER);
	}

	@Override
	public void refresh(MapBean map) {
		monsters.setValues(MonsterBuilderAdapter.getMonsters());
	}

	private MonsterBuilder getCharacter() {
		return monsters.getSelectedItem().getMonster();
	}

	@Override
	public Boolean isAvailable() {
		return monsters.getItemCount() > 0;
	}

	@Override
	public PathfinderMonsterElement buildMapElement(Point position) {
		PathfinderMonster monster = getCharacter().createMonster();
		CampaignClient.getInstance().addBean(monster);
		return new PathfinderMonsterElement(position, monster);
	}

	@Override
	public String getElementName() {
		return "Monstres";
	}

	@Override
	public String getElementDescription() {
		return "Dessine un monstre";
	}
}
