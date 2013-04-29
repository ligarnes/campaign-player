package net.alteiar.trigger;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.Effect;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class TriggerBean extends MapElement implements
		VetoableChangeListener {
	private static final long serialVersionUID = 1L;

	private UniqueID effectId;
	private ColoredShape areaOfActivation;
	private Class<? extends BasicBeans> typeOfActivator;
	private Boolean isActivate;

	public TriggerBean(ColoredShape areaOfActivation, UniqueID e,
			Class<? extends BasicBeans> typeBean, UniqueID mapID) {
		super(areaOfActivation.getPosition());
		this.areaOfActivation = areaOfActivation;
		this.effectId = e;
		isActivate = false;
		typeOfActivator = typeBean;

		this.setMapId(mapID);

		Map map = CampaignClient.getInstance().getBean(this.getMapId());
		HashSet<UniqueID> elements = map.getElements();
		for (UniqueID element : elements) {
			BasicBeans activator = CampaignClient.getInstance()
					.getBean(element);

			if (Beans.isInstanceOf(activator, typeOfActivator)) {
				activator.addVetoableChangeListener(this);
			}
		}
		map.addVetoableChangeListener(this);
	}

	public ColoredShape getAreaOfActivation() {
		return areaOfActivation;
	}

	public void setAreaOfEffect(ColoredShape areaOfActivation) {
		this.areaOfActivation = areaOfActivation;
		this.setPosition(areaOfActivation.getCenterPosition());
	}

	protected Effect getEffect() {
		return CampaignClient.getInstance().getBean(effectId);
	}

	public UniqueID getEffectId() {
		return effectId;
	}

	public void setEffectID(UniqueID e) {
		this.effectId = e;
	}

	public Class<? extends BasicBeans> getTypeOfActivator() {
		return typeOfActivator;
	}

	public void setTypeOfActivator(Class<? extends BasicBeans> typeOfActivator) {
		this.typeOfActivator = typeOfActivator;
	}

	@Override
	public Boolean contain(Point p) {
		return areaOfActivation.contain(p);
	}

	@Override
	public Double getWidthPixels() {
		return areaOfActivation.getWidthPixels();
	}

	@Override
	public Double getHeightPixels() {
		return areaOfActivation.getHeightPixels();
	}

	@Override
	public void setPosition(Point position) {
		this.setPosition(position);
		this.areaOfActivation.setPosition(position);
	}

	@Override
	public void setMapId(UniqueID mapId) {
		super.setMapId(mapId);
		areaOfActivation.setMapId(mapId);
	}

	public void setIsActivate(Boolean isActivate) {
		this.isActivate = isActivate;
	}

	public Boolean isActivate() {
		return this.isActivate;
	}

	@Override
	protected void drawElement(Graphics2D g, double zoomFactor) {
		areaOfActivation.draw(g, zoomFactor);
	}

	public void vetoableChange(PropertyChangeEvent event)
			throws PropertyVetoException {
		if (event.getPropertyName().contentEquals(Map.METH_ADD_ELEMENT_METHOD)) {
			BasicBeans activator = CampaignClient.getInstance().getBean(
					(UniqueID) event.getNewValue());

			if (Beans.isInstanceOf(activator, typeOfActivator)) {
				activator.addVetoableChangeListener(this);
			}
		}

		if (event.getPropertyName()
				.contentEquals(Map.METH_REMOVE_ELEMENT_METHOD)) {
			BasicBeans oldActivator = CampaignClient.getInstance().getBean(
					(UniqueID) event.getOldValue());
			if (Beans.isInstanceOf(oldActivator, typeOfActivator)) {
				oldActivator.removeVetoableChangeListener(this);
			}
		}
		triggerPropertyChange(event);
	}

	public abstract void triggerPropertyChange(PropertyChangeEvent arg0);
}
