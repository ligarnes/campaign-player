package net.alteiar.map;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.image.ImageBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.utils.map.Scale;

public class Map extends BasicBeans {
	private static final long serialVersionUID = 1L;

	private static final String PROP_NAME_PROPERTY = "name";
	private static final String PROP_WIDTH_PROPERTY = "width";
	private static final String PROP_HEIGHT_PROPERTY = "height";
	private static final String PROP_BACKGROUND_PROPERTY = "background";
	private static final String PROP_FILTER_PROPERTY = "filter";
	private static final String PROP_SCALE_PROPERTY = "scale";
	private static final String PROP_ELEMENTS_PROPERTY = "elements";

	private static final String METH_ADD_ELEMENT_METHOD = "addElement";
	private static final String METH_REMOVE_ELEMENT_METHOD = "removeElement";

	private String name;
	private Integer width;
	private Integer height;

	private HashSet<Long> elements;

	private Long background;
	private Long filter;

	private Scale scale;

	public Map() {
	}

	public Map(String name, Long background, Integer width, Integer height) {
		elements = new HashSet<Long>();
		scale = new Scale(70, 1.5);

		this.name = name;
		this.background = background;

		this.width = width;
		this.height = height;
	}

	// ///////////////// LOCAL METHODS ///////////////////////
	public List<MapElement> getElementsAt(Point position) {
		ArrayList<MapElement> elementsAt = new ArrayList<MapElement>();
		HashSet<Long> elements = getElements();

		for (Long id : elements) {
			MapElement element = CampaignClient.getInstance().getBean(id);
			if (element.contain(position)) {
				elementsAt.add(element);
			}
		}

		return elementsAt;
	}

	public BufferedImage getBackgroundImage() {
		BufferedImage background = null;
		ImageBean image = CampaignClient.getInstance().getBean(getBackground());
		try {
			background = image.getImage().restoreImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return background;
	}

	// ///////////////// BEAN METHODS ///////////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_NAME_PROPERTY,
					oldValue, name);
			this.name = name;
			propertyChangeSupport.firePropertyChange(PROP_NAME_PROPERTY,
					oldValue, name);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		Integer oldValue = this.width;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_WIDTH_PROPERTY,
					oldValue, width);
			this.width = width;
			propertyChangeSupport.firePropertyChange(PROP_WIDTH_PROPERTY,
					oldValue, width);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		Integer oldValue = this.height;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_HEIGHT_PROPERTY, oldValue, height);
			this.height = height;
			propertyChangeSupport.firePropertyChange(PROP_HEIGHT_PROPERTY,
					oldValue, height);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Long getBackground() {
		return this.background;
	}

	public void setBackground(Long background) {
		Long oldValue = this.background;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_BACKGROUND_PROPERTY, oldValue, background);
			this.background = background;
			propertyChangeSupport.firePropertyChange(PROP_BACKGROUND_PROPERTY,
					oldValue, background);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Long getFilter() {
		return filter;
	}

	public void setFilter(Long filter) {
		Long oldValue = this.filter;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_FILTER_PROPERTY, oldValue, filter);
			this.filter = filter;
			propertyChangeSupport.firePropertyChange(PROP_FILTER_PROPERTY,
					oldValue, filter);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		Scale oldValue = this.scale;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_SCALE_PROPERTY,
					oldValue, scale);
			this.scale = scale;
			propertyChangeSupport.firePropertyChange(PROP_SCALE_PROPERTY,
					oldValue, scale);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public HashSet<Long> getElements() {
		HashSet<Long> copy = new HashSet<Long>();
		synchronized (elements) {
			copy = (HashSet<Long>) elements.clone();
		}
		return copy;
	}

	public void setElements(HashSet<Long> elements) {
		HashSet<Long> oldValue = this.elements;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_ELEMENTS_PROPERTY, oldValue, elements);
			this.elements = elements;
			propertyChangeSupport.firePropertyChange(PROP_ELEMENTS_PROPERTY,
					oldValue, elements);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public void addElement(Long elementId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_ADD_ELEMENT_METHOD, null, elementId);
			synchronized (elements) {
				this.elements.add(elementId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_ELEMENT_METHOD,
					null, elementId);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public void removeElement(Long elementId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_REMOVE_ELEMENT_METHOD, null, elementId);
			synchronized (elements) {
				this.elements.remove(elementId);
			}
			propertyChangeSupport.firePropertyChange(
					METH_REMOVE_ELEMENT_METHOD, null, elementId);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
