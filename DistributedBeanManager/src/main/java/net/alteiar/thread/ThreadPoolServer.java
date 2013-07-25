package net.alteiar.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolServer {

	private static final Integer THREAD_MAX = 10;

	private static final ThreadPoolExecutor SERVER_POOL = new ThreadPoolExecutor(
			THREAD_MAX, THREAD_MAX, 10, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(500));

	public static void execute(Runnable run) {
		SERVER_POOL.execute(run);
	}

	public static void shutdown() {
		SERVER_POOL.shutdown();
	}
}
