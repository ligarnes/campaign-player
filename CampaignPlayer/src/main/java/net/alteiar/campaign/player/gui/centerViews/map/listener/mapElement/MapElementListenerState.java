package net.alteiar.campaign.player.gui.centerViews.map.listener.mapElement;

import java.awt.event.MouseEvent;

import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public abstract class MapElementListenerState {
	private final MapEditableInfo info;
	private final MapElementListener listener;

	public MapElementListenerState(MapEditableInfo info,
			MapElementListener listener) {
		this.info = info;
		this.listener = listener;
	}

	protected MapEditableInfo getMapEditableInfo() {
		return info;
	}

	protected MapElementListener getListener() {
		return listener;
	}

	public void setState(MapElementListenerState state) {
		listener.setState(state);
	}

	public void defaultState() {
		listener.setState(new DefaultState(info, listener));
	}

	public abstract void mouseClicked(MouseEvent event, MapElement element);
}
