package net.alteiar.client.bean;

import net.alteiar.client.ClientId;
import net.alteiar.client.bean.AuthorizationManager.AuthorizationManagerException;

public interface Authorizable {
	
	public boolean isAllowedToApplyChange(ClientId cliendId);
	public boolean isAllowedToSee(ClientId cliendId);
	
	public void removeOwner(ClientId cliendId, boolean keepUser) throws AuthorizationManagerException;;
	public void removeUser(ClientId cliendId) throws AuthorizationManagerException;
	
	public void addOwner(ClientId cliendId);
	public void addUser(ClientId cliendId);
	
}
