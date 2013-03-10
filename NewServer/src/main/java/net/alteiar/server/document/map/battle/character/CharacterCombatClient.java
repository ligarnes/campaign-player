package net.alteiar.server.document.map.battle.character;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.character.CharacterClient;

public class CharacterCombatClient extends
		DocumentClient<ICharacterCombatRemote> {
	private static final long serialVersionUID = 1L;

	private transient ICharacterCombatListenerRemote listener;
	private final Long characterId;
	private Integer initiative;

	public CharacterCombatClient(ICharacterCombatRemote remote)
			throws RemoteException {
		super(remote);

		characterId = remote.getCharacterId();
		initiative = remote.getInitiative();
	}

	public CharacterClient getCharacter() {
		return (CharacterClient) CampaignClient.getInstance().getDocument(
				characterId);
	}

	public Integer getInitiative() {
		return this.initiative;
	}

	public void setInitiative(Integer initiative) {
		if (!this.initiative.equals(initiative)) {
			try {
				getRemote().setInitiative(initiative);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
		listener = new CharacterCombatListener(getRemote());
	}

	@Override
	protected void closeDocument() throws RemoteException {
		getRemote().removeCharacterCombatListener(listener);
	}

	protected void setLocalInitiative(Integer initiative) {
		this.initiative = initiative;
		// notify listener
	}

	private class CharacterCombatListener extends UnicastRemoteObject implements
			ICharacterCombatListenerRemote {
		private static final long serialVersionUID = 1L;

		public CharacterCombatListener(ICharacterCombatRemote remote)
				throws RemoteException {
			remote.addCharacterCombatListener(this);
		}

		@Override
		public void initiativeChanged(int newInit) throws RemoteException {
			setLocalInitiative(newInit);
		}
	}
}
