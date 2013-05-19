package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.battle.PanelZoom;
import net.alteiar.campaign.player.gui.map.battle.tools.ToolMapAdventureListener.Tools;
import net.alteiar.campaign.player.gui.map.event.MapListener;
import net.alteiar.campaign.player.gui.map.listener.GlobalMapListener;
import net.alteiar.campaign.player.gui.map.listener.ShowHidePolygonMapListener;
import net.alteiar.documents.map.MapBean;
import net.alteiar.map.filter.MapFilter;

public class PanelToolsAdventure extends JToolBar implements Observer {
	private static final long serialVersionUID = 1L;

	private static String ICON_ADD_ELEMENT = "add.png";

	private static String ICON_SHOW = "Eye_16.png";
	private static String ICON_HIDE = "EyeHide_16.png";

	private static String ICON_SHOW_GRID = "show-grid.jpg";

	private final GlobalMapListener mapListener;

	private final ToolMapAdventureListener toolListener;

	private final ButtonGroup group;

	public PanelToolsAdventure(GlobalMapListener globalListener,
			final MapEditableInfo mapInfo, final MapBean battle) {
		this.mapListener = globalListener;

		toolListener = new ToolMapAdventureListener(mapInfo, globalListener,
				battle);
		toolListener.addObserver(this);

		group = new ButtonGroup();

		JToggleButton addElement = new JToggleButton(Helpers.getIcon(
				ICON_ADD_ELEMENT, 30, 30));
		addElement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.ADD_ELEMENT);
			}
		});
		group.add(addElement);
		this.add(addElement);

		// For Mj only
		if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
			this.addSeparator();
			JToggleButton rescale = new JToggleButton("rescale");
			rescale.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					changeTool(Tools.CHANGE_SCALE);
				}
			});
			this.add(rescale);

			JToggleButton showMap = new JToggleButton(Helpers.getIcon(
					ICON_SHOW, 30, 30));
			showMap.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MapListener listener = mapListener.getCurrentListener();
					if (listener instanceof ShowHidePolygonMapListener) {
						((ShowHidePolygonMapListener) listener)
								.finishShowHide();
					} else {
						changeTool(Tools.SHOW);
					}
				}
			});
			this.add(showMap);

			JToggleButton hideMap = new JToggleButton(Helpers.getIcon(
					ICON_HIDE, 30, 30));
			hideMap.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MapListener listener = mapListener.getCurrentListener();
					if (listener instanceof ShowHidePolygonMapListener) {
						((ShowHidePolygonMapListener) listener)
								.finishShowHide();
					} else {
						changeTool(Tools.HIDE);
					}
				}
			});
			this.add(hideMap);

			JButton showAll = new JButton(Helpers.getIcon(ICON_SHOW, 30, 30));
			showAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MapFilter filter = CampaignClient.getInstance().getBean(
							battle.getFilter());

					filter.showAll();
				}
			});
			this.add(showAll);

			JButton hideAll = new JButton(Helpers.getIcon(ICON_SHOW, 30, 30));
			hideAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MapFilter filter = CampaignClient.getInstance().getBean(
							battle.getFilter());

					filter.hideAll();
				}
			});
			this.add(hideAll);

			this.add(new PanelZoom(mapInfo));

			group.add(rescale);
			group.add(showMap);
			group.add(hideMap);
		}
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
		toolListener.setToolState(tool);
		mapListener.setCurrentListener(toolListener);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (toolListener.getToolState().equals(Tools.DO_NOTHING)) {
			group.clearSelection();
		}
	}
}
