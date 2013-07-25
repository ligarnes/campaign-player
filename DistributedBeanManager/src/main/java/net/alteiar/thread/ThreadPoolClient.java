package net.alteiar.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolClient {
	private static final MyTheadPool CLIENT_POOL = new MyTheadPool(25);

	public static class MyTheadPool extends ThreadPoolExecutor {
		public MyTheadPool(int maximumThread) {
			super(maximumThread, maximumThread, 10, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(500));
		}

		@Override
		protected void beforeExecute(Thread t, Runnable r) {
			// MyRunnable run = (MyRunnable) r;
			// System.out.println("remote; start: " + run.getTaskName());
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			// MyRunnable run = (MyRunnable) r;
			// System.out.println("remote; finish: " + run.getTaskName());
		}
	}

	public static void execute(MyRunnable run) {
		CLIENT_POOL.execute(run);
	}
}
