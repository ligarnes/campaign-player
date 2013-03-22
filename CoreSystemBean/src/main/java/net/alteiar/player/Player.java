package net.alteiar.player;

import java.awt.Color;
import java.beans.PropertyVetoException;

import net.alteiar.client.bean.BasicBeans;

public class Player extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";
	public static final String PROP_MJ_PROPERTY = "mj";
	public static final String PROP_COLOR_PROPERTY = "color";

	private String name;
	private Boolean mj;
	private Color color;

	public Player() {
		super();
	}

	public Player(String name, Boolean mj, Color color) {
		super();
		this.name = name;
		this.mj = mj;
		this.color = color;
	}

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
			// TODO do nothing, the veto is cause by the framework
			// e.printStackTrace();
		}
	}

	public Boolean isMj() {
		return mj;
	}

	public void setMj(Boolean mj) {
		Boolean oldValue = this.mj;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_MJ_PROPERTY,
					oldValue, mj);
			this.mj = mj;
			propertyChangeSupport.firePropertyChange(PROP_MJ_PROPERTY,
					oldValue, mj);
		} catch (PropertyVetoException e) {
			// TODO do nothing, the veto is cause by the framework
			// e.printStackTrace();
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		Color oldValue = this.color;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_COLOR_PROPERTY,
					oldValue, color);
			this.color = color;
			propertyChangeSupport.firePropertyChange(PROP_COLOR_PROPERTY,
					oldValue, color);
		} catch (PropertyVetoException e) {
			// TODO do nothing, the veto is cause by the framework
			// e.printStackTrace();
		}
	}
}
