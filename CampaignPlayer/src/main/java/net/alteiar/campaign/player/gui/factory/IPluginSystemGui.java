package net.alteiar.campaign.player.gui.factory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.newPlugin.ICorePlugin;
import net.alteiar.documents.BeanDocument;
import net.alteiar.map.MapBean;
import net.alteiar.map.elements.MapElement;

public interface IPluginSystemGui extends ICorePlugin {
	ArrayList<PanelMapElementBuilder> getGuiMapElementFactory(MapBean map);

	ArrayList<PanelDocumentBuilder> getGuiDocumentFactory();

	PanelViewDocument getViewPanel(BeanDocument bean);

	BufferedImage getDocumentIcon(BeanDocument bean);

	<E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(E bean);
}
