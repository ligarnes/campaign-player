package net.alteiar.campaign.player.gui.centerViews.map.listener.mapElement;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.event.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.event.MapListener;
import net.alteiar.map.elements.MapElement;

public class RotateState extends MapElementListenerState implements MapListener {

	private Point first;
	private final MapElement mapElement;

	public RotateState(MapEditableInfo info, MapElementListener listener,
			MapElement mapElement) {
		super(info, listener);
		this.mapElement = mapElement;
		this.first = mapElement.getCenterPosition();
		this.getMapEditableInfo().getPanelMap().addMapListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent event, MapElement element) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			// TODO mapElement.applyRotate();
			defaultState();
		} else if (SwingUtilities.isRightMouseButton(event)) {
			JPopupMenu popup = new JPopupMenu();
			popup.add(buildMenuRotate(mapElement));
			popup.add(buildMenuRotate(mapElement, 45.0));
			popup.add(buildMenuRotate(mapElement, 90.0));
			popup.add(buildMenuRotate(mapElement, 180.0));
			popup.show(event.getComponent(), event.getX(), event.getY());
		}
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

	@Override
	public void mouseClicked(MapEvent element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MapEvent element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MapEvent element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseElementEntered(MapEvent element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseElementExited(MapEvent element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
		// TODO Auto-generated method stub

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

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
		// TODO Auto-generated method stub

	}
}
