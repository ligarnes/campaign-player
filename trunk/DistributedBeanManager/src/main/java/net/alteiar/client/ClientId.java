package net.alteiar.client;

import java.io.Serializable;

public class ClientId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return Long.toString(id);
	}
	
}
