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

import java.util.Stack;

/**
 * @author Cody Stoutenburg
 * 
 */
public class WorkerPool {
	private final TaskPool taskPool;
	private final Stack<Worker> workers;

	public WorkerPool() {
		taskPool = new TaskPool();
		workers = new Stack<Worker>();

	}

	public void initWorkPool(int threadCount, TaskInfo output) {
		for (int i = 0; i < threadCount; ++i) {
			Worker newWorker = new Worker(taskPool, output);
			Thread t = new Thread(newWorker);
			t.start();
			workers.push(newWorker);
		}
	}

	public void addTask(Task t) {
		taskPool.addTask(t);
	}

	public void stopWorkers(int count) {
		for (int i = 0; i < count; ++i) {
			// remove the worker from the stack and stop it
			workers.pop().stopWorker();
		}
	}

	public void stopWorkers() {
		for (Worker worker : workers) {
			worker.stopWorker();
		}
		workers.clear();
	}
}
