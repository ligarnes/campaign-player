package net.alteiar.server.document.map.battle.character;

import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;

public interface ICharacterCombatRemote extends IDocumentRemote {
	int getInitiative() throws RemoteException;

	void setInitiative(int initiative) throws RemoteException;

	Long getCharacterId() throws RemoteException;

	void addCharacterCombatListener(ICharacterCombatListenerRemote listener)
			throws RemoteException;

	void removeCharacterCombatListener(ICharacterCombatListenerRemote listener)
			throws RemoteException;
}
