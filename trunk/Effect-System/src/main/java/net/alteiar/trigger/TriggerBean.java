package net.alteiar.trigger;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.HashSet;


import net.alteiar.CampaignClient;
import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.Effect;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class TriggerBean extends MapElement implements VetoableChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Effect e;
	private ColoredShape areaOfActivation;
	private Class<? extends BasicBeans> typeOfActivator;
	private Boolean isActivate;
	
	public TriggerBean(ColoredShape areaOfActivation, Effect e,Class<? extends BasicBeans> typeBean) throws ClassNotFoundException
	{
		super(areaOfActivation.getPosition());
		this.areaOfActivation=areaOfActivation;
		this.e=e;
		isActivate=false;
		typeOfActivator=typeBean;
		Map map=(Map)CampaignClient.getInstance().getBean(this.getMapId());
		HashSet<UniqueID> elements=map.getElements();
		for(UniqueID element:elements)
		{
			BasicBeans activator=CampaignClient.getInstance().getBean(element);
			if(Beans.isInstanceOf(activator, typeOfActivator))
			{
				activator.addVetoableChangeListener(this);
			}
		}
		map.addVetoableChangeListener(this);
	}
	
	public ColoredShape getAreaOfActivation()
	{
		return areaOfActivation;
	}
	
	public void setAreaOfEffect(ColoredShape areaOfActivation)
	{
		this.areaOfActivation=areaOfActivation;
		this.setPosition(areaOfActivation.getCenterPosition());
	}
	
	public Effect getEffect()
	{
		return e;
	}
	
	public void setEffect(Effect e)
	{
		this.e=e;
	}
	
	public Class<? extends BasicBeans> getTypeOfActivator()
	{
		return typeOfActivator;
	}
	
	public void setTypeOfActivator(Class<? extends BasicBeans> typeOfActivator)
	{
		this.typeOfActivator=typeOfActivator;
	}

	public Boolean contain(Point p){
		return areaOfActivation.contain(p);
	}

	public Double getWidthPixels(){
		return areaOfActivation.getWidthPixels();
	}

	public Double getHeightPixels(){
		return areaOfActivation.getHeightPixels();
	}
	
	public void setPosition(Point position)
	{
		this.setPosition(position);
		this.areaOfActivation.setPosition(position);
	}
	
	public void setMapId(UniqueID mapId)
	{
		super.setMapId(mapId);
		areaOfActivation.setMapId(mapId);
	}
	
	public void setIsActivate(Boolean isActivate)
	{
		this.isActivate=isActivate;
	}
	
	public Boolean isActivate()
	{
		return this.isActivate;
	}
	
	
	@Override
	protected void drawElement(Graphics2D g, double zoomFactor) {
		areaOfActivation.draw(g, zoomFactor);
	}
	
	public void vetoableChange(PropertyChangeEvent arg0)
			throws PropertyVetoException {
		if(arg0.getPropertyName().contentEquals(Map.METH_ADD_ELEMENT_METHOD))
		{
			BasicBeans activator=CampaignClient.getInstance().getBean((UniqueID) arg0.getNewValue());
			if(Beans.isInstanceOf(activator, typeOfActivator))
			{
				activator.addVetoableChangeListener(this);
			}
		}
		
		if(arg0.getPropertyName().contentEquals(Map.METH_REMOVE_ELEMENT_METHOD))
		{
			BasicBeans oldActivator=CampaignClient.getInstance().getBean((UniqueID) arg0.getOldValue());
			if(Beans.isInstanceOf(oldActivator, typeOfActivator))
			{
				oldActivator.removeVetoableChangeListener(this);
			}
		}
		triggerPropertyChange(arg0);
	}
	
	
	public abstract void triggerPropertyChange(PropertyChangeEvent arg0);
}
