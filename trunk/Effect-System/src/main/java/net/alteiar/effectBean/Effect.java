package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.HashSet;

import pathfinder.character.PathfinderCharacter;

import net.alteiar.CampaignClient;
import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class Effect extends MapElement implements VetoableChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean oneUse;
	private ColoredShape areaOfEffect;
	private ArrayList<BasicBeans> actOn;
	private Class<? extends BasicBeans> typeActOn;
	
	@SuppressWarnings("unchecked")
	public Effect(ColoredShape areaOfEffect, Boolean oneUse,Class<? extends BasicBeans> typeBean) throws ClassNotFoundException {
		super(areaOfEffect.getPosition());
		this.areaOfEffect=areaOfEffect;
		this.oneUse=oneUse;
		typeActOn=typeBean;
		actOn=new ArrayList<BasicBeans>(); 
		Map map=(Map)CampaignClient.getInstance().getBean(this.getMapId());
		HashSet<UniqueID> elements=map.getElements();
		for(UniqueID element:elements)
		{
			BasicBeans elem=CampaignClient.getInstance().getBean(element);
			if(Beans.isInstanceOf(elem, typeActOn))
			{
				actOn.add(elem);
			}
		}
		map.addVetoableChangeListener(this);
	}
	
	public ColoredShape getAreaOfEffect()
	{
		return areaOfEffect;
	}
	
	public void setPosition(Point position)
	{
		this.setPosition(position);
		this.areaOfEffect.setPosition(position);
	}
	
	public void setAreaOfEffect(ColoredShape areaOfEffect)
	{
		this.areaOfEffect=areaOfEffect;
		this.areaOfEffect.setPosition(this.getPosition());
	}
	
	@Override
	protected void drawElement(Graphics2D g, double zoomFactor) {
		areaOfEffect.draw(g, zoomFactor);
		
	}

	public Boolean contain(Point p){
		return areaOfEffect.contain(p);
	}

	public Double getWidthPixels(){
		return areaOfEffect.getWidthPixels();
	}

	public Double getHeightPixels(){
		return areaOfEffect.getHeightPixels();
	}
	
	public void setMapId(UniqueID mapId)
	{
		super.setMapId(mapId);
		areaOfEffect.setMapId(mapId);
	}
	
	public Boolean isOneUse()
	{
		return oneUse;
	}
	
	public void setOneUse(Boolean oneUse)
	{
		this.oneUse=oneUse;
	}
	
	public void vetoableChange(PropertyChangeEvent arg0)
			throws PropertyVetoException {
		if(arg0.getPropertyName().contentEquals(Map.METH_ADD_ELEMENT_METHOD))
		{
			BasicBeans elem=CampaignClient.getInstance().getBean((UniqueID) arg0.getNewValue());
			if(Beans.isInstanceOf(elem, typeActOn))
			{
				actOn.add(elem);
			}
		}
		
		if(arg0.getPropertyName().contentEquals(Map.METH_REMOVE_ELEMENT_METHOD))
		{
			BasicBeans elem=CampaignClient.getInstance().getBean((UniqueID) arg0.getOldValue());
			if(Beans.isInstanceOf(elem, typeActOn))
			{
				elem.removeVetoableChangeListener(this);
			}
		}
	}
	
	public Class<? extends BasicBeans> getTypeActOn()
	{
		return this.typeActOn;
	}
	
	public void setTypeActOn(Class<? extends BasicBeans> typeActOn)
	{
		this.typeActOn=typeActOn;
	}
	
	public abstract void activate();
}
