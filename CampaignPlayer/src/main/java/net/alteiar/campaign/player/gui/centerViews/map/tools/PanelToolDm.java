package net.alteiar.campaign.player.gui.centerViews.map.tools;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.FixGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.RescaleAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowHideAllAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowHideAreaAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ViewAsPlayerAction;

public class PanelToolDm extends JToolBar {
	private static final long serialVersionUID = 1L;

	public PanelToolDm(final MapEditableInfo mapInfo) {
		JButton showMap = new JButton(new ShowHideAreaAction(mapInfo, true));
		this.add(showMap);

		JButton hideMap = new JButton(new ShowHideAreaAction(mapInfo, false));
		this.add(hideMap);

		JButton showAll = new JButton(new ShowHideAllAction(mapInfo, true));
		this.add(showAll);

		JButton hideAll = new JButton(new ShowHideAllAction(mapInfo, false));
		this.add(hideAll);

		this.addSeparator();

		if (CampaignClient.getInstance().getCurrentPlayer().isDm()) {
			JButton rescale = new JButton(new RescaleAction(mapInfo));
			this.add(rescale);
		}
		JToggleButton showGrid = new JToggleButton(new ShowGridAction(mapInfo));
		this.add(showGrid);

		JToggleButton fixGrid = new JToggleButton(new FixGridAction(mapInfo));
		if (mapInfo.getFixGrid()) {
			fixGrid.setSelected(true);
		}
		if (mapInfo.getShowGrid()) {
			showGrid.setSelected(true);
		}
		this.add(fixGrid);

		this.add(new ViewAsPlayerAction(mapInfo));
	}
}
