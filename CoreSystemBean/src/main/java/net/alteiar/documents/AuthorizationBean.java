package net.alteiar.documents;

import java.beans.PropertyVetoException;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public abstract class AuthorizationBean extends BasicBean {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";

	public static final String PROP_OWNER_PROPERTY = "owner";
	public static final String PROP_MODIFIERS_PROPERTY = "modifiers";
	public static final String PROP_USERS_PROPERTY = "users";
	public static final String PROP_PUBLIC_PROPERTY = "public";

	public static final String METH_ADD_MODIFIER_METHOD = "addModifier";
	public static final String METH_REMOVE_MODIFIER_METHOD = "removeModifier";
	public static final String METH_ADD_USER_METHOD = "addUser";
	public static final String METH_REMOVE_USER_METHOD = "removeUser";

	@Element
	private String documentName;

	@Element
	private UniqueID owner;
	@ElementList
	private HashSet<UniqueID> modifiers;
	@ElementList
	private HashSet<UniqueID> users;
	// public enable view by every one but not modify
	@Element
	private Boolean isPublic;

	protected AuthorizationBean() {
		super();
		documentName = getId().toString();
		modifiers = new HashSet<UniqueID>();
		users = new HashSet<UniqueID>();
		isPublic = false;
	}

	public AuthorizationBean(String name) {
		super();
		documentName = name;
		modifiers = new HashSet<UniqueID>();
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
		}
	}

	public UniqueID getOwner() {
		return owner;
	}

	public void setOwner(UniqueID owner) {
		if (owner == null) {
			throw new NullPointerException("The owner could'nt be null");
		}
		UniqueID oldValue = this.owner;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_OWNER_PROPERTY,
					oldValue, owner);
			this.owner = owner;
			propertyChangeSupport.firePropertyChange(PROP_OWNER_PROPERTY,
					oldValue, owner);
		} catch (PropertyVetoException e) {
		}
	}

	public HashSet<UniqueID> getModifiers() {
		return modifiers;
	}

	public void setModifiers(HashSet<UniqueID> owners) {
		HashSet<UniqueID> oldValue = this.modifiers;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_MODIFIERS_PROPERTY, oldValue, owners);
			this.modifiers = owners;
			propertyChangeSupport.firePropertyChange(PROP_MODIFIERS_PROPERTY,
					oldValue, owners);
		} catch (PropertyVetoException e) {
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
		}
	}

	public boolean isAllowedToApplyChange(Player player) {
		return isAllowedToApplyChange(player.getId());
	}

	public boolean isAllowedToApplyChange(UniqueID cliendId) {
		Player player = CampaignClient.getInstance().getBean(cliendId);
		if (player.isMj()) {
			return true;
		} else {
			return modifiers.contains(cliendId) || owner.equals(cliendId);
		}
	}

	public boolean isAllowedToSee(Player player) {
		return isAllowedToSee(player.getId());
	}

	public boolean isAllowedToSee(UniqueID cliendId) {
		return (isPublic || users.contains(cliendId) || isAllowedToApplyChange(cliendId));
	}

	public void addModifier(UniqueID playerId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_ADD_MODIFIER_METHOD, null, playerId);
			synchronized (modifiers) {
				modifiers.add(playerId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_MODIFIER_METHOD,
					null, playerId);
		} catch (PropertyVetoException e) {
		}
	}

	public void removeModifier(UniqueID playerId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_REMOVE_MODIFIER_METHOD, null, playerId);
			synchronized (modifiers) {
				modifiers.remove(playerId);
			}
			propertyChangeSupport.firePropertyChange(
					METH_REMOVE_MODIFIER_METHOD, null, playerId);
		} catch (PropertyVetoException e) {
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
		}
	}
}
