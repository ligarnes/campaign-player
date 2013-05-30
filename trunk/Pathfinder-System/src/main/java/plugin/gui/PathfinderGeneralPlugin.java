package plugin.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.DynamicPanelBeanBuilder;
import net.alteiar.campaign.player.gui.factory.newPlugin.IPlugin;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.documents.map.MapBean;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.elements.RectangleElement;
import net.alteiar.shared.ExceptionTool;
import pathfinder.bean.spell.DocumentSpellBook;
import pathfinder.gui.document.builder.PanelCreateImage;
import pathfinder.gui.document.builder.character.PanelCreateCharacter;
import pathfinder.gui.document.builder.monster.PanelCreateMonster;
import pathfinder.gui.document.builder.spell.PanelCreateSpellBook;
import pathfinder.gui.document.viewer.PanelViewImage;
import pathfinder.gui.document.viewer.spell.PanelSpellBookViewer;
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

	private final DynamicPanelBeanBuilder viewPanels;
	private final DynamicPanelBeanBuilder mapElementEditor;

	private final ArrayList<PanelMapElementBuilder> mapElementBuilder;

	private final HashMap<Class<?>, ImageIconFactory<?>> documentIcons;

	public PathfinderGeneralPlugin() {
		viewPanels = new DynamicPanelBeanBuilder();
		viewPanels.add(DocumentImageBean.class, PanelViewImage.class);
		viewPanels.add(DocumentSpellBook.class, PanelSpellBookViewer.class);

		documentIcons = new HashMap<Class<?>, ImageIconFactory<?>>();

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
			addIconFactory(new SimpleImageIconFactory<MapBean>(MapBean.class,
					mapIcon));
		} catch (IOException e) {
			ExceptionTool.showError(e, "Impossible de lire l'image: "
					+ MAP_ICON);
		}
		addIconFactory(new CharacterImageIconFactory());
	}

	protected void addIconFactory(ImageIconFactory<?> factory) {
		documentIcons.put(factory.getDocumentClass(), factory);
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
		documentsBuilder.add(new PanelCreateSpellBook());
		documentsBuilder.add(new PanelCreateMonster());
		return documentsBuilder;
	}

	@Override
	public <E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(
			E bean) {
		return (PanelViewDocument<E>) viewPanels.getPanel(bean);
	}

	@Override
	public <E extends AuthorizationBean> BufferedImage getDocumentIcon(E bean) {
		ImageIconFactory<E> factory = ((ImageIconFactory<E>) documentIcons
				.get(bean.getClass()));
		if (factory != null) {
			return factory.getImage(bean);
		}
		return null;
	}

	@Override
	public <E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(
			E bean) {
		return (PanelMapElementEditor<E>) mapElementEditor.getPanel(bean);
	}

}
