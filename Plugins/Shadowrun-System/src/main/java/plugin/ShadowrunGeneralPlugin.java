package plugin;

import generic.bean.combat.CombatTracker;
import generic.gui.mapElement.builder.PanelCircleBuilder;
import generic.gui.mapElement.builder.PanelRectangleBuilder;
import generic.gui.mapElement.editor.PanelCircleEditor;
import generic.gui.mapElement.editor.PanelRectangleEditor;

import java.io.IOException;
import java.util.ArrayList;

import net.alteiar.beans.map.elements.CircleElement;
import net.alteiar.beans.map.elements.RectangleElement;
import net.alteiar.campaign.player.plugin.external.DocumentPlugin;
import net.alteiar.campaign.player.plugin.external.IPlugin;
import net.alteiar.campaign.player.plugin.external.MapElementPlugin;
import net.alteiar.campaign.player.plugin.external.PluginList;

import org.apache.log4j.Logger;

import shadowrun.bean.unit.ShadowrunCharacter;
import shadowrun.gui.mapElement.ShadowrunCharacterElement;
import shadowrun.gui.mapElement.builder.PanelCharacterBuilder;
import shadowrun.gui.mapElement.editor.PanelCharacterEditor;

public class ShadowrunGeneralPlugin implements IPlugin {

	private final ArrayList<MapElementPlugin> mapElementPlugins;

	private final PluginList<DocumentPlugin> documentPlugins;

	public ShadowrunGeneralPlugin() {
		documentPlugins = new PluginList<DocumentPlugin>();

		try {
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildBattleMapDocumentPlugin());
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildImageDocumentPlugin());
			documentPlugins.addPlugin(DocumentPluginFactory
					.buildCharacterDocumentPlugin());
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
				ShadowrunCharacterElement.class, null,
				new PanelCharacterBuilder(), new PanelCharacterEditor()));
	}

	@Override
	public void initialize() {

	}

	@Override
	public ArrayList<DocumentPlugin> getDocuments() {
		ArrayList<DocumentPlugin> lst = new ArrayList<DocumentPlugin>();
		try {
			lst.add(DocumentPluginFactory.buildBattleMapDocumentPlugin());
			lst.add(DocumentPluginFactory.buildImageDocumentPlugin());
			lst.add(DocumentPluginFactory.buildCharacterDocumentPlugin());
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
				ShadowrunCharacterElement.class, null,
				new PanelCharacterBuilder(), new PanelCharacterEditor()));
		return mapElementPlugins;
	}

	@Override
	public Class<?>[] getClasses() {
		return new Class[] { CircleElement.class, CombatTracker.class,
				ShadowrunCharacter.class, ShadowrunCharacter.class };
	}
}
