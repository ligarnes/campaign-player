package net.alteiar.campaign.player.plugin.external;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.plugin.imageIcon.ImageIconFactory;

public class DocumentPlugin implements IPluginElement {

	private final String documentType;

	private final ImageIconFactory icon;
	private final PanelDocumentBuilder builder;
	private final PanelViewDocument viewer;

	public DocumentPlugin(String documentType, ImageIconFactory icon,
			PanelDocumentBuilder builder, PanelViewDocument viewer) {
		super();
		this.documentType = documentType;
		this.icon = icon;
		this.builder = builder;
		this.viewer = viewer;
	}

	public ImageIconFactory getIcon() {
		return icon;
	}

	public PanelDocumentBuilder getBuilder() {
		return builder;
	}

	public PanelViewDocument getViewer() {
		return viewer;
	}

	@Override
	public String getType() {
		return documentType;
	}
}
