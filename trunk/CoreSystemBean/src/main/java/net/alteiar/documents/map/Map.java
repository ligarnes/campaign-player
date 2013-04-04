package net.alteiar.documents.map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import net.alteiar.CampaignClient;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.image.ImageBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.map.Scale;

public class Map extends AuthorizationBean {
	@Attribute
	private static final long serialVersionUID = 1L;

	private static final String PROP_NAME_PROPERTY = "name";
	private static final String PROP_WIDTH_PROPERTY = "width";
	private static final String PROP_HEIGHT_PROPERTY = "height";
	private static final String PROP_BACKGROUND_PROPERTY = "background";
	private static final String PROP_FILTER_PROPERTY = "filter";
	public static final String PROP_SCALE_PROPERTY = "scale";
	private static final String PROP_ELEMENTS_PROPERTY = "elements";

	public static final String METH_ADD_ELEMENT_METHOD = "addElement";
	public static final String METH_REMOVE_ELEMENT_METHOD = "removeElement";

	@Element
	private String name;
	@Element
	private Integer width;
	@Element
	private Integer height;
	@ElementList
	private HashSet<UniqueID> elements;
	@Element
	private UniqueID backgroundId;
	@Element
	private UniqueID filterId;
	@Element
	private Scale scale;

	public Map(String name) {
		super();
		this.name = name;
		elements = new HashSet<UniqueID>();
		scale = new Scale(70, 1.5);
	}

	// ///////////////// LOCAL METHODS ///////////////////////
	public List<MapElement> getElementsAt(Point position) {
		ArrayList<MapElement> elementsAt = new ArrayList<MapElement>();
		HashSet<UniqueID> elements = getElements();

		for (UniqueID id : elements) {
			MapElement element = CampaignClient.getInstance().getBean(id);
			if (element.contain(position)) {
				elementsAt.add(element);
			}
		}

		return elementsAt;
	}

	public void drawBackground(Graphics2D g2, double zoomFactor) {
		Graphics2D g = (Graphics2D) g2.create();
		AffineTransform transform = new AffineTransform();
		transform.scale(zoomFactor, zoomFactor);

		BufferedImage backgroundImage = null;
		try {
			backgroundImage = ImageBean.getImage(backgroundId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, transform, null);
		}// else draw other stuff
		g.dispose();
	}

	public void drawElements(Graphics2D g2, double zoomFactor) {
		for (UniqueID mapElementId : getElements()) {
			MapElement mapElement = CampaignClient.getInstance().getBean(
					mapElementId);
			mapElement.draw(g2, zoomFactor);
		}
	}

	public void drawFilter(Graphics2D g2, double zoomFactor) {
		MapFilter filter = CampaignClient.getInstance().getBean(filterId);
		filter.draw(g2, zoomFactor);
	}

	public void drawGrid(Graphics2D g2, double zoomFactor) {
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
			// TODO
			// e.printStackTrace();
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
			// TODO
			// e.printStackTrace();
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
			// TODO
			// e.printStackTrace();
		}
	}

	public UniqueID getBackground() {
		return this.backgroundId;
	}

	public void setBackground(UniqueID background) {
		UniqueID oldValue = this.backgroundId;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_BACKGROUND_PROPERTY, oldValue, background);
			this.backgroundId = background;
			propertyChangeSupport.firePropertyChange(PROP_BACKGROUND_PROPERTY,
					oldValue, background);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public UniqueID getFilter() {
		return filterId;
	}

	public void setFilter(UniqueID filter) {
		UniqueID oldValue = this.filterId;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_FILTER_PROPERTY, oldValue, filter);
			this.filterId = filter;
			propertyChangeSupport.firePropertyChange(PROP_FILTER_PROPERTY,
					oldValue, filter);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
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
			// TODO
			// e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public HashSet<UniqueID> getElements() {
		HashSet<UniqueID> copy = new HashSet<UniqueID>();
		synchronized (elements) {
			copy = (HashSet<UniqueID>) elements.clone();
		}
		return copy;
	}

	public void setElements(HashSet<UniqueID> elements) {
		HashSet<UniqueID> oldValue = this.elements;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_ELEMENTS_PROPERTY, oldValue, elements);
			this.elements = elements;
			propertyChangeSupport.firePropertyChange(PROP_ELEMENTS_PROPERTY,
					oldValue, elements);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public void addElement(UniqueID elementId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_ADD_ELEMENT_METHOD, null, elementId);
			synchronized (elements) {
				this.elements.add(elementId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_ELEMENT_METHOD,
					null, elementId);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public void removeElement(UniqueID elementId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_REMOVE_ELEMENT_METHOD, null, elementId);
			synchronized (elements) {
				this.elements.remove(elementId);
			}
			propertyChangeSupport.firePropertyChange(
					METH_REMOVE_ELEMENT_METHOD, null, elementId);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

}
