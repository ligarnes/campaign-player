package net.alteiar.effectBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import pathfinder.character.PathfinderCharacter;
import pathfinder.mapElement.character.PathfinderCharacterElement;

import net.alteiar.CampaignClient;
import net.alteiar.shared.UniqueID;


public class EffectManager implements PropertyChangeListener{
	
	private static EffectManager INSTANCE=null;
	private HashSet<UniqueID> effect;
	
	synchronized static public EffectManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EffectManager();
		}
		return INSTANCE;
	}

	private EffectManager()
	{
		effect=new HashSet<UniqueID>();
	}
	
	public void addEffect(Effect e)
	{
		addEffect(e.getId());
	}
	
	public void addEffect(UniqueID id)
	{
		effect.add(id);
	}
	
	public void removeEffect(Effect e)
	{
		removeEffect(e.getId());
	}
	
	public void removeEffect(UniqueID id)
	{
		effect.remove(id);
	}
	
	public void propertyChange(PropertyChangeEvent arg0) {
		PathfinderCharacterElement c=(PathfinderCharacterElement)arg0.getNewValue();
		for(UniqueID id:effect)
		{
			Effect e=CampaignClient.getInstance().getBean(id);
			if(e.contain(c.getCenterPosition()))
			{
				e.activate(c.getCharacter());
			}
		}
	}

}
