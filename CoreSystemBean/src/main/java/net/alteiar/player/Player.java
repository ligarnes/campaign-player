package net.alteiar.player;

import java.awt.Color;

import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.ColorWrapper;

import org.simpleframework.xml.Element;

public class Player extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";
	public static final String PROP_MJ_PROPERTY = "dm";
	public static final String PROP_COLOR_PROPERTY = "color";
	public static final String PROP_CONNECTED_PROPERTY = "connected";

	@Element
	private String name;
	@Element
	private Boolean dm;
	@Element
	private ColorWrapper color;

	private Boolean connected;

	public Player() {
		this.connected = false;
	}

	public Player(String name, Boolean mj, Color color) {
		super();
		this.name = name;
		this.dm = mj;
		this.color = new ColorWrapper(color);
		this.connected = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		if (notifyRemote(PROP_NAME_PROPERTY, oldValue, name)) {
			this.name = name;
			notifyLocal(PROP_NAME_PROPERTY, oldValue, name);
		}
	}

	public Boolean isDm() {
		return dm;
	}

	public void setDm(Boolean mj) {
		Boolean oldValue = this.dm;
		if (notifyRemote(PROP_MJ_PROPERTY, oldValue, mj)) {
			this.dm = mj;
			notifyLocal(PROP_MJ_PROPERTY, oldValue, mj);
		}
	}

	public Color getRealColor() {
		return getColor().getColor();
	}

	public void changeColor(Color color) {
		setColor(new ColorWrapper(color));
	}

	public ColorWrapper getColor() {
		return color;
	}

	public void setColor(ColorWrapper color) {
		ColorWrapper oldValue = this.color;
		if (notifyRemote(PROP_COLOR_PROPERTY, oldValue, color)) {
			this.color = color;
			notifyLocal(PROP_COLOR_PROPERTY, oldValue, color);
		}
	}

	public Boolean getConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		Boolean oldValue = this.connected;
		if (notifyRemote(PROP_CONNECTED_PROPERTY, oldValue, connected)) {
			this.connected = connected;
			notifyLocal(PROP_CONNECTED_PROPERTY, oldValue, connected);
		}
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", mj=" + dm + ", color=" + color + "]";
	}

}
