package net.alteiar.thread;

public class ThreadPoolUtils {
	private static MyTheadPool CLIENT_POOL;
	private static MyTheadPool SERVER_POOL;

	public static void startServerThreadPool() {
		if (SERVER_POOL != null) {
			SERVER_POOL.shutdown();
		}
		SERVER_POOL = new MyTheadPool(10);
	}

	public static void startClientThreadPool() {
		if (CLIENT_POOL != null) {
			CLIENT_POOL.shutdown();
		}
		CLIENT_POOL = new MyTheadPool(25);
	}

	public static MyTheadPool getClientPool() {
		return CLIENT_POOL;
	}

	public static MyTheadPool getServerPool() {
		return SERVER_POOL;
	}
}
