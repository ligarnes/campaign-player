package net.alteiar.campaign.player.gui.centerViews.map.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.alteiar.CampaignClient;
import net.alteiar.SuppressBeanListener;
import net.alteiar.WaitBeanListener;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public class MapElementDrawable extends Drawable implements
		PropertyChangeListener, SuppressBeanListener, WaitBeanListener {

	private final UniqueID mapElementId;
	private MapElement mapElement;

	public MapElementDrawable(UniqueID mapElementId) {
		super();
		this.mapElementId = mapElementId;

		// wait until we receive the bean
		CampaignClient.getInstance().addWaitBeanListener(this);
	}

	@Override
	public void draw(Graphics2D g2, double zoomFactor) {
		if (mapElement != null) {
			mapElement.draw(g2, zoomFactor);
		}
	}

	public boolean isSelected() {
		return mapElement.getSelected();
	}

	@Override
	public boolean contain(Point p) {
		return mapElement != null && mapElement.contain(p);
	}

	public MapElement getMapElement() {
		return mapElement;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		fireRedraw();
	}

	@Override
	public UniqueID getBeanId() {
		return mapElementId;
	}

	@Override
	public void beanReceived(BasicBean bean) {
		mapElement = (MapElement) bean;
		this.mapElement.addPropertyChangeListener(this);

		CampaignClient.getInstance().addSuppressBeanListener(this);
		fireRedraw();
	}

	@Override
	public void beanRemoved(BasicBean bean) {
		if (mapElement != null) {
			mapElement.removePropertyChangeListener(this);
			mapElement = null;
		}
		fireRemove();
	}

	public String getNameFormat() {
		return mapElement.getNameFormat();
	}
}
