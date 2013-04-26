package net.alteiar.effectBean;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.player.Player;
import net.alteiar.shared.MyColor;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizeSquare;

import pathfinder.character.PathfinderCharacter;

public class BasicEffect extends Effect{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicEffect(ColoredShape shape, Boolean oneUse, Class<? extends BasicBeans> typeBean) throws ClassNotFoundException {
		super(shape, oneUse,typeBean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activate() {
		System.out.println("Basic Effect Activated");
		if(this.isOneUse())
		{
			CampaignClient.getInstance().removeBean(this);
		}
	}

	
}
