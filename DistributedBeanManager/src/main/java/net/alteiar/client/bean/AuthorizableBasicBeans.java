package net.alteiar.client.bean;

import java.io.Serializable;

import net.alteiar.client.ClientId;

public abstract class AuthorizableBasicBeans extends BasicBeans implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected final AuthorizationManager authorizationManager;
	
	public AuthorizableBasicBeans(ClientId ownerId) {
		super();
		authorizationManager = new AuthorizationManager(ownerId);
	}
}
