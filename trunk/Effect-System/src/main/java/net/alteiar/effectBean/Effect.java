package net.alteiar.effectBean;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class Effect extends MapElement implements
		VetoableChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean oneUse;
	private MapElement areaOfEffect;

	private final ArrayList<UniqueID> actOn;
	private Class<? extends BasicBeans> typeActOn;

	public Effect(MapElement areaOfEffect, Boolean oneUse,
			Class<? extends BasicBeans> typeBean, UniqueID mapId) {
		super(areaOfEffect.getPosition());

		this.areaOfEffect = areaOfEffect;
		areaOfEffect.setHiddenForPlayer(true);

		this.oneUse = oneUse;
		typeActOn = typeBean;
		actOn = new ArrayList<UniqueID>();

		this.setMapId(mapId);

		Map map = CampaignClient.getInstance().getBean(this.getMapId());
		HashSet<UniqueID> elements = map.getElements();
		for (UniqueID element : elements) {
			BasicBeans elem = CampaignClient.getInstance().getBean(element);
			if (Beans.isInstanceOf(elem, typeActOn)) {
				actOn.add(elem.getId());
			}
		}
		map.addVetoableChangeListener(this);
	}

	public MapElement getAreaOfEffect() {
		return areaOfEffect;
	}

	@Override
	public void setPosition(Point position) {
		super.setPosition(position);
		this.areaOfEffect.setPosition(position);
	}

	public void setAreaOfEffect(MapElement areaOfEffect) {
		this.areaOfEffect = areaOfEffect;
		this.areaOfEffect.setPosition(this.getPosition());
	}

	@Override
	protected void drawElement(Graphics2D g, double zoomFactor) {
		areaOfEffect.draw(g, zoomFactor);

	}

	@Override
	public Boolean contain(Point p) {
		return areaOfEffect.contain(p);
	}

	@Override
	public Double getWidthPixels() {
		return areaOfEffect.getWidthPixels();
	}

	@Override
	public Double getHeightPixels() {
		return areaOfEffect.getHeightPixels();
	}

	@Override
	public void setMapId(UniqueID mapId) {
		super.setMapId(mapId);
		areaOfEffect.setMapId(mapId);
	}

	public Boolean isOneUse() {
		return oneUse;
	}

	public void setOneUse(Boolean oneUse) {
		this.oneUse = oneUse;
	}

	public void vetoableChange(PropertyChangeEvent arg0)
			throws PropertyVetoException {
		if (arg0.getPropertyName().contentEquals(Map.METH_ADD_ELEMENT_METHOD)) {
			BasicBeans elem = CampaignClient.getInstance().getBean(
					(UniqueID) arg0.getNewValue(), 300);
			if (Beans.isInstanceOf(elem, typeActOn)) {
				actOn.add(elem.getId());
			}
		}

		if (arg0.getPropertyName()
				.contentEquals(Map.METH_REMOVE_ELEMENT_METHOD)) {
			BasicBeans elem = CampaignClient.getInstance().getBean(
					(UniqueID) arg0.getOldValue());
			if (Beans.isInstanceOf(elem, typeActOn)) {
				elem.removeVetoableChangeListener(this);
			}
		}
	}

	public Class<? extends BasicBeans> getTypeActOn() {
		return this.typeActOn;
	}

	public void setTypeActOn(Class<? extends BasicBeans> typeActOn) {
		this.typeActOn = typeActOn;
	}

	public ArrayList<UniqueID> getActOn() {
		return this.actOn;
	}

	public void deleteEffect() {
		Map map = (Map) CampaignClient.getInstance().getBean(this.getMapId());
		map.removeVetoableChangeListener(this);
		map.removeElement(this.getId());
	}

	public void activation() {
		activate();
		areaOfEffect.setHiddenForPlayer(false);
		if (oneUse) {
			deleteEffect();
		}
	}

	public void desactivation() {
		desactivate();
		areaOfEffect.setHiddenForPlayer(true);
		if (oneUse) {
			deleteEffect();
		}
	}

	public abstract void activate();

	public abstract void desactivate();
}
