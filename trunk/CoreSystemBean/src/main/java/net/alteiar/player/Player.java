package net.alteiar.player;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.RectangleElement;
import net.alteiar.shared.MyColor;

public class Player extends BasicBeans {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";
	public static final String PROP_MJ_PROPERTY = "mj";
	public static final String PROP_COLOR_PROPERTY = "color";

	@Element
	private String name;
	@Element
	private Boolean mj;
	@Element
	private MyColor color;

	public Player() {
	}

	public Player(String name, Boolean mj, Color color) {
		super();
		this.name = name;
		this.mj = mj;
		this.color = new MyColor(color);
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
			this.color = new MyColor(color);
			propertyChangeSupport.firePropertyChange(PROP_COLOR_PROPERTY,
					oldValue, color);
		} catch (PropertyVetoException e) {
			// TODO do nothing, the veto is cause by the framework
			// e.printStackTrace();
		}
	}

	@Override
	public void save(File f) throws Exception {
		Serializer serializer = new Persister();
		serializer.write(this, f);
	}

	@Override
	public void loadDocument(File f) throws IOException, Exception {
		Serializer serializer = new Persister();
		Player temp= serializer.read(Player.class, f);
		this.setId(temp.getId());
		this.setColor(temp.getColor());
		this.setName(temp.getName());
		this.setMj(temp.isMj());
		
	}
}
