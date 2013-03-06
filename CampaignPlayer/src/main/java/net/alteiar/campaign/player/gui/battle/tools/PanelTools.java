package net.alteiar.campaign.player.gui.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.battle.plan.MapEditableInfo;
import net.alteiar.campaign.player.gui.battle.plan.details.MapListener;
import net.alteiar.campaign.player.gui.battle.plan.listener.GlobalMapListener;
import net.alteiar.campaign.player.gui.battle.plan.listener.ShowHidePolygonMapListener;
import net.alteiar.campaign.player.gui.battle.tools.ToolMapListener.Tools;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.map.battle.BattleClient;

public class PanelTools extends JToolBar implements Observer {
	private static final long serialVersionUID = 1L;

	private static String ICON_CHARACTER = "AjouterPersonnage.png";

	private static String ICON_CIRCLE = "Circle.png";
	private static String ICON_CONE = "Cone.png";

	private static String ICON_SHOW = "Eye_16.png";
	private static String ICON_HIDE = "EyeHide_16.png";

	private static String ICON_SHOW_GRID = "show-grid.jpg";

	private final GlobalMapListener mapListener;

	private final ToolMapListener toolListener;

	private final ButtonGroup group;

	public PanelTools(GlobalMapListener globalListener,
			final MapEditableInfo mapInfo, BattleClient battle) {
		this.mapListener = globalListener;

		toolListener = new ToolMapListener(mapInfo, globalListener, battle);
		toolListener.addObserver(this);

		group = new ButtonGroup();

		JToggleButton addCharacter = new JToggleButton(Helpers.getIcon(
				ICON_CHARACTER, 30, 30));
		addCharacter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.ADD_CHARACTER);
			}
		});
		this.add(addCharacter);

		if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
			JToggleButton addMonster = new JToggleButton("add monster");
			addMonster.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					changeTool(Tools.ADD_MONSTER);
				}
			});
			group.add(addMonster);

			this.add(addMonster);
		}
		this.addSeparator();
		//
		JToggleButton addCircle = new JToggleButton(Helpers.getIcon(
				ICON_CIRCLE, 30, 30));
		addCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.ADD_CIRCLE);
			}
		});
		this.add(addCircle);
		JToggleButton addCone = new JToggleButton(Helpers.getIcon(ICON_CONE,
				30, 30));
		addCone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.ADD_CONE);
			}
		});
		this.add(addCone);
		JToggleButton addRay = new JToggleButton("add ray");
		addRay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.ADD_RAY);
			}
		});
		this.add(addRay);

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

			group.add(rescale);
			group.add(showMap);
			group.add(hideMap);
		}
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

		group.add(addCharacter);
		group.add(addRay);
		group.add(addCone);
		group.add(addCircle);
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