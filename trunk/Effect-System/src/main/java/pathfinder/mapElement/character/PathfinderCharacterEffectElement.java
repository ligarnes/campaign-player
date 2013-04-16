package pathfinder.mapElement.character;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import net.alteiar.effectBean.EffectManager;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.map.element.MapElementSizeSquare;

import pathfinder.character.PathfinderCharacter;

public class PathfinderCharacterEffectElement extends PathfinderCharacterElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public PathfinderCharacterEffectElement(Point point, PathfinderCharacter character) {
		super(point, character);
		this.addPropertyChangeListener("position",EffectManager.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public  PathfinderCharacterEffectElement(Point point, UniqueID characterId) {
		super(point, characterId);
		this.addPropertyChangeListener("position",EffectManager.getInstance());
	}
	
	public final void setPosition(Point position)
	{
		super.setPosition(position);
		propertyChangeSupport.firePropertyChange("position", null, this);
	}
	
	 private void addPropertyChangeListener(String propertyName, PropertyChangeListener listener){
	        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	    }

	    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener){
	        propertyChangeSupport.removePropertyChangeListener(propertyName,listener);
	    }
	
	
	
	
	
}
