package net.alteiar.campaign.player.gui.centerViews.map.tools;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.FixGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.RescaleAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ViewAsPlayerAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter.ChangeMapFilterAction;

public class PanelToolDm extends JToolBar {
	private static final long serialVersionUID = 1L;

	public PanelToolDm(final MapEditableInfo mapInfo) {

		if (CampaignClient.getInstance().getCurrentPlayer().isDm()) {
			JButton changeFilter = new JButton(new ChangeMapFilterAction(
					mapInfo));
			this.add(changeFilter);
			this.addSeparator();

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
