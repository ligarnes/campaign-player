package net.alteiar.client.bean;

import java.util.HashSet;

import net.alteiar.client.ClientId;

public class AuthorizationManager implements Authorizable {

	protected HashSet<ClientId> owners;
	protected HashSet<ClientId> users;
	
	// Note: un Client Id ne peut pas se retrouver dans les deux set en meme temps.
	// 		si un client est owner, il est aussi considerer comme un user.
	// 		ainsi la liste de user peut etre vide mais pas la liste de owner qui doit 
	//      toujours contenir au moins un ClientId.
	
	public AuthorizationManager(ClientId ownerId) {
		owners = new HashSet<ClientId>();
		users = new HashSet<ClientId>();
		owners.add(ownerId);
	}

	@Override
	public boolean isAllowedToApplyChange(ClientId cliendId) {
		return owners.contains(cliendId);
	}

	@Override
	public boolean isAllowedToSee(ClientId cliendId) {
		return (users.contains(cliendId) || owners.contains(cliendId));
	}

	@Override
	public void removeOwner(ClientId cliendId, boolean keepUser) throws AuthorizationManagerException{
		if (!owners.contains(cliendId)) {
			throw new AuthorizationManagerException(String.format("Client id %s is not in the owners list.", cliendId.toString()));
		}
		else if (owners.size() <= 1) {
			throw new AuthorizationManagerException(String.format("Client id %s is the last owner of this object, owners list must contain a least one client id.", cliendId.toString()));
		}
		else {
			owners.remove(cliendId);
			if (keepUser) {
				users.add(cliendId);
			}
		}
	}

	@Override
	public void removeUser(ClientId cliendId) throws AuthorizationManagerException{
		if (!users.contains(cliendId)) {
			throw new AuthorizationManagerException(String.format("Client id %s is not in the users list.", cliendId.toString()));
		}
		else {
			users.remove(cliendId);
		}
	}

	@Override
	public void addOwner(ClientId cliendId) {
		if (users.contains(cliendId)) {
			users.remove(cliendId);
		}
		owners.add(cliendId);
	}

	@Override
	public void addUser(ClientId cliendId) {
		if (!owners.contains(cliendId)) {
			users.add(cliendId);
		}
	}
	
	public class AuthorizationManagerException extends Exception {
		  public AuthorizationManagerException() { super(); }
		  public AuthorizationManagerException(String message) { super(message); }
		  public AuthorizationManagerException(String message, Throwable cause) { super(message, cause); }
		  public AuthorizationManagerException(Throwable cause) { super(cause); }
	}

}
