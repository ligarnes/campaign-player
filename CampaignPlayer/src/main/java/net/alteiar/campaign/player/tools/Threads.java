package net.alteiar.campaign.player.tools;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.alteiar.thread.MyRunnable;

public class Threads {

	private static final Integer THREAD_MAX = 10;

	private static final MyTheadPool THREAD_POOL = new MyTheadPool(THREAD_MAX);

	public static class MyTheadPool extends ThreadPoolExecutor {
		public MyTheadPool(int maximumThread) {
			super(maximumThread, maximumThread, 10, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(500));
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
