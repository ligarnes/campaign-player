package net.alteiar.server.document.map.battle.character;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICharacterCombatListenerRemote extends Remote {
	void initiativeChanged(int newInit) throws RemoteException;
}
