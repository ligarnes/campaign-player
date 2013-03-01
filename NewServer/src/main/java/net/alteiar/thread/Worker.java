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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Worker implements Runnable {
	private static final Logger WORKER_LOGGER = Logger.getLogger("Worker");

	/* only for test and debug version
	static {
		try {
			WORKER_LOGGER.addHandler(new FileHandler(
					"./logs/SERVER_TASK_LOG.log", 50, 2, true));
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}*/

	private final TaskInfo output;
	private final TaskPool allTask;
	private boolean finish;

	public Worker(TaskPool taskPool, TaskInfo output) {
		allTask = taskPool;
		this.output = output;
		finish = false;
	}

	@Override
	public void run() {
		while (!finish) {
			// here we wait for task
			Task t = allTask.getTask();

			long begin = System.currentTimeMillis();
			this.output.setTaskName(t.getStartText());
			this.output.taskStarted();
			// do the task
			t.run();
			this.output.setTaskName(t.getFinishText());
			this.output.taskFinished();
			long end = System.currentTimeMillis();
			long time = (end - begin) / 1000;
			if (time > 1) {
				WORKER_LOGGER.log(Level.INFO, t.getFinishText() + " take "
						+ time + " second");
			}
		}
	}

	/**
	 * the worker may do a last task before he die
	 */
	public void stopWorker() {
		this.finish = true;
	}
}
