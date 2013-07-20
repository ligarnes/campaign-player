package net.alteiar.map.filter;

import java.awt.Graphics2D;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.map.MapBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public abstract class MapFilter extends BasicBean {
	private static final long serialVersionUID = 1L;

	@Element
	private UniqueID map;

	public MapFilter(UniqueID map) {
		this.map = map;
	}

	protected MapBean getMap() {
		return CampaignClient.getInstance().getBean(map);
	}

	public MapFilter() {
	}

	public abstract void draw(Graphics2D g, double zoomFactor, boolean isDm);

	public Integer getWidth() {
		return getMap().getWidth();
	}

	public Integer getHeight() {
		return getMap().getHeight();
	}
}
