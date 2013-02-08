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
package net.alteiar.thread;

import java.util.LinkedList;

/**
 * @author Cody Stoutenburg
 * 
 *         this class is a wookpool, you can add task whenever you want and it
 *         will be done as soon as possible
 */
public class TaskPool {
	private final LinkedList<Task> allTask;

	public TaskPool() {
		allTask = new LinkedList<Task>();
	}

	public synchronized void addTask(Task t) {
		allTask.offer(t);
		this.notifyAll();
	}

	public synchronized Task getTask() {
		while (allTask.isEmpty()) {
			// if no task we wait until one is add
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return allTask.poll();
	}
}
