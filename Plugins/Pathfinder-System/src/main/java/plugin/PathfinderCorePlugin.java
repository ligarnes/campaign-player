package plugin;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.campaign.player.plugin.ICorePlugin;
import pathfinder.gui.general.PanelCharacterInfo;
import pathfinder.map.state.PathfinderDrawInfo;

public class PathfinderCorePlugin implements ICorePlugin {

	@Override
	public JPanel buildSmallCharacterSheet() {
		return new PanelCharacterInfo();
	}

	@Override
	public DrawFilter getDrawInfo(MapEditableInfo mapInfo) {
		return new PathfinderDrawInfo(mapInfo);
	}

}
