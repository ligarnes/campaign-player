package net.alteiar.shared;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;


public class UniqueID implements Serializable {
	@Attribute
	private static final long serialVersionUID = 1L;

	private static String MAC_ADRESS = null;
	@Element
	private String guid;
	
	private static String getMacAddress() {
		if (MAC_ADRESS == null) {
			InetAddress ip;
			try {
				ip = InetAddress.getLocalHost();
				NetworkInterface network = NetworkInterface
						.getByInetAddress(ip);
				// ipAdress = ip.toString();
				byte[] mac = network.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i],
							(i < mac.length - 1) ? "-" : ""));
				}
				MAC_ADRESS = sb.toString();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return MAC_ADRESS;
	}

	/**
	 * Generate Globaly Unique Id We use the random uuid generator but to avoid
	 * collision we use: <br/>
	 * <ul>
	 * <li>mac address so the id is unique to specific computer</li>
	 * <li>timestamp so the id is unique to specific time so the collision are
	 * very unlikely</li>
	 * </ul>
	 * 
	 * @return a Globaly Unique Identifier
	 */
	private static String generateGuid() {
		Date time = new Date();
		return getMacAddress() + ":" + UUID.randomUUID() + ":" + time.getTime();
	}

	public UniqueID() {
		guid = generateGuid();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		UniqueID other = (UniqueID) obj;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return guid;
	}

}
