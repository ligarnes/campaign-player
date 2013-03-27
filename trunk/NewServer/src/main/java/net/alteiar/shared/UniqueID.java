package net.alteiar.shared;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

public class UniqueID {
	
	private static String MacAdress;
	private static String ipAdress;
	private static Timestamp date;
	
	public UniqueID()
	{
		InetAddress ip;
			try {
				ip = InetAddress.getLocalHost();
				NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				ipAdress=ip.toString();
				byte[] mac = network.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
				}
				MacAdress=sb.toString();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Date time= new Date();
			date=new Timestamp(time.getTime()); 
	}
	
	public String toString()
	{
		return MacAdress+date;
	}

}
