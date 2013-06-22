package plugin.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.DynamicPanelBeanBuilder;
import net.alteiar.campaign.player.gui.factory.newPlugin.IPlugin;
import net.alteiar.documents.BeanDocument;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.elements.RectangleElement;
import pathfinder.bean.spell.SpellManager;
import pathfinder.gui.mapElement.PathfinderCharacterElement;
import pathfinder.gui.mapElement.builder.PanelCharacterBuilder;
import pathfinder.gui.mapElement.builder.PanelCircleBuilder;
import pathfinder.gui.mapElement.builder.PanelMonsterBuilder;
import pathfinder.gui.mapElement.builder.PanelRectangleBuilder;
import pathfinder.gui.mapElement.editor.PanelCharacterEditor;
import pathfinder.gui.mapElement.editor.PanelCircleEditor;
import pathfinder.gui.mapElement.editor.PanelRectangleEditor;
import plugin.gui.imageIcon.ImageIconFactory;

public class PathfinderGeneralPlugin implements IPlugin {

	private final DynamicPanelBeanBuilder mapElementEditor;
	private final ArrayList<PanelMapElementBuilder> mapElementBuilder;

	private final PluginList plugins;

	public PathfinderGeneralPlugin() {
		plugins = new PluginList();

		try {
			plugins.addPlugin(DocumentPluginFactory
					.buildBattleMapDocumentPlugin());
			plugins.addPlugin(DocumentPluginFactory.buildImageDocumentPlugin());
			plugins.addPlugin(DocumentPluginFactory
					.buildCharacterDocumentPlugin());
			plugins.addPlugin(DocumentPluginFactory
					.buildSpellBookDocumentPlugin());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// mapElement builder
		mapElementBuilder = new ArrayList<PanelMapElementBuilder>();
		mapElementBuilder.add(new PanelCircleBuilder());
		mapElementBuilder.add(new PanelRectangleBuilder());
		mapElementBuilder.add(new PanelCharacterBuilder());
		mapElementBuilder.add(new PanelMonsterBuilder());

		// mapElement editor
		mapElementEditor = new DynamicPanelBeanBuilder();
		mapElementEditor.add(PathfinderCharacterElement.class,
				PanelCharacterEditor.class);
		mapElementEditor.add(CircleElement.class, PanelCircleEditor.class);
		mapElementEditor
				.add(RectangleElement.class, PanelRectangleEditor.class);

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
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		return mapElementBuilder;
	}

	@Override
	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		ArrayList<PanelDocumentBuilder> documentsBuilder = new ArrayList<PanelDocumentBuilder>();

		for (DocumentPlugin plugin : plugins.getPlugins()) {
			documentsBuilder.add(plugin.getBuilder());
		}

		return documentsBuilder;
	}

	@Override
	public PanelViewDocument getViewPanel(BeanDocument doc) {
		PanelViewDocument view = plugins.getPlugin(doc.getDocumentType())
				.getViewer();
		if (view != null) {
			view.setDocument(doc);
		}
		return view;
	}

	@Override
	public BufferedImage getDocumentIcon(BeanDocument bean) {
		ImageIconFactory factory = plugins.getPlugin(bean.getDocumentType())
				.getIcon();

		return factory.getImage(bean);
	}

	@Override
	public <E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(
			E bean) {
		return (PanelMapElementEditor<E>) mapElementEditor.getPanel(bean);
	}

}
