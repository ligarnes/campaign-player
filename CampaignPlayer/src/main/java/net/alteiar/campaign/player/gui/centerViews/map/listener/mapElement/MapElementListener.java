package net.alteiar.campaign.player.gui.centerViews.map.listener.mapElement;

import java.awt.event.MouseEvent;

import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.Drawable;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.Drawable.DrawableMouseListener;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.MapElementDrawable;

public class MapElementListener implements DrawableMouseListener {

	private MapElementListenerState state;

	public MapElementListener(MapEditableInfo info) {
		state = new DefaultState(info, this);
	}

	public void setState(MapElementListenerState state) {
		this.state = state;
	}

	@Override
	public void mouseClicked(MouseEvent e, Drawable draw) {
		MapElement element = ((MapElementDrawable) draw).getMapElement();
		state.mouseClicked(e, element);
	}
}
