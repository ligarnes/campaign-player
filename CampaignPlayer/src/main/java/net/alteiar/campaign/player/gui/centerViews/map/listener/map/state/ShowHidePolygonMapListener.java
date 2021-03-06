package net.alteiar.campaign.player.gui.centerViews.map.listener.map.state;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

import javax.swing.SwingUtilities;

import net.alteiar.beans.map.filter.ManualMapFilter;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse.PolygonToMouse;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.ActionMapListener;
import net.alteiar.shared.UniqueID;

public class ShowHidePolygonMapListener extends ActionMapListener {
	private final Boolean isShow;

	private final PolygonToMouse draw;

	public ShowHidePolygonMapListener(MapEditableInfo mapInfo, Point begin,
			Boolean isShow) {
		super(mapInfo);

		draw = new PolygonToMouse(mapInfo, begin);
		mapInfo.addDrawable(draw);
		this.isShow = isShow;
	}

	@Override
	public void mouseClicked(MapEvent event) {
		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			draw.addPoint(event.getMapPosition());
		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			finishShowHide();
		}
	}

	public void finishShowHide() {
		if (isShow) {
			showPolygon(draw.getPts());
		} else {
			hidePolygon(draw.getPts());
		}
		getMapInfo().getMapListener().defaultListener();
	}

	@Override
	public void startTask() {
		this.getMapInfo().getPanelMap()
				.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void endTask() {
		getMapInfo().removeDrawable(draw);
		this.getMapInfo().getPanelMap()
				.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private ManualMapFilter getMapFilter() {
		UniqueID filterId = getMapInfo().getMap().getFilter();
		return CampaignClient.getInstance().getBean(filterId);
	}

	private void showPolygon(List<Point> cwPts) {
		ManualMapFilter filter = getMapFilter();
		int[] x = new int[cwPts.size()];
		int[] y = new int[cwPts.size()];

		for (int i = 0; i < cwPts.size(); i++) {
			x[i] = cwPts.get(i).x;
			y[i] = cwPts.get(i).y;
		}

		filter.showPolygon(new Polygon(x, y, cwPts.size()));
	}

	private void hidePolygon(List<Point> cwPts) {
		ManualMapFilter filter = getMapFilter();
		int[] x = new int[cwPts.size()];
		int[] y = new int[cwPts.size()];

		for (int i = 0; i < cwPts.size(); i++) {
			x[i] = cwPts.get(i).x;
			y[i] = cwPts.get(i).y;
		}

		filter.hidePolygon(new Polygon(x, y, cwPts.size()));
	}
}
