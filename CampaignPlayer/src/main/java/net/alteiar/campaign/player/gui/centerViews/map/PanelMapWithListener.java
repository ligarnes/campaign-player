package net.alteiar.campaign.player.gui.centerViews.map;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.alteiar.beans.map.MapBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.MapElementDrawable;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.button.ButtonDrawable;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapListener;

public class PanelMapWithListener extends PanelMapBasic implements
		MouseListener, MouseMotionListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;

	public PanelMapWithListener(MapBean map, DrawFilter drawInfo) {
		super(map, drawInfo);

		this.setLayout(null);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}

	public void addMapListener(MapListener l) {
		if (l == null) {
			return;
		}
		listenerList.add(MapListener.class, l);
	}

	public void removeMapListener(MapListener l) {
		if (l == null) {
			return;
		}
		listenerList.remove(MapListener.class, l);
	}

	protected MapListener[] getMapListener() {
		return listenerList.getListeners(MapListener.class);
	}

	private MouseEvent createEvent(MouseEvent e) {
		Point p = convertMousePosition(e.getPoint());
		return new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(),
				p.x, p.y, e.getLocationOnScreen().x, e.getLocationOnScreen().y,
				e.getClickCount(), false, e.getButton());
	}

	@Override
	public void mousePressed(MouseEvent orgEvent) {
		MouseEvent e = createEvent(orgEvent);
		Point mapPosition = convertPointPanelToStandard(e.getPoint());

		List<MapElementDrawable> drawables = new ArrayList<MapElementDrawable>();
		for (MapElementDrawable draw : drawableElements) {
			if (draw.contain(mapPosition)) {

				if (!draw.getMapElement().isHiddenForPlayer()) {
					// if visible add it
					drawables.add(draw);
				} else if (CampaignClient.getInstance().getCurrentPlayer()
						.isDm()) {
					// if is mj add it
					drawables.add(draw);
				}
			}
		}

		MapEvent event = new MapEvent(e, map, mapPosition);
		for (MapListener listener : getMapListener()) {
			listener.mousePressed(event);
		}
	}

	@Override
	public void mouseClicked(MouseEvent orgEvent) {
		// notify buttons if any
		boolean btnClicked = notifyButtons(orgEvent);

		if (!btnClicked) {
			MouseEvent e = createEvent(orgEvent);
			Point mapPosition = convertPointPanelToStandard(e.getPoint());

			// notify mapElements if any
			boolean mapElementClicked = notifyMapElements(orgEvent, e,
					mapPosition);

			if (!mapElementClicked) {
				// notify map
				MapEvent event = new MapEvent(e, map, mapPosition);
				for (MapListener listener : getMapListener()) {
					listener.mouseClicked(event);
				}
			}
		}
	}

	private boolean notifyButtons(MouseEvent orgEvent) {
		boolean buttonFound = false;
		Iterator<ButtonDrawable> ittBtn = buttons.iterator();
		while (ittBtn.hasNext() && !buttonFound) {
			ButtonDrawable button = ittBtn.next();

			// is the click on the button
			if (button.contain(orgEvent.getPoint())) {
				// fire button event
				button.fireMouseClicked(orgEvent);
				buttonFound = true;
			}
		}
		return buttonFound;
	}

	private boolean notifyMapElements(MouseEvent orgEvent, MouseEvent e,
			Point mapPosition) {
		boolean mapElementFound = false;

		List<MapElementDrawable> drawables = new ArrayList<MapElementDrawable>();
		MapElementDrawable selected = null;
		for (MapElementDrawable draw : drawableElements) {
			if (draw.contain(mapPosition)) {
				if (draw.isSelected()) {
					selected = draw;
				}
				if (!draw.getMapElement().isHiddenForPlayer()) {
					// if visible add it
					drawables.add(draw);
				} else if (CampaignClient.getInstance().getCurrentPlayer()
						.isDm()) {
					// if is mj add it
					drawables.add(draw);
				}
			}
		}

		if (!drawables.isEmpty()) {
			if (selected != null) {
				selected.fireMouseClicked(e);
			} else {
				selectElementDrawable(orgEvent, e, drawables);
			}
			mapElementFound = true;
		}

		return mapElementFound;
	}

	@Override
	public void mouseReleased(MouseEvent orgEvent) {
		MouseEvent e = createEvent(orgEvent);
		Point mapPosition = convertPointPanelToStandard(e.getPoint());

		List<MapElementDrawable> drawables = new ArrayList<MapElementDrawable>();
		for (MapElementDrawable draw : drawableElements) {
			if (draw.contain(mapPosition)) {

				if (!draw.getMapElement().isHiddenForPlayer()) {
					// if visible add it
					drawables.add(draw);
				} else if (CampaignClient.getInstance().getCurrentPlayer()
						.isDm()) {
					// if is mj add it
					drawables.add(draw);
				}
			}
		}

		MapEvent event = new MapEvent(e, map, mapPosition);
		for (MapListener listener : getMapListener()) {
			listener.mouseReleased(event);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		e = createEvent(e);
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
		e = createEvent(e);
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

	private void selectElementDrawable(final MouseEvent orgEvent,
			final MouseEvent event, List<MapElementDrawable> elements) {

		if (elements.size() > 1) {
			JPopupMenu menu = new JPopupMenu();

			for (final MapElementDrawable mapElement : elements) {
				JMenuItem item = new JMenuItem(mapElement.getNameFormat());
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mapElement.fireMouseClicked(event);
					}
				});
				menu.add(item);
			}

			menu.show(orgEvent.getComponent(), orgEvent.getX(), orgEvent.getY());
		} else if (elements.size() > 0) {
			elements.get(0).fireMouseClicked(event);
		}
	}
}
