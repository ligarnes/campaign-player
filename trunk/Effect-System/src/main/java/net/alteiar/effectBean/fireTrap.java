package net.alteiar.effectBean;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.shared.UniqueID;

public class fireTrap extends Effect {
	private static final long serialVersionUID = 1L;

	public fireTrap(ColoredShape areaOfEffect, Boolean oneUse,
			Class<? extends BasicBeans> typeBean, UniqueID mapId) {
		super(areaOfEffect, oneUse, typeBean, mapId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivate() {
		// TODO Auto-generated method stub

	}

	// should be move to Pathfinder-system
	/*
	 * public fireTrap(ColoredShape areaOfEffect, Boolean oneUse, Class<?
	 * extends BasicBeans> typeBean) throws ClassNotFoundException {
	 * super(areaOfEffect, oneUse, PathfinderCharacterElement.class); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override public void activate() { for (BasicBeans bean :
	 * this.getActOn()) { PathfinderCharacterElement character =
	 * (PathfinderCharacterElement) bean; if
	 * (this.contain(character.getCenterPosition())) { PathfinderCharacter
	 * realCharacter = CampaignClient
	 * .getInstance().getBean(character.getCharactedId());
	 * realCharacter.setCurrentHp(realCharacter.getCurrentHp() - 10); } }
	 * 
	 * }
	 * 
	 * @Override public void desactivate() { }
	 */
}
