package net.alteiar.effectBean;

import java.awt.Color;

import pathfinder.character.PathfinderCharacter;
import pathfinder.gui.mapElement.PathfinderCharacterElement;
import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;

public class VisibilityArea extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VisibilityArea(ColoredShape areaOfEffect, Boolean oneUse,
			Class<? extends MapElement> typeBean) throws ClassNotFoundException {
		super(areaOfEffect, oneUse, typeBean);
		this.getAreaOfEffect().setColor(new Color(255,255,255,0));
	}
	
	@Override
	public void activate() {
		for(BasicBeans bean:this.getActOn())
		{
			MapElement element=(MapElement) bean;
			if(this.contain(element.getCenterPosition()))
			{
				element.setHiddenForPlayer(false);
			}
		}
	}

	@Override
	public void desactivate() {
		for(BasicBeans bean:this.getActOn())
		{
			MapElement element=(MapElement) bean;
			if(this.contain(element.getCenterPosition()))
			{
				element.setHiddenForPlayer(true);
			}
		}
	}

}
