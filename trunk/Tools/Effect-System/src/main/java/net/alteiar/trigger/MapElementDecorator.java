package net.alteiar.trigger;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import net.alteiar.map.elements.MapElement;

public class MapElementDecorator extends MapElement implements
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final MapElement mapElement;

	public MapElementDecorator(MapElement mapElement) {
		this.mapElement = mapElement;
	}

	protected MapElement getMapElement() {
		return mapElement;
	}

	@Override
	protected void drawElement(Graphics2D g, double zoomFactor) {
		getMapElement().draw(g, zoomFactor);
	}

	@Override
	public Boolean contain(Point p) {
		return getMapElement().contain(p);
	}

	@Override
	public Double getWidthPixels() {
		return getMapElement().getWidthPixels();
	}

	@Override
	public Double getHeightPixels() {
		return getMapElement().getHeightPixels();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		try {
			Boolean isInvoke = false;
			BeanInfo info = Introspector
					.getBeanInfo(getMapElement().getClass());
			for (PropertyDescriptor descriptor : info.getPropertyDescriptors()) {
				if (descriptor.getName().equals(evt.getPropertyName())) {
					descriptor.getWriteMethod().invoke(getMapElement(),
							evt.getNewValue());
					isInvoke = true;
				}
			}

			if (!isInvoke) {
				for (MethodDescriptor descriptor : info.getMethodDescriptors()) {
					if (descriptor.getName().equals(evt.getPropertyName())) {
						descriptor.getMethod().invoke(getMapElement(),
								evt.getNewValue());
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
