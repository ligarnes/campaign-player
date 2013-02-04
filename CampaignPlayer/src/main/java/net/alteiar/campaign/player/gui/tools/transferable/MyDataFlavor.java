package net.alteiar.campaign.player.gui.tools.transferable;

import java.awt.datatransfer.DataFlavor;

public class MyDataFlavor extends DataFlavor {

	public MyDataFlavor() {
		super();
	}

	public MyDataFlavor(Class<?> representationClass,
			String humanPresentableName) {
		super(representationClass, humanPresentableName);
	}

	public MyDataFlavor(String mimeType, String humanPresentableName,
			ClassLoader classLoader) throws ClassNotFoundException {
		super(mimeType, humanPresentableName, classLoader);
	}

	public MyDataFlavor(String mimeType, String humanPresentableName) {
		super(mimeType, humanPresentableName);
	}

	public MyDataFlavor(String mimeType) throws ClassNotFoundException {
		super(mimeType);
	}

}
