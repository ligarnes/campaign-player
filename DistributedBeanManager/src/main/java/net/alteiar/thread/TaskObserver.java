package net.alteiar.thread;

public interface TaskObserver {
	public void taskStarted(String name);

	public void taskEnded(String name);
}