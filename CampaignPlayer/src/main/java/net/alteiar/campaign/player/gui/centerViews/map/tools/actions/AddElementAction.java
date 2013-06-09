package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelCreateMapElement;
import net.alteiar.campaign.player.gui.centerViews.map.event.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapAdapter;

public class AddElementAction extends MapAction {
	private static final long serialVersionUID = 1L;

	private static final String ICON_ADD_ELEMENT_NAME = "add.png";
	private static final String ICON_ADD_ELEMENT_REDUCE_NAME = "add-reduce.png";

	private final Point position;

	public AddElementAction(MapEditableInfo info) {
		this(info, null);
	}

	public AddElementAction(MapEditableInfo info, Point position) {
		super(info);

		this.position = position;

		putValue(NAME, "Ajouter un élément");
		putValue(SMALL_ICON, Helpers.getIcon(ICON_ADD_ELEMENT_REDUCE_NAME));
		putValue(LARGE_ICON_KEY, Helpers.getIcon(ICON_ADD_ELEMENT_NAME));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (position == null) {
			getMapInfo().getPanelMap().addMapListener(new AddElementListener());
		} else {
			PanelCreateMapElement.createMapElement(getMapInfo().getMap(),
					position);
		}
	}

	private class AddElementListener extends MapAdapter {
		@Override
		public void mouseClicked(MapEvent event) {
			PanelCreateMapElement
					.createMapElement(getMapInfo().getMap(), event);
			getMapInfo().getPanelMap().removeMapListener(
					AddElementListener.this);
		}
	}
}
