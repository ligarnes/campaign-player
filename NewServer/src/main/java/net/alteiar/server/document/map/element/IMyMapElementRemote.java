package net.alteiar.server.document.map.element;

import java.rmi.RemoteException;

public interface IMyMapElementRemote extends IMapElementRemote {

	MapElement getObject() throws RemoteException;
}
