package net.alteiar.sharedDocuments;

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.HashSet;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.player.Player;

public abstract class AuthorizationBasicBeans extends BasicBeans implements
		Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PROP_OWNERS_PROPERTY = "owners";
	public static final String PROP_USERS_PROPERTY = "users";

	public static final String METH_ADD_OWNER_METHOD = "addOwner";
	public static final String METH_REMOVE_OWNER_METHOD = "removeOwner";
	public static final String METH_ADD_USER_METHOD = "addUser";
	public static final String METH_REMOVE_USER_METHOD = "removeUser";

	private HashSet<Long> owners;
	private HashSet<Long> users;

	public AuthorizationBasicBeans() {
		super();
		owners = new HashSet<Long>();
		users = new HashSet<Long>();
	}

	public HashSet<Long> getOwners() {
		return owners;
	}

	public void setOwners(HashSet<Long> owners) {
		HashSet<Long> oldValue = this.owners;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_OWNERS_PROPERTY, oldValue, owners);
			this.owners = owners;
			propertyChangeSupport.firePropertyChange(PROP_OWNERS_PROPERTY,
					oldValue, owners);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}

	public HashSet<Long> getUsers() {
		return users;
	}

	public void setUsers(HashSet<Long> users) {
		HashSet<Long> oldValue = this.users;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_USERS_PROPERTY,
					oldValue, users);
			this.users = users;
			propertyChangeSupport.firePropertyChange(PROP_USERS_PROPERTY,
					oldValue, users);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}

	public boolean isAllowedToApplyChange(Player player) {
		return isAllowedToApplyChange(player.getId());
	}

	public boolean isAllowedToApplyChange(Long cliendId) {
		return owners.contains(cliendId);
	}

	public boolean isAllowedToSee(Player player) {
		return isAllowedToSee(player.getId());
	}

	public boolean isAllowedToSee(Long cliendId) {
		return (users.contains(cliendId) || owners.contains(cliendId));
	}

	public void addOwner(Long playerId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_ADD_OWNER_METHOD, null, playerId);
			synchronized (owners) {
				owners.add(playerId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_OWNER_METHOD,
					null, playerId);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}

	public void removeOwner(Long playerId) throws AuthorizationManagerException {
		if (owners.size() <= 1) {
			throw new AuthorizationManagerException(
					String.format(
							"Client id %s is the last owner of this object, owners set must contain at least one client id.",
							playerId.toString()));
		} else {
			try {
				vetoableRemoteChangeSupport.fireVetoableChange(
						METH_REMOVE_OWNER_METHOD, null, playerId);
				synchronized (owners) {
					owners.remove(playerId);
				}
				propertyChangeSupport.firePropertyChange(
						METH_REMOVE_OWNER_METHOD, null, playerId);
			} catch (PropertyVetoException e) {
				// TODO Remote refuse
				// e.printStackTrace();
			}
		}
	}

	public void addUser(Long playerId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_ADD_USER_METHOD, null, playerId);
			synchronized (users) {
				users.add(playerId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_USER_METHOD,
					null, playerId);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}

	public void removeUser(Long playerId) {
		synchronized (users) {
			try {
				vetoableRemoteChangeSupport.fireVetoableChange(
						METH_REMOVE_USER_METHOD, null, playerId);
				users.remove(playerId);
				propertyChangeSupport.firePropertyChange(
						METH_REMOVE_USER_METHOD, null, playerId);
			} catch (PropertyVetoException e) {
				// TODO Remote refuse
				// e.printStackTrace();
			}
		}
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
