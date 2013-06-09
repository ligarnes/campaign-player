package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.event.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapAdapter;
import net.alteiar.campaign.player.gui.centerViews.map.listener.ShowHidePolygonMapListener;

public class ShowHideAreaAction extends MapAction {
	private static final long serialVersionUID = 1L;

	private static final String ICON_SHOW = "Eye_16.png";
	private static final String ICON_HIDE = "EyeHide_16.png";

	private final Boolean isShow;
	private final Point begin;

	public ShowHideAreaAction(MapEditableInfo info, Boolean isShow) {
		this(info, isShow, null);

	}

	public ShowHideAreaAction(MapEditableInfo info, Boolean isShow, Point begin) {
		super(info);

		this.begin = begin;

		this.isShow = isShow;

		String icon = ICON_HIDE;
		String text = "Cacher";
		if (isShow) {
			icon = ICON_SHOW;
			text = "Afficher";
		}

		putValue(SMALL_ICON, Helpers.getIcon(icon));
		putValue(NAME, text);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (begin == null) {
			getMapInfo().getPanelMap().addMapListener(
					new ShowHideListener(getMapInfo(), isShow));
		} else {
			getMapInfo().getMapListener()
					.setCurrentListener(
							new ShowHidePolygonMapListener(getMapInfo(), begin,
									isShow));
		}
	}

	private class ShowHideListener extends MapAdapter {
		private final MapEditableInfo info;
		private final Boolean isShow;

		public ShowHideListener(MapEditableInfo info, Boolean isShow) {
			this.info = info;
			this.isShow = isShow;
		}

		@Override
		public void mouseClicked(MapEvent event) {
			if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
				info.getMapListener().setCurrentListener(
						new ShowHidePolygonMapListener(info, event
								.getMapPosition(), isShow));
				this.info.getPanelMap().removeMapListener(this);
			}
		}
	}
}
