package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.Beans;
import java.util.ArrayList;

import pathfinder.character.PathfinderCharacter;

import net.alteiar.CampaignClient;
import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class Effect extends MapElement implements DocumentManagerListener{
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
		actOn=(ArrayList<BasicBeans>) CampaignClient.getInstance().getBeanFromClass(typeActOn);
		CampaignClient.getInstance().addDocumentManagerListener(this);
	}
	
	@SuppressWarnings("unchecked")
	public Effect(ColoredShape areaOfEffect,Class<? extends BasicBeans> typeBean) throws ClassNotFoundException {
		super(areaOfEffect.getCenterPosition());
		this.areaOfEffect=areaOfEffect;
		this.oneUse=false;
		typeActOn=typeBean;
		actOn=(ArrayList<BasicBeans>) CampaignClient.getInstance().getBeanFromClass(typeActOn);
		CampaignClient.getInstance().addDocumentManagerListener(this);
	}

	public ColoredShape getAreaOfEffect()
	{
		return areaOfEffect;
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
	
	public void documentAdded(DocumentClient document){
		BasicBeans bean = document.getBeanEncapsulator().getBean();
		
		if(Beans.isInstanceOf(bean, typeActOn))
		{
			actOn.add(bean);
		}
	}
	
	public void documentRemoved(DocumentClient document) {
		BasicBeans bean = document.getBeanEncapsulator().getBean();
		if(Beans.isInstanceOf(bean, typeActOn))
		{
			actOn.remove(bean);
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
