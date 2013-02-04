package net.alteiar.server.shared.observer.campaign;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.observer.BaseObservableRemote;

public class MediaObservableRemote extends BaseObservableRemote {
	private static final long serialVersionUID = 1L;

	private static Class<?> LISTENER = IMediaObserverRemote.class;

	public MediaObservableRemote() throws RemoteException {
		super();
	}

	public void addMediaManagerListener(IMediaObserverRemote listener)
			throws RemoteException {
		this.addListener(LISTENER, listener);
	}

	public void removeMediaManagerListener(IMediaObserverRemote listener)
			throws RemoteException {
		this.removeListener(LISTENER, listener);
	}

	protected void notifyMediaAdded(Long remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MediaAddedTask(this,
					observer, remote));
		}
	}

	protected void notifyMediaRemoved(Long remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MediaRemovedTask(
					this, observer, remote));
		}
	}

	private class MediaAddedTask extends BaseNotify {

		private final Long added;

		public MediaAddedTask(MediaObservableRemote observable,
				Remote observer, Long added) {
			super(observable, LISTENER, observer);
			this.added = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IMediaObserverRemote) observer).mediaAdded(added);
		}

		@Override
		public String getStartText() {
			return "start media added";
		}

		@Override
		public String getFinishText() {
			return "finish media removed";
		}
	}

	private class MediaRemovedTask extends BaseNotify {

		private final Long removed;

		public MediaRemovedTask(MediaObservableRemote observable,
				Remote observer, Long added) {
			super(observable, LISTENER, observer);
			this.removed = added;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IMediaObserverRemote) observer).mediaRemoved(removed);
		}

		@Override
		public String getStartText() {
			return "start notify battled player";
		}

		@Override
		public String getFinishText() {
			return "finish notify battle player";
		}
	}
}
