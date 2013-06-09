package net.alteiar.zoom;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelMoveZoom<E extends JPanel & Zoomable> extends JPanel
		implements Zoomable {
	private static final long serialVersionUID = 1L;

	private final E inside;
	private final JScrollPane scroll;

	public PanelMoveZoom(E panel) {
		super();
		inside = panel;
		scroll = new JScrollPane(inside);

		// in order to maximize component size inside
		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER);
	}

	public void addZoomListener(ZoomListener listener) {
		listenerList.add(ZoomListener.class, listener);
	}

	public void removeZoomListener(ZoomListener listener) {
		listenerList.remove(ZoomListener.class, listener);
	}

	public Point getViewPosition() {
		return scroll.getViewport().getViewPosition();
	}

	public void setVerticalScrollPolicy(int policy) {
		scroll.setVerticalScrollBarPolicy(policy);
	}

	public void setHorizontalScrollPolicy(int policy) {
		scroll.setHorizontalScrollBarPolicy(policy);
	}

	public void moveTo(int moveX, int moveY) {
		final Point newViewPos = scroll.getViewport().getViewPosition();
		newViewPos.translate(moveX, moveY);

		int offsetX = scroll.getVerticalScrollBar().getWidth();
		if (!scroll.getVerticalScrollBar().isVisible()) {
			offsetX = 0;
		}
		int offsetY = scroll.getHorizontalScrollBar().getHeight();
		if (!scroll.getHorizontalScrollBar().isVisible()) {
			offsetY = 0;
		}

		newViewPos.x = Math
				.min((inside.getWidth() + offsetX) - scroll.getWidth(),
						newViewPos.x);
		newViewPos.y = Math.min(
				(inside.getHeight() + offsetY) - scroll.getHeight(),
				newViewPos.y);

		newViewPos.x = Math.max(0, newViewPos.x);
		newViewPos.y = Math.max(0, newViewPos.y);

		scroll.getViewport().setViewPosition(newViewPos);
		revalidate();
		repaint();
	}

	public void zoom(int zoomFactor) {
		double scrollFactor = 0.2;
		if (this.inside.getZoomFactor() < 1) {
			scrollFactor = 0.02;
		}

		double zoomValue = this.inside.getZoomFactor() + (-1) * scrollFactor
				* zoomFactor;

		zoomValue = Math.max(0.1, zoomValue);
		zoomValue = Math.min(8, zoomValue);

		this.inside.zoom(zoomValue);
		this.fireZoomChanged(getZoomFactor());
		this.revalidate();
		this.repaint();
	}

	protected void fireZoomChanged(Double zoomFactor) {
		for (ZoomListener listener : listenerList
				.getListeners(ZoomListener.class)) {
			listener.zoomChanged(zoomFactor);
		}
	}

	public void zoom(Point center, int zoomFactor) {
		double oldZoom = inside.getZoomFactor();
		Rectangle oldView = scroll.getViewport().getViewRect();

		this.zoom(zoomFactor);

		// calculate the new view position
		double newZoom = this.inside.getZoomFactor();
		Point newViewPos = new Point();
		newViewPos.x = (int) Math.max(0.0d, (oldView.x + oldView.width / 2.0)
				* newZoom / oldZoom - oldView.width / 2.0);
		newViewPos.y = (int) Math.max(0.0d, (oldView.y + oldView.height / 2.0)
				* newZoom / oldZoom - oldView.height / 2.0);
		scroll.getViewport().setViewPosition(newViewPos);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void zoom(double value) {
		zoom((int) value);
	}

	@Override
	public Double getZoomFactor() {
		return inside.getZoomFactor();
	}
}
