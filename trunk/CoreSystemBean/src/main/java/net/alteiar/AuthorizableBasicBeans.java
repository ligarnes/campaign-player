package net.alteiar;

import java.io.Serializable;
import java.util.HashSet;

import net.alteiar.client.ClientId;
import net.alteiar.client.bean.BasicBeans;

public abstract class AuthorizableBasicBeans extends BasicBeans implements
		Serializable {
	private static final long serialVersionUID = 1L;

	// Attention: Comme les elements sont des set, les methodes
	// fireVetoableChange et firePropertyChange
	// ne permettent pas de seulement ajouter ou enelver un element (le set en
	// entier sera
	// retransmis) et donc pour l 'instant rient n'est fait pour avertir avertir
	// le remote
	// des changement effetctuer sur ces variables. Et donc TODO trouver un
	// moyen de faire
	// la notification de ces changements.

	protected HashSet<ClientId> owners;
	protected HashSet<ClientId> users;

	public AuthorizableBasicBeans(ClientId ownerId) {
		super();
		owners = new HashSet<ClientId>();
		users = new HashSet<ClientId>();
		owners.add(ownerId);
		users.add(ownerId);
	}

	public boolean isAllowedToApplyChange(ClientId cliendId) {
		return owners.contains(cliendId);
	}

	public boolean isAllowedToSee(ClientId cliendId) {
		return (users.contains(cliendId) || owners.contains(cliendId));
	}

	public void removeOwner(ClientId cliendId)
			throws AuthorizationManagerException {
		if (owners.size() <= 1) {
			throw new AuthorizationManagerException(
					String.format(
							"Client id %s is the last owner of this object, owners set must contain at least one client id.",
							cliendId.toString()));
		} else {
			owners.remove(cliendId);
		}
	}

	public void removeUser(ClientId cliendId) {
		users.remove(cliendId);
	}

	public void addOwner(ClientId cliendId) {
		owners.add(cliendId);
	}

	public void addUser(ClientId cliendId) {
		users.add(cliendId);
	}

	public class AuthorizationManagerException extends Exception {
		private static final long serialVersionUID = 1L;

		public AuthorizationManagerException() {
			super();
		}

		public AuthorizationManagerException(String message) {
			super(message);
		}

		public AuthorizationManagerException(String message, Throwable cause) {
			super(message, cause);
		}

		public AuthorizationManagerException(Throwable cause) {
			super(cause);
		}
	}

}
