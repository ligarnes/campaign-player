package plugin.gui;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.IPluginSystemGui;
import net.alteiar.campaign.player.gui.factory.PanelCharacterFactory;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.documents.map.battle.Battle;
import pathfinder.gui.document.builder.PanelCreateCharacter;
import pathfinder.gui.document.builder.PanelCreateImage;
import pathfinder.gui.document.viewer.PanelViewImage;

public class PluginEffectSystemGui implements IPluginSystemGui {

	private final HashMap<Class<?>, Class<?>> viewPanels;
	private final HashMap<Class<?>, ImageIcon> documentIcons;

	public PluginEffectSystemGui() {
		viewPanels = new HashMap<Class<?>, Class<?>>();
		viewPanels.put(DocumentImageBean.class, PanelViewImage.class);

		documentIcons = new HashMap<Class<?>, ImageIcon>();
		// Setting up icons
		ImageIcon mapIcon = new ImageIcon(
				PluginSystemGui.class.getResource("/icons/map.png"));
		mapIcon.getImage()
				.getScaledInstance(32, 32, BufferedImage.SCALE_SMOOTH);
		documentIcons.put(Battle.class, mapIcon);
	}

	

	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		return new EffectMapElementFactory().getBuilders();
	}

	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		ArrayList<PanelDocumentBuilder> documentsBuilder = new ArrayList<PanelDocumentBuilder>();
		documentsBuilder
				.add(new pathfinder.gui.document.builder.PanelCreateBattle());
		documentsBuilder.add(new PanelCreateImage());
		documentsBuilder.add(new PanelCreateCharacter());
		return documentsBuilder;
	}

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

	public <E extends AuthorizationBean> ImageIcon getDocumentIcon(E bean) {
		return documentIcons.get(bean.getClass());
	}



	public PanelCharacterFactory getGuiCharacterFactory() {
		return null;
	}



	public DrawInfo getDrawInfo(MapEditableInfo mapInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
