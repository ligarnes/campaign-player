package net.alteiar.campaign.player.gui.sideView;

import javax.swing.JPanel;

public class SideView {

	private final String name;
	private final JPanel panelView;
	private Boolean isShow;

	public SideView(String name, JPanel view) {
		this.name = name;
		this.panelView = view;
		this.isShow = true;
	}

	public String getName() {
		return name;
	}

	public JPanel getView() {
		return panelView;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Boolean isShow() {
		return isShow;
	}
}
