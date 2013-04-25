package net.alteiar.trigger;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import pathfinder.mapElement.character.PathfinderCharacterElement;

import net.alteiar.CampaignClient;
import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.effectBean.Effect;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class TriggerBean extends MapElement implements PropertyChangeListener,DocumentManagerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Effect e;
	private ColoredShape areaOfActivation;
	private Class<? extends BasicBeans> typeOfActivator;
	
	public TriggerBean(ColoredShape areaOfActivation, Effect e,Class<? extends BasicBeans> typeBean) throws ClassNotFoundException
	{
		super(areaOfActivation.getPosition());
		this.areaOfActivation=areaOfActivation;
		this.e=e;
		typeOfActivator=typeBean;
		ArrayList<BasicBeans> activators=(ArrayList<BasicBeans>) CampaignClient.getInstance().getBeanFromClass(typeBean);
		for(BasicBeans activator:activators)
		{
			activator.addPropertyChangeListener(this);
		}
		CampaignClient.getInstance().addDocumentManagerListener(this);
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
	
	public void draw(Graphics2D g, double zoomFactor){
		areaOfActivation.draw(g, zoomFactor);
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
	
	public void setMapId(UniqueID mapId)
	{
		super.setMapId(mapId);
		areaOfActivation.setMapId(mapId);
	}
	
	public void documentAdded(DocumentClient document){
		BasicBeans bean = document.getBeanEncapsulator().getBean();
		if(Beans.isInstanceOf(bean, typeOfActivator))
		{
			bean.addPropertyChangeListener(this);
		}
	}
	
	public void documentRemoved(DocumentClient document) {
		BasicBeans bean = document.getBeanEncapsulator().getBean();
		if(Beans.isInstanceOf(bean, typeOfActivator))
		{
			bean.removePropertyChangeListener(this);
		}
	}
	
	public abstract void propertyChange(PropertyChangeEvent arg0);
}
