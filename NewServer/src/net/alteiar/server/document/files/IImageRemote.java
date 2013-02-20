package net.alteiar.server.document.files;

import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.shared.TransfertImage;

public interface IImageRemote extends IDocumentRemote {
	TransfertImage getImage() throws RemoteException;
}
