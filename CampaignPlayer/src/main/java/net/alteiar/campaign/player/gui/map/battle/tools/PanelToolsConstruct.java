package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.battle.tools.ToolMapAdventureListener.Tools;
import net.alteiar.campaign.player.gui.map.listener.GlobalMapListener;
import net.alteiar.campaign.player.gui.tools.test.PanelZoom;
import net.alteiar.documents.map.battle.Battle;

public class PanelToolsConstruct extends JToolBar {
	private static final long serialVersionUID = 1L;

	private static String ICON_CONSTRUCT = "travaux.png";
	private static String ICON_SHOW_GRID = "show-grid.jpg";

	private final GlobalMapListener mapListener;

	private final ToolMapAdventureListener toolListener;

	public PanelToolsConstruct(GlobalMapListener globalListener,
			final MapEditableInfo mapInfo, Battle battle) {
		this.mapListener = globalListener;

		toolListener = new ToolMapAdventureListener(mapInfo, globalListener, battle);

		JToggleButton addConstuct = new JToggleButton(Helpers.getIcon(
				ICON_CONSTRUCT, 30, 30));
		addConstuct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.ADD_ELEMENT);
			}
		});
		this.add(addConstuct);

		// For Mj only
		this.addSeparator();
		JToggleButton rescale = new JToggleButton("rescale");
		rescale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.CHANGE_SCALE);
			}
		});
		this.add(rescale);

		this.add(new PanelZoom(mapInfo));

		this.addSeparator();

		this.add(new DiceToolBar());

		this.addSeparator();

		JToggleButton showGrid = new JToggleButton(Helpers.getIcon(
				ICON_SHOW_GRID, 30, 30));
		showGrid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapInfo.showGrid(!mapInfo.getShowGrid());
			}
		});
		this.add(showGrid);

		JToggleButton fixGrid = new JToggleButton("fix grid");
		fixGrid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapInfo.fixGrid(!mapInfo.getFixGrid());
			}
		});
		if (mapInfo.getFixGrid()) {
			fixGrid.setSelected(true);
		}
		if (mapInfo.getShowGrid()) {
			showGrid.setSelected(true);
		}
		this.add(fixGrid);
	}

	private void changeTool(Tools tool) {
		mapListener.setCurrentListener(toolListener);
	}
}
