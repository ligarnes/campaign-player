package net.alteiar.thread;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyTheadPool {

	private final MyRealThreadPool pool;

	public MyTheadPool(int maximumThread) {
		pool = new MyRealThreadPool(maximumThread);

	}

	public void execute(MyRunnable command) {
		try {
			pool.execute(command);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addTaskObserver(TaskObserver obs) {
		pool.addTaskObserver(obs);
	}

	public void removeTaskObserver(TaskObserver obs) {
		pool.removeTaskObserver(obs);
	}

	public void shutdown() {
		if (!pool.isShutdown()) {
			pool.shutdown();
		}
	}

	private class MyRealThreadPool extends ThreadPoolExecutor {
		private final ArrayList<TaskObserver> observers;

		public MyRealThreadPool(int maximumThread) {
			super(maximumThread, maximumThread, 0L, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());

			observers = new ArrayList<TaskObserver>();
		}

		@Override
		protected void beforeExecute(Thread t, Runnable r) {
			MyRunnable run = (MyRunnable) r;
			notifyTaskStarted(run.getTaskName());
			// MyRunnable run = (MyRunnable) r;
			// System.out.println("remote; start: " + run.getTaskName());
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			MyRunnable run = (MyRunnable) r;
			notifyTaskEnded(run.getTaskName());
			// MyRunnable run = (MyRunnable) r;
			// System.out.println("remote; finish: " + run.getTaskName());
		}

		public void addTaskObserver(TaskObserver obs) {
			synchronized (observers) {
				observers.add(obs);
			}
		}

		public void removeTaskObserver(TaskObserver obs) {
			synchronized (observers) {
				observers.remove(obs);
			}
		}

		@SuppressWarnings("unchecked")
		private ArrayList<TaskObserver> getObservers() {
			ArrayList<TaskObserver> obs = null;
			synchronized (observers) {
				obs = (ArrayList<TaskObserver>) observers.clone();
			}
			return obs;
		}

		private void notifyTaskStarted(String name) {
			for (TaskObserver obs : getObservers()) {
				obs.taskStarted(name);
			}
		}

		private void notifyTaskEnded(String name) {
			for (TaskObserver obs : getObservers()) {
				obs.taskStarted(name);
			}
		}
	}
}