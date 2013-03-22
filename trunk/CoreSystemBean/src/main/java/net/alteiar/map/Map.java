package net.alteiar.map;

import java.beans.PropertyVetoException;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.utils.map.Scale;

public class Map extends BasicBeans {
	private static final long serialVersionUID = 1L;

	private String name;

	private int width;
	private int height;

	// TODO how it work for list ?
	// private final HashSet<Long> elements;

	private Long background;
	private Long filter;

	private Scale scale;

	public Map() {
		// elements = new HashSet<>();
		scale = new Scale(70, 1.5);
	}

	// ///////////////// BEAN METHODS ///////////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange("name", oldValue,
					name);
			this.name = name;
			propertyChangeSupport.firePropertyChange("name", oldValue, name);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		int oldValue = this.width;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange("width", oldValue,
					width);
			this.width = width;
			propertyChangeSupport.firePropertyChange("width", oldValue, width);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		int oldValue = this.height;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange("height", oldValue,
					height);
			this.height = height;
			propertyChangeSupport
					.firePropertyChange("height", oldValue, height);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Long getBackground() {
		return this.background;
	}

	public void setBackground(Long background) {
		int oldValue = this.height;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange("background",
					oldValue, background);
			this.background = background;
			propertyChangeSupport.firePropertyChange("background", oldValue,
					background);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Long getFilter() {
		return filter;
	}

	public void setFilter(Long filter) {
		int oldValue = this.height;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange("filter", oldValue,
					filter);
			this.filter = filter;
			propertyChangeSupport
					.firePropertyChange("filter", oldValue, filter);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		int oldValue = this.height;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange("scale", oldValue,
					scale);
			this.scale = scale;
			propertyChangeSupport.firePropertyChange("scale", oldValue, scale);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
