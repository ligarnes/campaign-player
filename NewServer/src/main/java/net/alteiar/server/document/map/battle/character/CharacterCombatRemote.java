package net.alteiar.server.document.map.battle.character;

import java.rmi.RemoteException;

import net.alteiar.server.BaseObservableRemote;
import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.DocumentRemote;

public class CharacterCombatRemote extends DocumentRemote implements
		ICharacterCombatRemote {
	private static final long serialVersionUID = 1L;

	private final Long characterId;

	private int initiative;

	public CharacterCombatRemote(Long characterId, int initiative)
			throws RemoteException {
		super();

		this.characterId = characterId;
		this.initiative = initiative;
	}

	@Override
	public int getInitiative() throws RemoteException {
		return initiative;
	}

	@Override
	public void setInitiative(int initiative) throws RemoteException {
		this.initiative = initiative;
		notifyInitiativeChanged(initiative);
	}

	@Override
	public Long getCharacterId() throws RemoteException {
		return characterId;
	}

	@Override
	public void addCharacterCombatListener(
			ICharacterCombatListenerRemote listener) throws RemoteException {
		this.addListener(ICharacterCombatListenerRemote.class, listener);
	}

	@Override
	public void removeCharacterCombatListener(
			ICharacterCombatListenerRemote listener) throws RemoteException {
		this.removeListener(ICharacterCombatListenerRemote.class, listener);
	}

	protected void notifyInitiativeChanged(int newInit) {
		for (ICharacterCombatListenerRemote listener : getListener(ICharacterCombatListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new NotifyInitiativeChangedTask(this, listener,
							newInit));
		}
	}

	private class NotifyInitiativeChangedTask extends
			BaseNotify<ICharacterCombatListenerRemote> {

		private final int newInit;

		public NotifyInitiativeChangedTask(BaseObservableRemote observable,
				ICharacterCombatListenerRemote observer, int newInit) {
			super(observable, ICharacterCombatListenerRemote.class, observer);
			this.newInit = newInit;
		}

		@Override
		protected void doAction() throws RemoteException {
			this.observer.initiativeChanged(newInit);
		}

	}

	@Override
	public DocumentClient<?> buildProxy() throws RemoteException {
		return new CharacterCombatClient(this);
	}
}
