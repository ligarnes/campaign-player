package net.alteiar.campaign.player.gui.factory.newPlugin;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.map.elements.MapElement;

public interface IPlugin {
	ArrayList<PanelMapElementBuilder> getGuiMapElementFactory();

	ArrayList<PanelDocumentBuilder> getGuiDocumentFactory();

	<E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(E bean);

	<E extends AuthorizationBean> BufferedImage getDocumentIcon(E bean);

	<E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(E bean);
}
