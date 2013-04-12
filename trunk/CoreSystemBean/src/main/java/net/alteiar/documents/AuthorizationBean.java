package net.alteiar.documents;

import java.beans.PropertyVetoException;
import java.util.HashSet;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public abstract class AuthorizationBean extends BasicBeans {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";
	public static final String PROP_OWNERS_PROPERTY = "owners";
	public static final String PROP_USERS_PROPERTY = "users";
	public static final String PROP_PUBLIC_PROPERTY = "public";

	public static final String METH_ADD_OWNER_METHOD = "addOwner";
	public static final String METH_REMOVE_OWNER_METHOD = "removeOwner";
	public static final String METH_ADD_USER_METHOD = "addUser";
	public static final String METH_REMOVE_USER_METHOD = "removeUser";

	@Element
	private String documentName;

	@ElementList
	private HashSet<UniqueID> owners;
	@ElementList
	private HashSet<UniqueID> users;
	// public enable view by every one but not modify
	@Element
	private Boolean isPublic;

	protected AuthorizationBean() {
		super();
		documentName = getId().toString();
		owners = new HashSet<UniqueID>();
		users = new HashSet<UniqueID>();
		isPublic = false;
	}

	public AuthorizationBean(String name) {
		super();
		documentName = name;
		owners = new HashSet<UniqueID>();
		users = new HashSet<UniqueID>();
		isPublic = false;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		String oldDocumentName = documentName;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_NAME_PROPERTY,
					oldDocumentName, documentName);
			this.documentName = documentName;
			propertyChangeSupport.firePropertyChange(PROP_NAME_PROPERTY,
					oldDocumentName, documentName);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}

	public HashSet<UniqueID> getOwners() {
		return owners;
	}

	public void setOwners(HashSet<UniqueID> owners) {
		HashSet<UniqueID> oldValue = this.owners;
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

	public HashSet<UniqueID> getUsers() {
		return users;
	}

	public void setUsers(HashSet<UniqueID> users) {
		HashSet<UniqueID> oldValue = this.users;
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

	public boolean isAllowedToApplyChange(UniqueID cliendId) {
		return owners.contains(cliendId);
	}

	public boolean isAllowedToSee(Player player) {
		return isAllowedToSee(player.getId());
	}

	public boolean isAllowedToSee(UniqueID cliendId) {
		return (isPublic || users.contains(cliendId) || owners
				.contains(cliendId));
	}

	public void addOwner(UniqueID playerId) {
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

	public void removeOwner(UniqueID playerId)
			throws AuthorizationManagerException {
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

	public void addUser(UniqueID playerId) {
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

	public void removeUser(UniqueID playerId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_REMOVE_USER_METHOD, null, playerId);
			synchronized (users) {
				users.remove(playerId);
			}
			propertyChangeSupport.firePropertyChange(METH_REMOVE_USER_METHOD,
					null, playerId);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}

	public Boolean getPublic() {
		return isPublic;
	}

	public void setPublic(Boolean isPublic) {
		Boolean oldValue = this.isPublic;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_PUBLIC_PROPERTY, oldValue, isPublic);
			this.isPublic = isPublic;
			propertyChangeSupport.firePropertyChange(PROP_PUBLIC_PROPERTY,
					oldValue, isPublic);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}

	public class AuthorizationManagerException extends Exception {
		private static final long serialVersionUID = 1L;

		public AuthorizationManagerException(String message) {
			super(message);
		}
	}

}
