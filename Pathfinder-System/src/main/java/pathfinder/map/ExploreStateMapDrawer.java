package pathfinder.map;

import net.alteiar.documents.map.Map;

public class ExploreStateMapDrawer extends BasicStateMapDrawer {

	public ExploreStateMapDrawer(Map map) {
		this(map, true);
	}

	public ExploreStateMapDrawer(Map map, Boolean showGrid) {
		super(map, showGrid);
	}

}
