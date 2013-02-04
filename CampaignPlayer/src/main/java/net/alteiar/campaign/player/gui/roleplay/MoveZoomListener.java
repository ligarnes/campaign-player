package net.alteiar.campaign.player.gui.roleplay;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.tools.PanelMoveZoom;

public class MoveZoomListener extends MouseAdapter {
	private final PanelMoveZoom<?> panel;
	private Point first;

	public MoveZoomListener(PanelMoveZoom<?> panel) {
		this.panel = panel;
		first = null;
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			first = event.getPoint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (first != null) {
			int moveX = first.x - e.getX();
			int moveY = first.y - e.getY();
			panel.moveTo(moveX, moveY);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		panel.zoom(e.getPoint(), e.getWheelRotation());
	}

}
