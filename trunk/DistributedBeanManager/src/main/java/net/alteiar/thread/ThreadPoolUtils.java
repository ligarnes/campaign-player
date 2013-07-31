package net.alteiar.thread;

public class ThreadPoolUtils {
	private static final MyTheadPool CLIENT_POOL = new MyTheadPool(25);
	private static final MyTheadPool SERVER_POOL = new MyTheadPool(10);

	public static MyTheadPool getClientPool() {
		return CLIENT_POOL;
	}

	public static MyTheadPool getServerPool() {
		return SERVER_POOL;
	}
}
