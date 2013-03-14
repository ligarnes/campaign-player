package net.alteiar.server.document.map.element;

import java.awt.Dimension;
import java.awt.Toolkit;

public abstract class IAction {
	public abstract String getName();

	public abstract Boolean canDoAction();

	public void doAction() throws Exception {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		doAction(dim.width / 2, dim.height / 2);
	}

	public abstract void doAction(int xOnScreen, int yOnScreen)
			throws Exception;
}
