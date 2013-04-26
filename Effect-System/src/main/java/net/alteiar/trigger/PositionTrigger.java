package net.alteiar.trigger;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.effectBean.Effect;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;

public class PositionTrigger extends TriggerBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PositionTrigger(ColoredShape areaOfActivation, Effect e, Class<? extends BasicBeans> typeBean)
			throws ClassNotFoundException {
		super(areaOfActivation,e,typeBean);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void triggerPropertyChange(PropertyChangeEvent arg0) {
		if(arg0.getPropertyName().contentEquals(MapElement.PROP_POSITION_PROPERTY))
		{
			System.out.println("dans propertyChange");
			
			Point position=(Point) arg0.getNewValue();
			System.out.println("arg0 position="+position);
			System.out.println("areaOfActivation position="+this.getAreaOfActivation().getPosition());
			if(this.contain(position))
			{
				this.getEffect().activate();
			}
		}
	}

}
