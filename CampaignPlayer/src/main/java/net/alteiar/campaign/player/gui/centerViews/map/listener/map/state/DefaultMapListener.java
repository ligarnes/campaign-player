package net.alteiar.campaign.player.gui.centerViews.map.listener.map.state;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.ActionMapListener;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.AddElementAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.FixGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowHideAreaAction;

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
				popup.add(new JMenuItem(new ShowHideAreaAction(getMapInfo(),
						true)));
				popup.add(new JMenuItem(new ShowHideAreaAction(getMapInfo(),
						false)));
			}

			popup.addSeparator();
			popup.add(buildShowGrid());
			popup.add(buildFixGrid());

			popup.show(event.getMouseEvent().getComponent(), event
					.getMouseEvent().getX(), event.getMouseEvent().getY());
		}
	}

	private JMenuItem buildShowGrid() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				new ShowGridAction(getMapInfo()));
		menuItem.setSelected(getMapInfo().getShowGrid());
		return menuItem;
	}

	private JMenuItem buildFixGrid() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				new FixGridAction(getMapInfo()));
		menuItem.setSelected(getMapInfo().getFixGrid());
		return menuItem;
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
