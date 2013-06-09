package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.zoom.Zoomable;

public class ZoomInOutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final Zoomable zoomable;
	private final int zoomValue;

	public ZoomInOutAction(Zoomable info, int zoomValue) {
		this.zoomable = info;
		this.zoomValue = zoomValue;
		if (zoomValue > 0) {
			putValue(NAME, "-");
		} else {
			putValue(NAME, "+");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		zoomable.zoom(zoomValue);
	}
}
