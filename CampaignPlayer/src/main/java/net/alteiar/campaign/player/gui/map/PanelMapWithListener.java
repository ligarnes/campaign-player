package net.alteiar.campaign.player.gui.map;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.campaign.player.gui.map.event.MapListener;
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.map.elements.MapElement;

public class PanelMapWithListener extends PanelBasicMap implements
		MouseListener, MouseMotionListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;

	public PanelMapWithListener(Battle map, DrawInfo drawInfo) {
		super(map, drawInfo);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}

	public synchronized void addMapListener(MapListener l) {
		if (l == null) {
			return;
		}
		listenerList.add(MapListener.class, l);
	}

	protected MapListener[] getMapListener() {
		return listenerList.getListeners(MapListener.class);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point mapPosition = convertPointPanelToStandard(e.getPoint());
		MapElement mapElement = this.getElementAt(mapPosition);

		MapEvent event = new MapEvent(e, mapElement, mapPosition);
		for (MapListener listener : getMapListener()) {
			listener.mousePressed(event);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point mapPosition = convertPointPanelToStandard(e.getPoint());
		MapElement mapElement = this.getElementAt(mapPosition);

		MapEvent event = new MapEvent(e, mapElement, mapPosition);
		for (MapListener listener : getMapListener()) {
			listener.mouseClicked(event);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point mapPosition = convertPointPanelToStandard(e.getPoint());
		MapElement mapElement = this.getElementAt(mapPosition);

		MapEvent event = new MapEvent(e, mapElement, mapPosition);
		for (MapListener listener : getMapListener()) {
			listener.mouseReleased(event);
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		final Point mapPosition = convertPointPanelToStandard(e.getPoint());

		for (MapListener listener : getMapListener()) {
			listener.mouseMove(e, mapPosition);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point mapPosition = convertPointPanelToStandard(e.getPoint());
		for (MapListener listener : getMapListener()) {
			listener.mouseDragged(e, mapPosition);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Point mapPosition = convertPointPanelToStandard(e.getPoint());
		for (MapListener listener : getMapListener()) {
			listener.mouseWheelMoved(e, mapPosition);
		}
	}
}
