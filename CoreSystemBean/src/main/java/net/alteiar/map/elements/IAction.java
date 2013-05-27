package net.alteiar.map.elements;

public abstract class IAction {
	public abstract String getName();

	public abstract Boolean canDoAction();

	public abstract void doAction(int xOnScreen, int yOnScreen)
			throws Exception;
}
