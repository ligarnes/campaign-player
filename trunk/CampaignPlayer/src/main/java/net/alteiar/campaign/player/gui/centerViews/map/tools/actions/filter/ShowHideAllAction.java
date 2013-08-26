package net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter;

import java.awt.event.ActionEvent;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.MapAction;
import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.map.filter.ManualMapFilter;
import net.alteiar.shared.UniqueID;

public class ShowHideAllAction extends MapAction {
	private static final long serialVersionUID = 1L;

	private static final String ICON_SHOW = "Eye_16.png";
	private static final String ICON_HIDE = "EyeHide_16.png";

	private final Boolean isShow;

	public ShowHideAllAction(MapEditableInfo info, Boolean isShow) {
		super(info);

		this.isShow = isShow;

		String icon = ICON_HIDE;
		String text = "Cacher tout";
		if (isShow) {
			icon = ICON_SHOW;
			text = "Afficher tout";
		}

		putValue(SMALL_ICON, HelpersImages.getIcon(icon));
		putValue(NAME, text);
	}

	private ManualMapFilter getMapFilter() {
		UniqueID filterId = getMapInfo().getMap().getFilter();
		return CampaignClient.getInstance().getBean(filterId);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isShow) {
			getMapFilter().showAll();
		} else {
			getMapFilter().hideAll();
		}
	}
}
