package plugin;

import java.io.IOException;
import java.util.ArrayList;

import net.alteiar.campaign.player.plugin.external.DocumentPlugin;
import net.alteiar.campaign.player.plugin.external.IPlugin;
import net.alteiar.campaign.player.plugin.external.MapElementPlugin;
import net.alteiar.campaign.player.plugin.external.PluginList;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.RectangleElement;

import org.apache.log4j.Logger;

import pathfinder.bean.combat.CombatTracker;
import pathfinder.bean.spell.DocumentSpellBook;
import pathfinder.bean.spell.SpellManager;
import pathfinder.bean.unit.PathfinderCharacter;
import pathfinder.gui.mapElement.PathfinderCharacterElement;
import pathfinder.gui.mapElement.PathfinderMonsterElement;
import pathfinder.gui.mapElement.builder.PanelCharacterBuilder;
import pathfinder.gui.mapElement.builder.PanelCircleBuilder;
import pathfinder.gui.mapElement.builder.PanelMonsterBuilder;
import pathfinder.gui.mapElement.builder.PanelRectangleBuilder;
import pathfinder.gui.mapElement.editor.PanelCharacterEditor;
import pathfinder.gui.mapElement.editor.PanelCircleEditor;
import pathfinder.gui.mapElement.editor.PanelRectangleEditor;

public class PathfinderGeneralPlugin implements IPlugin {

	private final ArrayList<MapElementPlugin> mapElementPlugins;

	private final PluginList<DocumentPlugin> documentPlugins;

	public PathfinderGeneralPlugin() {
		documentPlugins = new PluginList<DocumentPlugin>();

		try {
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildBattleMapDocumentPlugin());
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildImageDocumentPlugin());
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildCharacterDocumentPlugin());
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildSpellBookDocumentPlugin());
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildNoteDocumentPlugin());

			documentPlugins.addPlugin(DocumentPluginFactory
					.buildAudioDocumentPlugin());
		} catch (IOException e1) {
			Logger.getLogger(getClass()).error(
					"impossible de charger le plugin", e1);
		}

		// mapElement builder
		mapElementPlugins = new ArrayList<MapElementPlugin>();
		mapElementPlugins.add(new MapElementPlugin(CircleElement.class, null,
				new PanelCircleBuilder(), new PanelCircleEditor()));
		mapElementPlugins.add(new MapElementPlugin(RectangleElement.class,
				null, new PanelRectangleBuilder(), new PanelRectangleEditor()));
		mapElementPlugins.add(new MapElementPlugin(
				PathfinderCharacterElement.class, null,
				new PanelCharacterBuilder(), new PanelCharacterEditor()));
		mapElementPlugins.add(new MapElementPlugin(
				PathfinderMonsterElement.class, null,
				new PanelMonsterBuilder(), null));

		// mapElement editor
		Thread tr = new Thread(new Runnable() {
			@Override
			public void run() {
				loadData();
			}
		});
		tr.start();
	}

	private void loadData() {
		// touch to load
		SpellManager.getInstance();
	}

	@Override
	public ArrayList<DocumentPlugin> getDocuments() {
		ArrayList<DocumentPlugin> lst = new ArrayList<DocumentPlugin>();
		try {
			lst.add(DocumentPluginFactory.buildBattleMapDocumentPlugin());
			lst.add(DocumentPluginFactory.buildImageDocumentPlugin());
			lst.add(DocumentPluginFactory.buildCharacterDocumentPlugin());
			lst.add(DocumentPluginFactory.buildSpellBookDocumentPlugin());
			lst.add(DocumentPluginFactory.buildNoteDocumentPlugin());
			lst.add(DocumentPluginFactory.buildAudioDocumentPlugin());
		} catch (IOException e1) {
			Logger.getLogger(getClass()).error(
					"impossible de charger le plugin", e1);
		}

		return lst;
	}

	@Override
	public ArrayList<MapElementPlugin> getMapElements() {
		// mapElement builder
		ArrayList<MapElementPlugin> mapElementPlugins = new ArrayList<MapElementPlugin>();
		mapElementPlugins.add(new MapElementPlugin(CircleElement.class, null,
				new PanelCircleBuilder(), new PanelCircleEditor()));
		mapElementPlugins.add(new MapElementPlugin(RectangleElement.class,
				null, new PanelRectangleBuilder(), new PanelRectangleEditor()));
		mapElementPlugins.add(new MapElementPlugin(
				PathfinderCharacterElement.class, null,
				new PanelCharacterBuilder(), new PanelCharacterEditor()));
		mapElementPlugins.add(new MapElementPlugin(
				PathfinderMonsterElement.class, null,
				new PanelMonsterBuilder(), null));
		return mapElementPlugins;
	}

	@Override
	public Class<?>[] getClasses() {
		return new Class[] { CircleElement.class, DocumentSpellBook.class,
				CombatTracker.class, PathfinderCharacter.class,
				PathfinderCharacter.class };
	}
}
