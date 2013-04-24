package plugin.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.IPluginSystemGui;
import net.alteiar.campaign.player.gui.factory.PanelCharacterFactory;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.map.elements.MapElement;
import pathfinder.gui.document.builder.PanelCreateCharacter;
import pathfinder.gui.document.builder.PanelCreateImage;
import pathfinder.gui.document.viewer.PanelViewImage;
import pathfinder.gui.mapElement.PathfinderCharacterElement;
import pathfinder.gui.mapElement.editor.PanelCharacterEditor;
import pathfinder.map.state.PathfinderDrawInfo;
import plugin.gui.imageIcon.CharacterImageIconFactory;
import plugin.gui.imageIcon.ImageIconFactory;
import plugin.gui.imageIcon.SimpleImageIconFactory;

public class PluginSystemGui implements IPluginSystemGui {

	private final HashMap<Class<?>, Class<?>> viewPanels;
	private final HashMap<Class<?>, ImageIconFactory<?>> documentIcons;
	private final HashMap<Class<?>, Class<? extends PanelMapElementEditor<?>>> mapElementEditor;

	public PluginSystemGui() {
		viewPanels = new HashMap<Class<?>, Class<?>>();
		viewPanels.put(DocumentImageBean.class, PanelViewImage.class);

		documentIcons = new HashMap<Class<?>, ImageIconFactory<?>>();

		mapElementEditor = new HashMap<Class<?>, Class<? extends PanelMapElementEditor<?>>>();
		mapElementEditor.put(PathfinderCharacterElement.class,
				PanelCharacterEditor.class);
		// Setting up icons
		try {
			BufferedImage mapIcon = ImageIO.read(PluginSystemGui.class
					.getResource("/icons/map.png"));
			addIconFactory(new SimpleImageIconFactory<Battle>(Battle.class,
					mapIcon));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addIconFactory(new CharacterImageIconFactory());
	}

	protected void addIconFactory(ImageIconFactory<?> factory) {
		documentIcons.put(factory.getDocumentClass(), factory);
	}

	@Override
	public DrawInfo getDrawInfo(MapEditableInfo mapInfo) {
		return new PathfinderDrawInfo(mapInfo);
	}

	@Override
	public PanelCharacterFactory getGuiCharacterFactory() {
		return new PathfinderPanelCharacterFactory();
	}

	@Override
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		return new PathfinderMapElementFactory().getBuilders();
	}

	@Override
	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		ArrayList<PanelDocumentBuilder> documentsBuilder = new ArrayList<PanelDocumentBuilder>();
		documentsBuilder
				.add(new pathfinder.gui.document.builder.PanelCreateBattle());
		documentsBuilder.add(new PanelCreateImage());
		documentsBuilder.add(new PanelCreateCharacter());
		return documentsBuilder;
	}

	@Override
	public <E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(
			E bean) {
		Class<PanelViewDocument<E>> classes = (Class<PanelViewDocument<E>>) viewPanels
				.get(bean.getClass());
		if (classes != null) {
			try {
				return classes.getConstructor(bean.getClass())
						.newInstance(bean);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public <E extends AuthorizationBean> BufferedImage getDocumentIcon(E bean) {
		return ((ImageIconFactory<E>) documentIcons.get(bean.getClass()))
				.getImage(bean);
	}

	@Override
	public <E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(
			E bean) {
		Class<PanelMapElementEditor<E>> classes = (Class<PanelMapElementEditor<E>>) mapElementEditor
				.get(bean.getClass());
		if (classes != null) {
			try {
				return classes.getConstructor(bean.getClass())
						.newInstance(bean);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
