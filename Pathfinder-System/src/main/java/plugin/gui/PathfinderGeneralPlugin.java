package plugin.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.DynamicPanelBeanBuilder;
import net.alteiar.campaign.player.gui.factory.newPlugin.IPlugin;
import net.alteiar.documents.BeanDocument;
import net.alteiar.documents.DocumentType;
import net.alteiar.map.MapBean;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.elements.RectangleElement;
import net.alteiar.shared.ExceptionTool;
import pathfinder.gui.document.builder.PanelCreateImage;
import pathfinder.gui.document.builder.character.PanelCreateCharacter;
import pathfinder.gui.document.builder.monster.PanelCreateMonster;
import pathfinder.gui.document.viewer.PanelViewImage;
import pathfinder.gui.mapElement.PathfinderCharacterElement;
import pathfinder.gui.mapElement.builder.PanelCharacterBuilder;
import pathfinder.gui.mapElement.builder.PanelCircleBuilder;
import pathfinder.gui.mapElement.builder.PanelMonsterBuilder;
import pathfinder.gui.mapElement.builder.PanelRectangleBuilder;
import pathfinder.gui.mapElement.editor.PanelCharacterEditor;
import pathfinder.gui.mapElement.editor.PanelCircleEditor;
import pathfinder.gui.mapElement.editor.PanelRectangleEditor;
import plugin.gui.imageIcon.CharacterImageIconFactory;
import plugin.gui.imageIcon.ImageIconFactory;
import plugin.gui.imageIcon.SimpleImageIconFactory;

public class PathfinderGeneralPlugin implements IPlugin {

	private static String MAP_ICON = "/icons/map.png";

	private final DynamicPanelBeanBuilder mapElementEditor;

	private final ArrayList<PanelMapElementBuilder> mapElementBuilder;

	private final HashMap<DocumentType, PanelViewDocument> views;
	private final HashMap<DocumentType, ImageIconFactory<?>> documentIcon;

	public PathfinderGeneralPlugin() {
		views = new HashMap<>();

		views.put(DocumentType.IMAGE, new PanelViewImage());
		// viewPanels.add(DocumentSpellBook.class, PanelSpellBookViewer.class);

		documentIcon = new HashMap<>();

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

		// Setting up icons
		try {
			BufferedImage mapIcon = ImageIO.read(PathfinderGeneralPlugin.class
					.getResource(MAP_ICON));
			documentIcon.put(DocumentType.BATTLE_MAP,
					new SimpleImageIconFactory<>(MapBean.class, mapIcon));
		} catch (IOException e) {
			ExceptionTool.showError(e, "Impossible de lire l'image: "
					+ MAP_ICON);
		}
		documentIcon.put(DocumentType.CHARACTER,
				new CharacterImageIconFactory());
	}

	@Override
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		return mapElementBuilder;
	}

	@Override
	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		ArrayList<PanelDocumentBuilder> documentsBuilder = new ArrayList<PanelDocumentBuilder>();
		documentsBuilder
				.add(new pathfinder.gui.document.builder.PanelCreateBattle());
		documentsBuilder.add(new PanelCreateImage());
		documentsBuilder.add(new PanelCreateCharacter());
		// documentsBuilder.add(new PanelCreateSpellBook());
		documentsBuilder.add(new PanelCreateMonster());
		return documentsBuilder;
	}

	@Override
	public PanelViewDocument getViewPanel(BeanDocument bean) {
		BeanDocument doc = bean;
		PanelViewDocument view = views.get(doc.getDocumentType());
		if (view != null) {
			view.setDocument(doc);
		}
		return view;
	}

	@Override
	public BufferedImage getDocumentIcon(BeanDocument bean) {
		ImageIconFactory factory = documentIcon.get(bean.getDocumentType());
		if (factory != null) {
			return factory.getImage(bean.getBean());
		}
		return null;
	}

	@Override
	public <E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(
			E bean) {
		return (PanelMapElementEditor<E>) mapElementEditor.getPanel(bean);
	}

}
