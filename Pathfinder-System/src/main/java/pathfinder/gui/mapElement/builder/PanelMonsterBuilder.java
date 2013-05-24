package pathfinder.gui.mapElement.builder;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.component.MyCombobox;
import pathfinder.bean.unit.monster.PathfinderMonster;
import pathfinder.gui.adapter.MonsterAdapter;
import pathfinder.gui.mapElement.PathfinderMonsterElement;

public class PanelMonsterBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final MyCombobox<MonsterAdapter> monsters;

	public PanelMonsterBuilder() {
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Cr\u00E9er un Monstre"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

		monsters = new MyCombobox<MonsterAdapter>(MonsterAdapter.getMonsters());
		panelCenter.add(monsters);

		this.add(panelCenter, BorderLayout.CENTER);
	}

	private PathfinderMonster getCharacter() {
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
