package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.map.elements.MapElement;

public class RotateMapListener extends ActionMapListener {

	private Point first;
	private final MapElement mapElement;

	public RotateMapListener(GlobalMapListener mapListener,
			MapElement mapElement, Point begin) {
		super(mapListener);
		this.mapElement = mapElement;
		this.first = begin;
	}

	@Override
	public void mouseClicked(MapEvent event) {

		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			// TODO mapElement.applyRotate();
			mapListener.defaultListener();
		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			JPopupMenu popup = new JPopupMenu();
			popup.add(buildMenuRotate(mapElement));
			popup.add(buildMenuRotate(mapElement, 45.0));
			popup.add(buildMenuRotate(mapElement, 90.0));
			popup.add(buildMenuRotate(mapElement, 180.0));
			popup.show(event.getMouseEvent().getComponent(), event
					.getMouseEvent().getX(), event.getMouseEvent().getY());
		}
	}

	@Override
	public void mouseMove(MouseEvent e, Point mapPosition) {
		Double angle = mapElement.getAngle();
		double distance = first.distance(mapPosition);

		double diffX = first.x - mapPosition.x;
		if (diffX > 0) {
			angle += distance;
		} else {
			angle -= distance;
		}

		mapElement.setAngle(angle);
		first = mapPosition;
	}

	private JMenuItem buildMenuRotate(final MapElement element,
			final Double angle) {
		JMenuItem menu45 = new JMenuItem(angle + " degres");
		menu45.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rotateMapElement(element, angle);
			}
		});
		return menu45;
	}

	private JMenuItem buildMenuRotate(final MapElement element) {
		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				element.setAngle(0.0);
				// TODO element.applyRotate();
			}
		});

		return reset;
	}

	private void rotateMapElement(MapElement rotate, Double angle) {
		rotate.setAngle(rotate.getAngle() + angle);
		// TODO rotate.applyRotate();
	}
}
