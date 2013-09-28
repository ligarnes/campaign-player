package net.alteiar.campaign.player.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.alteiar.thread.MyRunnable;

public class Threads {
	private static final ExecutorService THREAD_POOL = new MyTheadPool();

	/**
	 * @see Executors.newCachedThreadPool
	 * @author ligarnes
	 * 
	 */
	public static class MyTheadPool extends ThreadPoolExecutor {
		public MyTheadPool() {
			super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
					new SynchronousQueue<Runnable>());
		}

		@Override
		protected void beforeExecute(Thread t, Runnable r) {
			// MyRunnable run = (MyRunnable) r;
			// System.out.println("app task; start: " + run.getTaskName());
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			// MyRunnable run = (MyRunnable) r;
			// System.out.println("app task; finish: " + run.getTaskName());
		}
	}

	/**
	 * Thread pool execute a task that can be done asynchronously
	 * 
	 * @param run
	 */
	public static void execute(MyRunnable run) {
		// System.out.println("run add task: " + run.getTaskName());
		THREAD_POOL.execute(run);
	}
}
