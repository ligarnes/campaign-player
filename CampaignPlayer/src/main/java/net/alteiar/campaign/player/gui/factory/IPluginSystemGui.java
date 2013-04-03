package net.alteiar.campaign.player.gui.factory;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.documents.AuthorizationBean;

public interface IPluginSystemGui {

	PanelCharacterFactory getGuiCharacterFactory();

	ArrayList<PanelMapElementBuilder> getGuiMapElementFactory();

	ArrayList<PanelDocumentBuilder> getGuiDocumentFactory();

	<E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(E bean);

	<E extends AuthorizationBean> ImageIcon getDocumentIcon(E bean);

}
