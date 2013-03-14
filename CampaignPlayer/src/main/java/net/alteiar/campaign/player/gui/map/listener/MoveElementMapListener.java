package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.server.document.map.element.MapElementClient;

public class MoveElementMapListener extends ActionMapListener {
	private final MapElementClient mapElement;
	private final MapEditableInfo mapInfo;

	public MoveElementMapListener(GlobalMapListener mapListener,
			MapEditableInfo mapInfo, Point first, MapElementClient mapElement) {
		super(mapListener);
		this.mapElement = mapElement;

		this.mapInfo = mapInfo;
		if (mapInfo.getFixGrid()) {
			mapInfo.drawPathToElement(first, mapElement);
		} else {
			mapInfo.drawLineToMouse(first);
		}
	}

	@Override
	public void mouseClicked(MapEvent event) {
		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			finishMove(event.getMapPosition());
		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			JPopupMenu popup = new JPopupMenu();

			popup.add(buildAddPoint(event.getMapPosition()));
			popup.add(buildCancel());

			popup.show(event.getMouseEvent().getComponent(), event
					.getMouseEvent().getX(), event.getMouseEvent().getY());
		}
	}

	@Override
	public void mouseMove(MouseEvent e, Point mapPosition) {
		mapInfo.moveElementAt(mapElement, mapPosition);
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
		JMenuItem menuItem = new JMenuItem("Annuler déplacement");

		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelPoint();
			}
		});

		return menuItem;
	}

	private void cancelPoint() {
		// TODO mapElement.revertPosition();
		mapListener.defaultListener();
		mapInfo.stopDrawPathToMouse();

		finish();
	}

	private void addPoint(Point mapPosition) {
		mapInfo.addPointToPath(mapPosition);
	}

	private void finishMove(Point mapPosition) {
		mapInfo.moveElementAt(mapElement, mapPosition);
		// TODO mapElement.applyPosition();
		mapListener.defaultListener();

		finish();
	}

	private void finish() {
		mapListener.defaultListener();
		mapInfo.stopDrawPathToMouse();

		if (mapInfo.getFixGrid()) {
			mapInfo.stopDrawPathToMouse();
		} else {
			mapInfo.stopDrawLineToMouse();
		}
	}
}
