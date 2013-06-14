package net.alteiar.player;

import java.awt.Color;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.MyColor;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Player extends BasicBean {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";
	public static final String PROP_MJ_PROPERTY = "mj";
	public static final String PROP_COLOR_PROPERTY = "color";
	public static final String PROP_CONNECTED_PROPERTY = "connected";

	@Element
	private String name;
	@Element
	private Boolean mj;
	@Element
	private MyColor color;

	private Boolean connected;

	public Player() {
		this.connected = false;
	}

	public Player(String name, Boolean mj, Color color) {
		super();
		this.name = name;
		this.mj = mj;
		this.color = new MyColor(color);
		this.connected = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		if (notifyRemote(PROP_NAME_PROPERTY, oldValue, name)) {
			this.name = name;
			propertyChangeSupport.firePropertyChange(PROP_NAME_PROPERTY,
					oldValue, name);
		}
	}

	public Boolean isDm() {
		return mj;
	}

	public void setMj(Boolean mj) {
		Boolean oldValue = this.mj;
		if (notifyRemote(PROP_MJ_PROPERTY, oldValue, mj)) {
			this.mj = mj;
			propertyChangeSupport.firePropertyChange(PROP_MJ_PROPERTY,
					oldValue, mj);
		}
	}

	public Color getColor() {
		return color.getColor();
	}

	public void setColor(Color color) {
		Color oldValue = this.color.getColor();
		if (notifyRemote(PROP_COLOR_PROPERTY, oldValue, color)) {
			this.color = new MyColor(color);
			propertyChangeSupport.firePropertyChange(PROP_COLOR_PROPERTY,
					oldValue, color);
		}
	}

	public Boolean getConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		Boolean oldValue = this.connected;
		if (notifyRemote(PROP_CONNECTED_PROPERTY, oldValue, connected)) {
			this.connected = connected;
			propertyChangeSupport.firePropertyChange(PROP_CONNECTED_PROPERTY,
					oldValue, connected);
		}
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", mj=" + mj + ", color=" + color + "]";
	}

}
