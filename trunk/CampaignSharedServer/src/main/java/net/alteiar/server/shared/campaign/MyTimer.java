package net.alteiar.server.shared.campaign;

public class MyTimer {

	private long begin;
	private long end;

	public MyTimer() {

	}

	public void startTimer() {
		begin = System.currentTimeMillis();
	}

	public void endTimer() {
		end = System.currentTimeMillis();
	}

	public void printTime(String task) {
		System.out.println(task + " take " + (end - begin) + "ms");
	}
}
