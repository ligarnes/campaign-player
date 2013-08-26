package net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.ActionMapListener;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.state.ShowHidePolygonMapListener;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.MapAction;
import net.alteiar.campaign.player.infos.HelpersImages;

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

		putValue(SMALL_ICON, HelpersImages.getIcon(icon));
		putValue(NAME, text);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (begin == null) {
			getMapInfo().getMapListener().setCurrentListener(
					new ShowHideListener(getMapInfo(), isShow));
		} else {
			getMapInfo().getMapListener()
					.setCurrentListener(
							new ShowHidePolygonMapListener(getMapInfo(), begin,
									isShow));
		}
	}

	private class ShowHideListener extends ActionMapListener {
		private final Boolean isShow;

		public ShowHideListener(MapEditableInfo info, Boolean isShow) {
			super(info);
			this.isShow = isShow;
		}

		@Override
		public void mouseClicked(MapEvent event) {
			if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
				getMapInfo().getMapListener().setCurrentListener(
						new ShowHidePolygonMapListener(getMapInfo(), event
								.getMapPosition(), isShow));
			}
		}

		@Override
		public void startTask() {
			this.getMapInfo().getPanelMap()
					.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}

		@Override
		public void endTask() {
			this.getMapInfo().getPanelMap()
					.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
