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
package net.alteiar.client.shared.observer;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BaseObservable {
	private final ConcurrentHashMap<Class<?>, HashSet<Object>> observers;

	public BaseObservable() {
		super();
		observers = new ConcurrentHashMap<Class<?>, HashSet<Object>>();
	}

	protected void addListener(Class<?> key, Object observer) {
		HashSet<Object> set = observers.get(key);
		if (set == null) {
			set = new HashSet<Object>();
			observers.put(key, set);
		}
		set.add(observer);
	}

	protected void removeListener(Class<?> key, Object observer) {
		HashSet<Object> set = observers.get(key);
		if (set != null) {
			set.remove(observer);
		}
	}

	protected HashSet<Object> getListener(Class<?> key) {
		HashSet<Object> set = observers.get(key);
		if (set == null) {
			set = new HashSet<Object>();
			observers.put(key, set);
		}
		return set;
	}
}