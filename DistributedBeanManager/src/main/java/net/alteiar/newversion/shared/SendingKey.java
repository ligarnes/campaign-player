package net.alteiar.newversion.shared;

import net.alteiar.shared.UniqueID;

import com.esotericsoftware.kryonet.Connection;

public class SendingKey {
	private final UniqueID guid;
	private final Connection conn;

	public SendingKey(UniqueID guid, Connection conn) {
		this.guid = guid;
		this.conn = conn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conn == null) ? 0 : conn.hashCode());
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SendingKey other = (SendingKey) obj;
		if (conn == null) {
			if (other.conn != null)
				return false;
		} else if (!conn.equals(other.conn))
			return false;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		return true;
	}

}