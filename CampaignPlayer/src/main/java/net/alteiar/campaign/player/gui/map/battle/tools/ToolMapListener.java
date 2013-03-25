package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Observable;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.element.PanelCreateMapElement;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.campaign.player.gui.map.event.MapListener;
import net.alteiar.campaign.player.gui.map.listener.GlobalMapListener;
import net.alteiar.campaign.player.gui.map.listener.RescaleMapListener;
import net.alteiar.campaign.player.gui.map.listener.ShowHidePolygonMapListener;
import net.alteiar.map.battle.Battle;

public class ToolMapListener extends Observable implements MapListener {

	public enum Tools {
		ADD_ELEMENT, CHANGE_SCALE, SHOW, HIDE, DO_NOTHING
	};

	private final MapEditableInfo mapInfo;
	private final Battle battle;
	private final GlobalMapListener mapListener;
	private Tools toolState;

	public ToolMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener, Battle battle) {
		toolState = Tools.ADD_ELEMENT;
		this.mapListener = mapListener;
		this.mapInfo = mapInfo;
		this.battle = battle;
	}

	public void setToolState(Tools tool) {
		this.toolState = tool;
		this.setChanged();
		this.notifyObservers();
	}

	public Tools getToolState() {
		return this.toolState;
	}

	@Override
	public void mouseClicked(MapEvent event) {

		switch (toolState) {
		case ADD_ELEMENT:
			PanelCreateMapElement.createMapElement(battle, event);
			mapListener.defaultListener();
			break;
		case CHANGE_SCALE:
			mapListener.setCurrentListener(new RescaleMapListener(mapListener,
					mapInfo));
			break;
		case SHOW:
			mapListener.setCurrentListener(new ShowHidePolygonMapListener(
					mapListener, mapInfo, event.getMapPosition(), true));
			break;
		case HIDE:
			mapListener.setCurrentListener(new ShowHidePolygonMapListener(
					mapListener, mapInfo, event.getMapPosition(), false));
			break;
		case DO_NOTHING:
			break;
		}

		setToolState(Tools.DO_NOTHING);
	}

	@Override
	public void mousePressed(MapEvent event) {
	}

	@Override
	public void mouseReleased(MapEvent event) {
	}

	@Override
	public void mouseElementEntered(MapEvent event) {
	}

	@Override
	public void mouseElementExited(MapEvent event) {
	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
	}

	@Override
	public void mouseMove(MouseEvent e, Point mapPosition) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
	}
}
