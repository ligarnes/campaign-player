package net.alteiar.campaign.player.gui.centerViews.map.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

public abstract class Drawable {
	private final EventListenerList listeners;

	public Drawable() {
		listeners = new EventListenerList();
	}

	public abstract void draw(Graphics2D g2, double zoomFactor, boolean isDm);

	public void addDrawableMouseListener(DrawableMouseListener listener) {
		listeners.add(DrawableMouseListener.class, listener);
	}

	public void addDrawableListener(DrawableListener listener) {
		listeners.add(DrawableListener.class, listener);
	}

	public void removeDrawableListener(DrawableListener listener) {
		listeners.remove(DrawableListener.class, listener);
	}

	public boolean contain(Point p) {
		return false;
	}

	public void fireMouseClicked(MouseEvent e) {
		DrawableMouseListener[] listener = listeners
				.getListeners(DrawableMouseListener.class);

		for (DrawableMouseListener drawableListener : listener) {
			drawableListener.mouseClicked(e, this);
		}
	}

	/*
	 * public void fireMousePressed(MouseEvent e) { DrawableMouseListener[]
	 * listener = listeners .getListeners(DrawableMouseListener.class);
	 * 
	 * for (DrawableMouseListener drawableListener : listener) {
	 * drawableListener.mousePressed(e, this); } }
	 * 
	 * public void fireMouseReleased(MouseEvent e) { DrawableMouseListener[]
	 * listener = listeners .getListeners(DrawableMouseListener.class);
	 * 
	 * for (DrawableMouseListener drawableListener : listener) {
	 * drawableListener.mouseReleased(e, this); } }
	 */

	protected void fireRedraw() {
		DrawableListener[] listener = listeners
				.getListeners(DrawableListener.class);

		for (DrawableListener drawableListener : listener) {
			drawableListener.redraw();
		}
	}

	protected void fireRemove() {
		DrawableListener[] listener = listeners
				.getListeners(DrawableListener.class);

		for (DrawableListener drawableListener : listener) {
			drawableListener.remove(this);
		}
	}

	public interface DrawableListener extends EventListener {
		public void redraw();

		public void remove(Drawable drawable);
	}

	public interface DrawableMouseListener extends EventListener {
		/**
		 * Invoked when the mouse button has been clicked (pressed and released)
		 * on a component.
		 */
		public void mouseClicked(MouseEvent e, Drawable draw);

		/**
		 * Invoked when a mouse button has been pressed on a component.
		 */
		// public void mousePressed(MouseEvent e, Drawable draw);

		/**
		 * Invoked when a mouse button has been released on a component.
		 */
		// public void mouseReleased(MouseEvent e, Drawable draw);
	}

}
