/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.server.shared.observer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;

import net.alteiar.thread.Task;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BaseObservableRemote extends GUIDRemote {
	private static final long serialVersionUID = -3655647076385346860L;

	private final HashMap<Class<?>, HashSet<Remote>> observers;

	/**
	 * @throws RemoteException
	 */
	public BaseObservableRemote() throws RemoteException {
		super();
		observers = new HashMap<Class<?>, HashSet<Remote>>();
	}

	public synchronized void addListener(Class<?> key, Remote observer)
			throws RemoteException {
		HashSet<Remote> set = observers.get(key);
		if (set == null) {
			set = new HashSet<Remote>();
			observers.put(key, set);
		}
		set.add(observer);
	}

	public synchronized void removeListener(Class<?> key, Remote observer)
			throws RemoteException {
		HashSet<Remote> set = observers.get(key);
		if (set != null) {
			set.remove(observer);
		}
	}

	protected Remote[] getListener(Class<?> key) {
		Remote[] obs = null;
		synchronized (this) {
			HashSet<Remote> set = observers.get(key);
			if (set == null) {
				set = new HashSet<Remote>();
				observers.put(key, set);
			}
			obs = new Remote[set.size()];
			set.toArray(obs);
		}
		return obs;
	}

	public abstract class BaseNotify implements Task {
		private final BaseObservableRemote observable;
		private final Class<?> key;
		protected final Remote observer;

		public BaseNotify(BaseObservableRemote observable, Class<?> key,
				Remote observer) {
			this.key = key;
			this.observer = observer;
			this.observable = observable;
		}

		@Override
		public final void run() {
			try {
				doAction();
			} catch (RemoteException e) {
				try {
					// The observer is unreachable he may have been disconnected
					observable.removeListener(key, observer);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}

		protected abstract void doAction() throws RemoteException;
	}
}
