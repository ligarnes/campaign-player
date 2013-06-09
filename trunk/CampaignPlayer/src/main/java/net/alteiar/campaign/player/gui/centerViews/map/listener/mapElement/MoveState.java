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
import net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse.LineToMouse;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse.PathToMouse;
import net.alteiar.campaign.player.gui.centerViews.map.event.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.event.MapListener;
import net.alteiar.map.elements.MapElement;

public class MoveState extends MapElementListenerState implements MapListener {
	private final MapElement mapElement;
	private final LineToMouse draw;

	public MoveState(MapEditableInfo info, MapElementListener listener,
			MapElement mapElement) {
		super(info, listener);

		this.mapElement = mapElement;

		if (info.getFixGrid()) {
			draw = new PathToMouse(info, mapElement);
		} else {
			draw = new LineToMouse(info, mapElement.getCenterPosition());
		}
		info.addDrawable(draw);

		mapElement.setSelected(true);

		getMapEditableInfo().getPanelMap().addMapListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent event, MapElement element) {
		Point mapPosition = getMapEditableInfo().convertPointPanelToStandard(
				event.getPoint());

		if (SwingUtilities.isLeftMouseButton(event)) {
			finishMove(mapPosition);
		} else if (SwingUtilities.isRightMouseButton(event)) {
			JPopupMenu popup = new JPopupMenu();

			popup.add(buildAddPoint(mapPosition));
			popup.add(buildCancel());

			popup.show(event.getComponent(), event.getX(), event.getY());
		}
	}

	@Override
	public void mouseMove(MouseEvent e, Point mapPosition) {
		getMapEditableInfo().moveElementAt(mapElement, mapPosition);
	}

	private JMenuItem buildAddPoint(final Point mapPosition) {
		JMenuItem menuItem = new JMenuItem("Ajouter point");

		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPoint(mapPosition);
			}
		});

		return menuItem;
	}

	private JMenuItem buildCancel() {
		JMenuItem menuItem = new JMenuItem("Annuler d\u00E9placement");

		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelPoint();
			}
		});

		return menuItem;
	}

	private void cancelPoint() {
		mapElement.undoMove();
		getMapEditableInfo().removeDrawable(draw);

		finish();
	}

	private void addPoint(Point mapPosition) {
		draw.addPoint(mapPosition);
	}

	private void finishMove(Point mapPosition) {
		getMapEditableInfo().moveElementAt(mapElement, mapPosition);
		mapElement.applyMove();

		finish();
	}

	private void finish() {
		this.mapElement.setSelected(false);
		getMapEditableInfo().removeDrawable(draw);
		getMapEditableInfo().getPanelMap().removeMapListener(this);
		defaultState();
	}

	@Override
	public void mouseClicked(MapEvent element) {
	}

	@Override
	public void mousePressed(MapEvent element) {
	}

	@Override
	public void mouseReleased(MapEvent element) {
	}

	@Override
	public void mouseElementEntered(MapEvent element) {
	}

	@Override
	public void mouseElementExited(MapEvent element) {
	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
	}
}
