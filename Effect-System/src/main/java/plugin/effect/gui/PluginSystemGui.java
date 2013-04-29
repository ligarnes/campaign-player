package plugin.effect.gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.DynamicPanelBeanBuilder;
import net.alteiar.campaign.player.gui.factory.IPluginSystemGui;
import net.alteiar.campaign.player.gui.factory.PanelCharacterFactory;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.gui.effect.EffectEditor;
import net.alteiar.effectBean.gui.effect.trigger.TriggerEditor;
import net.alteiar.map.elements.MapElement;
import net.alteiar.trigger.TriggerBean;

public class PluginSystemGui implements IPluginSystemGui {

	private final DynamicPanelBeanBuilder mapElementEditor;

	public PluginSystemGui() {
		mapElementEditor = new DynamicPanelBeanBuilder();
		mapElementEditor.add(Effect.class, EffectEditor.class);
		mapElementEditor.add(TriggerBean.class, TriggerEditor.class);
	}

	public <E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(
			E bean) {
		return (PanelMapElementEditor<E>) mapElementEditor.getPanel(bean);
	}

	public PanelCharacterFactory getGuiCharacterFactory() {
		return null;
	}

	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		return new EffectMapElementFactory().getBuilders();
	}

	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		return null;
	}

	public <E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(
			E bean) {
		return null;
	}

	public <E extends AuthorizationBean> BufferedImage getDocumentIcon(E bean) {
		return null;
	}

	public DrawInfo getDrawInfo(MapEditableInfo mapInfo) {
		return null;
	}
}
