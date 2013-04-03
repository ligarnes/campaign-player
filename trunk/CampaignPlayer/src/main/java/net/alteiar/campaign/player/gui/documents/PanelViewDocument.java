package net.alteiar.campaign.player.gui.documents;

import javax.swing.JPanel;

import net.alteiar.documents.AuthorizationBean;

public abstract class PanelViewDocument<E extends AuthorizationBean> extends
		JPanel {
	private static final long serialVersionUID = 1L;

	private final E documentBean;

	public PanelViewDocument(E bean) {
		documentBean = bean;
	}

	public final E getBean() {
		return documentBean;
	}
}
