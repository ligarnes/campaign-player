package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Point;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;

import pathfinder.character.PathfinderCharacter;

public class DelayedEffect extends EffectSuite{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long time;
	
	public DelayedEffect(ColoredShape shape, Boolean oneUse, Class<? extends BasicBeans> typeBean,long time) throws ClassNotFoundException  {
		super(shape, oneUse,typeBean);
		this.time=time;
	}
	
	public long getTime()
	{
		return time;
	}
	
	public void setTime(long time)
	{
		this.time=time;
	}
	
	public void activate()
	{
		final Timer t=new Timer();
		t.schedule(new TimerTask(){
            public void run()
            {
            	for(Effect effect:effects)
        		{
        			effect.activate();
        			if(effect.isOneUse())
        			{
        					effects.remove(effect);
        			}
        		}
            	t.cancel();
            }
        }, time);
	}
	
}
