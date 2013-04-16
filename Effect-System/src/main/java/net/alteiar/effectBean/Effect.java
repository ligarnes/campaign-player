package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import pathfinder.character.PathfinderCharacter;
import pathfinder.mapElement.character.PathfinderCharacterElement;

import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizeSquare;

public abstract class Effect extends MapElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean oneUse;
	private ColoredShape shape;
	
	
	public Effect(Point position,Color color, Boolean oneUse) {
		super(position);
		shape=new CircleElement(position,color, new MapElementSizeSquare(1));
		this.oneUse=false;
	}
	
	public Effect(Point position, Boolean oneUse) {
		super(position);
		shape=new CircleElement(position,Color.red, new MapElementSizeSquare(1));
		this.oneUse=false;
	}
	
	public ColoredShape getShape()
	{
		return shape;
	}
	
	public void setShape(ColoredShape shape)
	{
		this.shape=shape;
		this.shape.setPosition(this.getPosition());
	}
	
	
	public void draw(Graphics2D g, double zoomFactor){
		shape.draw(g, zoomFactor);
	}

	public Boolean contain(Point p){
		return shape.contain(p);
	}

	public Double getWidthPixels(){
		return shape.getWidthPixels();
	}

	public Double getHeightPixels(){
		return shape.getHeightPixels();
	}
	
	public void setMapId(UniqueID mapId)
	{
		super.setMapId(mapId);
		shape.setMapId(mapId);
	}
	public abstract void activate(PathfinderCharacter c);
}
