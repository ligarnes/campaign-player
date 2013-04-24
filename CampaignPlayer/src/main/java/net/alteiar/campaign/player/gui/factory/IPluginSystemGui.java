package net.alteiar.campaign.player.gui.factory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.map.elements.MapElement;

public interface IPluginSystemGui {

	PanelCharacterFactory getGuiCharacterFactory();

	ArrayList<PanelMapElementBuilder> getGuiMapElementFactory();

	ArrayList<PanelDocumentBuilder> getGuiDocumentFactory();

	<E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(E bean);

	<E extends AuthorizationBean> BufferedImage getDocumentIcon(E bean);

	DrawInfo getDrawInfo(MapEditableInfo mapInfo);

	<E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(E bean);
}
