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
package net.alteiar.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class BaseObservableClient implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient HashMap<Class<?>, HashSet<Object>> observers;

	public BaseObservableClient() {
		super();

		observers = new HashMap<Class<?>, HashSet<Object>>();
	}

	protected void addListener(Class<?> key, Object listener) {
		synchronized (observers) {
			HashSet<Object> set = observers.get(key);
			if (set == null) {
				set = new HashSet<Object>();
				observers.put(key, set);
			}
			set.add(listener);
		}
	}

	protected void removeListener(Class<?> key, Object listener) {
		synchronized (observers) {
			HashSet<Object> set = observers.get(key);
			if (set != null) {
				set.remove(listener);
			}
		}
	}

	protected <E> ArrayList<E> getListener(Class<E> key) {
		ArrayList<E> obs = new ArrayList<E>();
		synchronized (observers) {
			HashSet<Object> set = observers.get(key);
			if (set == null) {
				set = new HashSet<Object>();
				observers.put(key, set);
			}

			for (Object remote : set) {
				obs.add((E) remote);
			}
		}
		return obs;
	}

	public void loadDocument() {
		observers = new HashMap<Class<?>, HashSet<Object>>();
	}
}
