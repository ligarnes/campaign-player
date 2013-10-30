package net.alteiar.campaign.player.gui.centerViews.map.tools;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.dice.PathfinderDiceToolBar;
import net.alteiar.zoom.PanelMoveZoom;

public class PanelToolsAdventure extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	public PanelToolsAdventure(final MapEditableInfo mapInfo,
			PanelMoveZoom<?> panelZoom) {

		JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pane.add(new PanelToolGeneral(mapInfo, panelZoom));
		this.addTab("Général", pane);

		// For Mj only
		if (CampaignClient.getInstance().getCurrentPlayer().isDm()) {
			pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pane.add(new PanelToolDm(mapInfo));
			this.addTab("Maitre du jeu", pane);

			pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pane.add(new PanelToolFilter(mapInfo));
			this.addTab("Vision", pane);
		}

		JToolBar diceToolBar = new JToolBar();
		diceToolBar.add(new PathfinderDiceToolBar());
		pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pane.add(diceToolBar);
		this.addTab("Dés", pane);
	}
}
