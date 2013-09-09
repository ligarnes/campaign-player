package net.alteiar.newversion.shared.message;

import java.util.Date;

import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;

public class MessageModifyValue implements IUniqueObject {
	private final UniqueID objectId;

	private final String propertyName;
	private final Object newValue;
	private Long timestamp;

	public MessageModifyValue() {
		super();
		this.objectId = null;
		this.propertyName = null;
		this.newValue = null;
	}

	public MessageModifyValue(UniqueID objectId, String propertyName,
			Object newValue) {
		super();
		this.objectId = objectId;
		this.propertyName = propertyName;
		this.newValue = newValue;
	}

	public void setTimestamp() {
		this.timestamp = new Date().getTime();
	}

	@Override
	public UniqueID getId() {
		return objectId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getNewValue() {
		return newValue;
	}

	public Long getTimestamp() {
		return timestamp;
	}
}
