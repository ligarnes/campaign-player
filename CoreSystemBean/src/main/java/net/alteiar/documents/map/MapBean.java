package net.alteiar.documents.map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.image.ImageBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.map.Scale;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class MapBean extends AuthorizationBean {
	private static final long serialVersionUID = 1L;

	private static final String PROP_WIDTH_PROPERTY = "width";
	private static final String PROP_HEIGHT_PROPERTY = "height";
	private static final String PROP_BACKGROUND_PROPERTY = "background";
	private static final String PROP_FILTER_PROPERTY = "filter";
	public static final String PROP_SCALE_PROPERTY = "scale";
	private static final String PROP_ELEMENTS_PROPERTY = "elements";

	public static final String METH_ADD_ELEMENT_METHOD = "addElement";
	public static final String METH_REMOVE_ELEMENT_METHOD = "removeElement";

	@Element
	private Integer width;
	@Element
	private Integer height;

	@ElementList
	private ArrayList<UniqueID> elements;

	@Element
	private UniqueID backgroundId;
	@Element
	private UniqueID filterId;
	@Element
	private Scale scale;

	protected MapBean() {
	}

	public MapBean(String name) {
		super(name);
		elements = new ArrayList<UniqueID>();
		scale = new Scale(70, 1.5);
	}

	@Override
	public void beanRemoved() {
		super.beanRemoved();

		CampaignClient.getInstance().removeBean(backgroundId);
		CampaignClient.getInstance().removeBean(filterId);

		Iterator<UniqueID> itt = elements.iterator();
		while (itt.hasNext()) {
			CampaignClient.getInstance().removeBean(itt.next());
		}
	}

	// ///////////////// LOCAL METHODS ///////////////////////
	public List<MapElement> getElementsAt(Point position) {
		ArrayList<MapElement> elementsAt = new ArrayList<MapElement>();
		ArrayList<UniqueID> elements = getElements();

		for (UniqueID id : elements) {
			MapElement element = CampaignClient.getInstance().getBean(id);
			if (element != null && element.contain(position)) {
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

	public void drawGrid(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		Double squareSize = this.getScale().getPixels() * zoomFactor;

		Double width = this.getWidth() * zoomFactor;
		Double height = this.getHeight() * zoomFactor;

		for (double i = 0; i < width; i += squareSize) {
			g2.drawLine((int) i, 0, (int) i, height.intValue());
		}
		for (double i = 0; i < height; i += squareSize) {
			g2.drawLine(0, (int) i, width.intValue(), (int) i);
		}
		g2.dispose();
	}

	// ///////////////// BEAN METHODS ///////////////////////
	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		Integer oldValue = this.width;
		if (notifyRemote(PROP_WIDTH_PROPERTY, oldValue, width)) {
			this.width = width;
			propertyChangeSupport.firePropertyChange(PROP_WIDTH_PROPERTY,
					oldValue, width);
		}
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		Integer oldValue = this.height;
		if (notifyRemote(PROP_HEIGHT_PROPERTY, oldValue, height)) {
			this.height = height;
			propertyChangeSupport.firePropertyChange(PROP_HEIGHT_PROPERTY,
					oldValue, height);
		}
	}

	public UniqueID getBackground() {
		return this.backgroundId;
	}

	public void setBackground(UniqueID background) {
		UniqueID oldValue = this.backgroundId;
		if (notifyRemote(PROP_BACKGROUND_PROPERTY, oldValue, background)) {
			this.backgroundId = background;
			propertyChangeSupport.firePropertyChange(PROP_BACKGROUND_PROPERTY,
					oldValue, background);
		}
	}

	public UniqueID getFilter() {
		return filterId;
	}

	public void setFilter(UniqueID filter) {
		UniqueID oldValue = this.filterId;
		if (notifyRemote(PROP_FILTER_PROPERTY, oldValue, filter)) {
			this.filterId = filter;
			propertyChangeSupport.firePropertyChange(PROP_FILTER_PROPERTY,
					oldValue, filter);
		}
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		Scale oldValue = this.scale;
		if (notifyRemote(PROP_SCALE_PROPERTY, oldValue, scale)) {
			this.scale = scale;
			propertyChangeSupport.firePropertyChange(PROP_SCALE_PROPERTY,
					oldValue, scale);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<UniqueID> getElements() {
		ArrayList<UniqueID> copy = new ArrayList<UniqueID>();
		synchronized (elements) {
			copy = (ArrayList<UniqueID>) elements.clone();
		}
		return copy;
	}

	public void setElements(ArrayList<UniqueID> elements) {
		ArrayList<UniqueID> oldValue = this.elements;
		if (notifyRemote(PROP_ELEMENTS_PROPERTY, oldValue, elements)) {
			this.elements = elements;
			propertyChangeSupport.firePropertyChange(PROP_ELEMENTS_PROPERTY,
					oldValue, elements);
		}
	}

	public void addElement(UniqueID elementId) {
		if (notifyRemote(METH_ADD_ELEMENT_METHOD, null, elementId)) {
			synchronized (elements) {
				this.elements.add(elementId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_ELEMENT_METHOD,
					null, elementId);
		}
	}

	public void removeElement(UniqueID elementId) {
		if (notifyRemote(METH_REMOVE_ELEMENT_METHOD, null, elementId)) {
			synchronized (elements) {
				this.elements.remove(elementId);
			}
			propertyChangeSupport.firePropertyChange(
					METH_REMOVE_ELEMENT_METHOD, null, elementId);
		}
	}
}
