package pathfinder.effect;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.beans.Beans;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.event.Effect;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.shared.UniqueID;
import pathfinder.gui.mapElement.PathfinderCharacterElement;

public class VisionEffect extends Effect {
	private static final long serialVersionUID = 1L;

	private UniqueID mapId;
	private UniqueID mapElement;

	public VisionEffect() {
	}

	public VisionEffect(UniqueID mapId, UniqueID mapElement) {
		this.mapId = mapId;
		this.mapElement = mapElement;
	}

	protected boolean containElement() {
		Map map = CampaignClient.getInstance().getBean(mapId);

		MapElement area = CampaignClient.getInstance().getBean(mapElement);

		for (UniqueID elementId : map.getElements()) {
			MapElement element = CampaignClient.getInstance()
					.getBean(elementId);
			if (Beans.isInstanceOf(element, PathfinderCharacterElement.class)) {

				if (area.getBoundingBox().intersects(element.getBoundingBox())
						|| area.getBoundingBox().contains(
								element.getBoundingBox())) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void activate(BasicBeans bean) {
		if (containElement()) {
			Map map = CampaignClient.getInstance().getBean(mapId);

			MapFilter filter = CampaignClient.getInstance().getBean(
					map.getFilter());

			MapElement element = CampaignClient.getInstance().getBean(
					mapElement);

			Rectangle2D rect = element.getBoundingBox();

			Polygon p = new Polygon();
			p.addPoint((int) rect.getMinX(), (int) rect.getMinY());
			p.addPoint((int) rect.getMaxX(), (int) rect.getMinY());
			p.addPoint((int) rect.getMaxX(), (int) rect.getMaxY());
			p.addPoint((int) rect.getMinX(), (int) rect.getMaxY());

			filter.showPolygon(p);
		}
	}

	@Override
	public void desactivate(BasicBeans bean) {
		if (!containElement()) {
			Map map = CampaignClient.getInstance().getBean(mapId);

			MapFilter filter = CampaignClient.getInstance().getBean(
					map.getFilter());

			MapElement element = CampaignClient.getInstance().getBean(
					mapElement);

			Rectangle2D rect = element.getBoundingBox();

			Polygon p = new Polygon();
			p.addPoint((int) rect.getMinX(), (int) rect.getMinY());
			p.addPoint((int) rect.getMaxX(), (int) rect.getMinY());
			p.addPoint((int) rect.getMaxX(), (int) rect.getMaxY());
			p.addPoint((int) rect.getMinX(), (int) rect.getMaxY());

			filter.hidePolygon(p);
		}
	}
}
