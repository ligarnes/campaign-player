package net.alteiar.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool extends ThreadPoolExecutor {

	public ThreadPool(int maximumThread) {
		super(maximumThread, maximumThread, 10, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(500));

		/*
		 * new RejectedExecutionHandler() {
		 * 
		 * @Override public void rejectedExecution(Runnable r,
		 * ThreadPoolExecutor executor) { Thread.sleep(200); executor.submit(r);
		 * } }
		 */
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
	}

}
