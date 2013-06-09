package net.alteiar.campaign.player.gui.centerViews.map.tools;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.AddElementAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.FixGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.RescaleAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowGridAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowHideAllAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.ShowHideAreaAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.dice.PathfinderDiceToolBar;
import net.alteiar.zoom.PanelMoveZoom;
import net.alteiar.zoom.PanelZoomEditor;

public class PanelToolsAdventure extends JToolBar {
	private static final long serialVersionUID = 1L;

	private final ButtonGroup group;

	public PanelToolsAdventure(final MapEditableInfo mapInfo,
			PanelMoveZoom<?> panelZoom) {

		group = new ButtonGroup();

		JToggleButton addElement = new JToggleButton(new AddElementAction(
				mapInfo));
		addElement.setHideActionText(true);

		group.add(addElement);
		this.add(addElement);

		// For Mj only
		if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
			JToggleButton showMap = new JToggleButton(new ShowHideAreaAction(
					mapInfo, true));
			this.add(showMap);

			JToggleButton hideMap = new JToggleButton(new ShowHideAreaAction(
					mapInfo, false));
			this.add(hideMap);

			JButton showAll = new JButton(new ShowHideAllAction(mapInfo, true));
			this.add(showAll);

			JButton hideAll = new JButton(new ShowHideAllAction(mapInfo, false));
			this.add(hideAll);

			this.addSeparator();
			this.add(new PanelZoomEditor(panelZoom));

			group.add(showMap);
			group.add(hideMap);
		}
		this.addSeparator();

		if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
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

		this.addSeparator();
		this.add(new PathfinderDiceToolBar());

	}
}
