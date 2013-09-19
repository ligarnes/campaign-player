package net.alteiar.campaign.player.gui.centerViews.map.listener.map.state;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import net.alteiar.beans.map.filter.ManualMapFilter;
import net.alteiar.beans.map.filter.MapFilter;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.ActionMapListener;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.AddElementAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter.ShowHideAreaAction;

public class DefaultMapListener extends ActionMapListener {

	public DefaultMapListener(MapEditableInfo mapInfo) {
		super(mapInfo);
	}

	@Override
	public void mouseClicked(MapEvent event) {
		if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			JPopupMenu popup = new JPopupMenu();

			popup.add(new JMenuItem(new AddElementAction(getMapInfo(), event
					.getFixToGridPosition())));

			if (CampaignClient.getInstance().getCurrentPlayer().isDm()) {
				popup.addSeparator();

				// depend on filter
				MapFilter filter = CampaignClient.getInstance().getBean(
						getMapInfo().getMap().getFilter());
				if (filter instanceof ManualMapFilter) {
					popup.add(new JMenuItem(new ShowHideAreaAction(
							getMapInfo(), true)));
					popup.add(new JMenuItem(new ShowHideAreaAction(
							getMapInfo(), false)));
				}
			}

			popup.show(event.getMouseEvent().getComponent(), event
					.getMouseEvent().getX(), event.getMouseEvent().getY());
			popup.setLocation(event.getMouseEvent().getXOnScreen(), event
					.getMouseEvent().getYOnScreen());
		}
	}

	@Override
	public void startTask() {
		// Do nothing
	}

	@Override
	public void endTask() {
		// Do nothing
	}

}
