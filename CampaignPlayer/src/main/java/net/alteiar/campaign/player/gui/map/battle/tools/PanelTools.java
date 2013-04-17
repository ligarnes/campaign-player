package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.battle.tools.ToolMapListener.Tools;
import net.alteiar.campaign.player.gui.map.event.MapListener;
import net.alteiar.campaign.player.gui.map.listener.GlobalMapListener;
import net.alteiar.campaign.player.gui.map.listener.ShowHidePolygonMapListener;
import net.alteiar.campaign.player.gui.tools.test.PanelZoom;
import net.alteiar.documents.map.battle.Battle;

public class PanelTools extends JToolBar implements Observer {
	private static final long serialVersionUID = 1L;

	private static String ICON_CIRCLE = "Circle.png";

	private static String ICON_SHOW = "Eye_16.png";
	private static String ICON_HIDE = "EyeHide_16.png";

	private static String ICON_SHOW_GRID = "show-grid.jpg";
	
	private static String ICON_20_FACES_DIE = "die-20-faces.png";
	private static String ICON_12_FACES_DIE = "die-12-faces.png";
	private static String ICON_6_FACES_DIE = "die-6-faces.png";

	private final GlobalMapListener mapListener;

	private final ToolMapListener toolListener;

	private final ButtonGroup group;
	
	private ArrayList<Die> dice;

	public PanelTools(GlobalMapListener globalListener,
			final MapEditableInfo mapInfo, Battle battle) {
		this.mapListener = globalListener;

		toolListener = new ToolMapListener(mapInfo, globalListener, battle);
		toolListener.addObserver(this);

		group = new ButtonGroup();
		dice = new ArrayList<Die>();

		JToggleButton addCircle = new JToggleButton(Helpers.getIcon(
				ICON_CIRCLE, 30, 30));
		addCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool(Tools.ADD_ELEMENT);
			}
		});
		this.add(addCircle);

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

			this.add(new PanelZoom(mapInfo));

			group.add(rescale);
			group.add(showMap);
			group.add(hideMap);
		}
		this.addSeparator();
		
		Die twentyDie = new Die(20);
		JToggleButton dieTwentyFaces = new JToggleButton(Helpers.getIcon(
				ICON_20_FACES_DIE, 30, 30));
		dieTwentyFaces.addActionListener(twentyDie);
		this.dice.add(twentyDie);
		this.add(dieTwentyFaces);
		
		Die twelveDie = new Die(12);
		JToggleButton dieTwelveFaces = new JToggleButton(Helpers.getIcon(
				ICON_12_FACES_DIE, 30, 30));
		dieTwelveFaces.addActionListener(twelveDie);
		this.dice.add(twelveDie);
		this.add(dieTwelveFaces);
		
		Die sixDie = new Die(6);
		JToggleButton dieSixFaces = new JToggleButton(Helpers.getIcon(
				ICON_6_FACES_DIE, 30, 30));
		dieSixFaces.addActionListener(sixDie);
		this.dice.add(sixDie);
		this.add(dieSixFaces);
		
		JButton rollButton = new JButton("Roll");
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDice();
				showDice();
			}
		});
		this.add(rollButton);
		
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

		group.add(addCircle);
	}

	private void changeTool(Tools tool) {
		toolListener.setToolState(tool);
		mapListener.setCurrentListener(toolListener);
	}
	
	private void rollDice(){
		for (Die die : this.dice){
			if (die.isSelected()){
				die.roll();
			}
		}
	}
	
	private void showDice(){
		for (Die die : this.dice){
			if (die.isSelected()){
				// TODO : send the result to the chat. Good idea?
				System.out.println(die);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (toolListener.getToolState().equals(Tools.DO_NOTHING)) {
			group.clearSelection();
		}
	}
}
