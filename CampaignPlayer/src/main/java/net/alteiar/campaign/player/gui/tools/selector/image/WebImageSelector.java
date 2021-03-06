package net.alteiar.campaign.player.gui.tools.selector.image;

import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.UrlOkCancel;
import net.alteiar.utils.file.images.TransfertImage;
import net.alteiar.utils.file.images.WebImage;

public class WebImageSelector implements ImageSelectorStrategy {

	@Override
	public TransfertImage selectImage() {
		TransfertImage selectedImage = null;
		DialogOkCancel<UrlOkCancel> dialogUrl = new DialogOkCancel<UrlOkCancel>(
				null, "Choisir une image sur internet", true, new UrlOkCancel());

		dialogUrl.setOkText("Choisir");
		dialogUrl.setCancelText("Annuler");
		dialogUrl.setLocationRelativeTo(null);
		dialogUrl.pack();
		dialogUrl.setVisible(true);

		if (dialogUrl.getReturnStatus() == DialogOkCancel.RET_OK) {
			selectedImage = new WebImage(dialogUrl.getMainPanel().getUrl());
		}

		return selectedImage;
	}
}
