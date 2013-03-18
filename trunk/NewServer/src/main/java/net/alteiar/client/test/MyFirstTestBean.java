package net.alteiar.client.test;

public class MyFirstTestBean extends BasicBeans {
	private static final long serialVersionUID = 1L;
	private String name;

	public MyFirstTestBean() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		propertyChangeSupportRemote.firePropertyChange("name", oldValue, name);
	}

	public String getLocalname() {
		return this.name;
	}

	public void setLocalname(String name) {
		this.name = name;
	}
}
