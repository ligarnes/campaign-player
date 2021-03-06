package net.alteiar.campaign.player.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

import net.alteiar.campaign.player.gui.centerViews.ApplicationView;
import net.alteiar.campaign.player.gui.centerViews.PanelCenter;
import net.alteiar.campaign.player.gui.centerViews.PanelDashboard;
import net.alteiar.campaign.player.gui.centerViews.TabbedPaneListAllBattle;
import net.alteiar.campaign.player.gui.centerViews.settings.PanelSettings;
import net.alteiar.campaign.player.gui.sideView.PanelWest;
import net.alteiar.campaign.player.gui.sideView.SideView;
import net.alteiar.campaign.player.gui.sideView.chat.PanelChatFactory;
import net.alteiar.campaign.player.gui.sideView.combatTraker.PanelCombatTraker;
import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.campaign.player.plugin.PluginSystem;

public class UiManager {

	private static final UiManager INSTANCE = new UiManager();

	public static UiManager getInstance() {
		return INSTANCE;
	}

	private final PanelCenter panelCenter;
	private final PanelWest panelWest;

	private ApplicationView currentView;

	private final TreeMap<String, SideView> sideViews;
	private final TreeMap<String, ApplicationView> centerViews;

	public UiManager() {
		sideViews = new TreeMap<String, SideView>();
		centerViews = new TreeMap<String, ApplicationView>();

		initializeViews();

		panelCenter = new PanelCenter();
		panelWest = new PanelWest(getViews());

		selectView("Accueil");
	}

	public PanelCenter getCenterPanel() {
		return panelCenter;
	}

	public PanelWest getWestPanel() {
		return panelWest;
	}

	private void initializeViews() {
		// Side elements
		SideView chat = new SideView("Chat", PanelChatFactory.buildChatSmall());

		SideView characters = new SideView("Personnages", PluginSystem
				.getInstance().buildSmallCharacterSheet());

		SideView combatTracker = new SideView("Combat", new PanelCombatTraker(
				200));

		addSideView(characters);
		addSideView(chat);
		addSideView(combatTracker);

		// Applications views
		ApplicationView dashBoard = new ApplicationView("Accueil",
				new PanelDashboard(), null);
		dashBoard.addSideView(chat);

		ApplicationView battle = new ApplicationView("Cartes",
				new TabbedPaneListAllBattle(), null);
		battle.addSideView(chat);
		battle.addSideView(characters);
		battle.addSideView(combatTracker);

		ApplicationView settings = new ApplicationView("Paramètres",
				new PanelSettings(), HelpersImages.getIcon("params.png"));
		settings.addSideView(chat);

		addView(dashBoard);
		addView(battle);
		addView(settings);
	}

	private void addView(ApplicationView view) {
		centerViews.put(view.getName(), view);
	}

	private void addSideView(SideView sideElement) {
		sideViews.put(sideElement.getName(), sideElement);
	}

	public Collection<ApplicationView> getViews() {
		return Collections.unmodifiableCollection(centerViews.values());
	}

	public ApplicationView getApplicationView(String name) {
		return centerViews.get(name);
	}

	public SideView getSideView(String name) {
		return sideViews.get(name);
	}

	public ApplicationView getSelectedView() {
		return currentView;
	}

	public void selectView(String view) {
		currentView = centerViews.get(view);

		panelCenter.updateSelectedView(currentView);
		panelWest.updateSelectedView(currentView);
	}
}
