package net.alteiar.effectBean;

import java.awt.Color;
import java.awt.Point;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import pathfinder.character.PathfinderCharacter;

public class DelayedEffect extends EffectSuite{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long time;
	
	public DelayedEffect(Point position, Boolean oneUse) {
		super(position, oneUse);
		time=0;
		// TODO Auto-generated constructor stub
	}
	
	public DelayedEffect(Point position, Color color, Boolean oneUse) {
		super(position, color, oneUse);
		time=0;
		// TODO Auto-generated constructor stub
	}
	
	public DelayedEffect(Point position, Color color, Boolean oneUse,long time) {
		super(position, color, oneUse);
		this.time=time;
		// TODO Auto-generated constructor stub
	}

	public DelayedEffect(Point position, Color color, Boolean oneUse,Time time) {
		super(position, color, oneUse);
		this.time=time.getTime();
	}
	
	public void activate(final PathfinderCharacter c)
	{
		final Timer t=new Timer();
		t.schedule(new TimerTask(){
            public void run()
            {
            	for(Effect effect:effects)
        		{
        			effect.activate(c);
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
