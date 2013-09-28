package net.alteiar.beans.map.filter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import net.alteiar.beans.map.MapBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public abstract class MapFilter extends BasicBean {
	private static final long serialVersionUID = 1L;

	@Element
	private UniqueID map;

	protected MapFilter(UniqueID id, UniqueID map) {
		super(id);
		this.map = map;
	}

	public MapFilter(UniqueID map) {
		super();
		this.map = map;
	}

	protected MapBean getMap() {
		return CampaignClient.getInstance().getBean(map);
	}

	public MapFilter() {
	}

	public UniqueID getMapId() {
		return map;
	}

	public abstract void draw(Graphics2D g, double zoomFactor, boolean isDm);

	public Integer getWidth() {
		return getMap().getWidth();
	}

	public Integer getHeight() {
		return getMap().getHeight();
	}

	protected void drawDefault(Graphics2D g, double zoomFactor, boolean isDm) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.scale(zoomFactor, zoomFactor);
		g2.setColor(Color.BLACK);

		if (isDm) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.5f));
		} else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1.0f));
		}

		g2.fillRect(0, 0, getMap().getWidth(), getMap().getHeight());

		g2.dispose();
	}

}
