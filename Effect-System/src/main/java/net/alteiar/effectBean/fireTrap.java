package net.alteiar.effectBean;

import pathfinder.character.PathfinderCharacter;
import pathfinder.gui.mapElement.PathfinderCharacterElement;
import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;

public class fireTrap extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public fireTrap(ColoredShape areaOfEffect, Boolean oneUse,
			Class<? extends BasicBeans> typeBean) throws ClassNotFoundException {
		super(areaOfEffect, oneUse, PathfinderCharacterElement.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void activate() {
		for(BasicBeans bean:this.getActOn())
		{
			PathfinderCharacterElement character=(PathfinderCharacterElement) bean;
			if(this.contain(character.getCenterPosition()))
			{
				PathfinderCharacter realCharacter=CampaignClient.getInstance().getBean(character.getCharactedId());
				realCharacter.setCurrentHp(realCharacter.getCurrentHp()-10);
			}
		}
		
	}
	
	

	@Override
	public void desactivate() {
	}

}
