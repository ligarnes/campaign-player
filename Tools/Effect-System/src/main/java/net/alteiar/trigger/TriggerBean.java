package net.alteiar.trigger;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.documents.map.MapBean;
import net.alteiar.documents.map.MapAddRemoveElementAdapter;
import net.alteiar.effectBean.mine.MyEffect;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class TriggerBean extends MapElement implements
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private UniqueID effectId;
	private ColoredShape areaOfActivation;
	private Class<? extends BasicBean> typeOfActivator;
	private Boolean isActivate;

	public TriggerBean(ColoredShape areaOfActivation, UniqueID effect,
			Class<? extends BasicBean> typeBean, UniqueID mapID) {
		super(areaOfActivation.getPosition());
		this.areaOfActivation = areaOfActivation;
		this.effectId = effect;
		isActivate = false;
		typeOfActivator = typeBean;

		this.setMapId(mapID);

		MapBean map = CampaignClient.getInstance().getBean(this.getMapId());
		HashSet<UniqueID> elements = map.getElements();
		for (UniqueID element : elements) {
			MapElement activator = CampaignClient.getInstance()
					.getBean(element);

			if (Beans.isInstanceOf(activator, typeOfActivator)) {
				activator.addPropertyChangeListener(this);
			}
		}
		map.addPropertyChangeListener(new MapObserver());
	}

	public ColoredShape getAreaOfActivation() {
		return areaOfActivation;
	}

	public void setAreaOfEffect(ColoredShape areaOfActivation) {
		this.areaOfActivation = areaOfActivation;
		this.setPosition(areaOfActivation.getCenterPosition());
	}

	protected MyEffect getEffect() {
		return CampaignClient.getInstance().getBean(effectId);
	}

	public UniqueID getEffectId() {
		return effectId;
	}

	public void setEffectID(UniqueID e) {
		this.effectId = e;
	}

	public Class<? extends BasicBean> getTypeOfActivator() {
		return typeOfActivator;
	}

	public void setTypeOfActivator(Class<? extends BasicBean> typeOfActivator) {
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

	public void propertyChange(PropertyChangeEvent event) {
		triggerPropertyChange((MapElement) event.getSource());
	}

	public abstract void triggerPropertyChange(MapElement element);

	private class MapObserver extends MapAddRemoveElementAdapter {

		@Override
		protected void mapElementAdded(MapElement element) {
			element.addPropertyChangeListener(TriggerBean.this);
			triggerPropertyChange(element);
		}

		@Override
		protected void mapElementRemoved(MapElement element) {
			element.removePropertyChangeListener(TriggerBean.this);
		}

	}
}
