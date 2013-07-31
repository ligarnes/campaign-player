package net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.MapAction;
import net.alteiar.map.filter.CharacterMapFilter;
import net.alteiar.media.ImageBean;
import net.alteiar.utils.files.images.SerializableImage;

import org.apache.log4j.Logger;

public class ChooseFilterAction extends MapAction {
	private static final long serialVersionUID = 1L;

	public ChooseFilterAction(MapEditableInfo info) {
		super(info);

		// putValue(SMALL_ICON, Helpers.getIcon(icon));
		putValue(NAME, "Changer l'image filtre");
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		final CharacterMapFilter filter = CampaignClient.getInstance().getBean(
				getMapInfo().getMap().getFilter());

		File imgFile = StaticDialog.getSelectedImageFile((Component) action
				.getSource());

		if (imgFile != null) {
			try {
				ImageBean bean = new ImageBean(new SerializableImage(imgFile));
				CampaignClient.getInstance().addBean(bean);
				// BufferedImage img = ImageIO.read(imgFile);
				filter.setFilteredImageId(bean.getId());
			} catch (IOException e) {
				Logger.getLogger(getClass()).error(
						"Impossible de lire l'image filtre", e);
			} catch (IllegalArgumentException e) {
				Logger.getLogger(getClass()).warn(
						"L'image n'est pas de la même dimension que la carte",
						e);
			}
		}
	}

}
