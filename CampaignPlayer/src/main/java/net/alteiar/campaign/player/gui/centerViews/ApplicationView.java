package net.alteiar.campaign.player.gui.centerViews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;

import net.alteiar.campaign.player.gui.sideView.SideView;

public class ApplicationView {
	private final String name;
	private final JComponent panelView;
	private final Icon icon;

	private final ArrayList<SideView> sideViews;

	public ApplicationView(String name, JComponent view, Icon icon) {
		this.name = name;
		this.panelView = view;
		this.icon = icon;

		sideViews = new ArrayList<SideView>();
	}

	public String getName() {
		return name;
	}

	public JComponent getView() {
		return panelView;
	}

	public Icon getIcon() {
		return icon;
	}

	public void addSideView(SideView sideView) {
		this.sideViews.add(sideView);
	}

	public List<SideView> getSideViews() {
		return Collections.unmodifiableList(this.sideViews);
	}
}
