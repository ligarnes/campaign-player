package net.alteiar.campaign.player.plugin.external;

import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.plugin.imageIcon.ImageIconFactory;

public class MapElementPlugin implements IPluginElement {

	private final Class<?> mapElement;

	private final ImageIconFactory icon;
	private final PanelMapElementBuilder builder;
	private final PanelMapElementEditor editor;

	public MapElementPlugin(Class<?> documentType, ImageIconFactory icon,
			PanelMapElementBuilder builder, PanelMapElementEditor editor) {
		super();
		this.mapElement = documentType;
		this.icon = icon;
		this.builder = builder;
		this.editor = editor;
	}

	@Override
	public String getType() {
		return mapElement.getCanonicalName();
	}

	public ImageIconFactory getIcon() {
		return icon;
	}

	public PanelMapElementBuilder getBuilder() {
		return builder;
	}

	public PanelMapElementEditor getEditor() {
		return editor;
	}

}
