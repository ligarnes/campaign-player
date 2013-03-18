package net.alteiar.client.test;

import java.util.HashSet;

public class MapBean extends BasicBeans {
	private static final long serialVersionUID = 1L;

	private String mapName;

	private int width;
	private int height;

	private HashSet<Long> elements;

	private Long background;
	private Long filterRemote;

	public MapBean() {
	}

	public String getName() {
		return mapName;
	}

	public void setName(String name) {
		this.propertyChangeSupportRemote.firePropertyChange("name", getName(),
				name);
	}

	public String getLocalname() {
		return getName();
	}

	public void setLocalname(String name) {
		String oldname = this.mapName;
		this.mapName = name;
		propertyChangeSupportClient.firePropertyChange("name", oldname,
				this.mapName);
	}
}
