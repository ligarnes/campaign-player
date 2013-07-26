package net.alteiar.campaign.player.gui.tools.selector.image;

import java.io.File;
import java.io.IOException;

import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.utils.files.images.SerializableImage;
import net.alteiar.utils.files.images.TransfertImage;

public class LocalImageSelector implements ImageSelectorStrategy {

	@Override
	public TransfertImage selectImage() {
		TransfertImage selectedImage = null;
		File imageFile = StaticDialog.getSelectedImageFile(null);

		if (imageFile != null) {
			try {
				selectedImage = new SerializableImage(imageFile);
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		}

		return selectedImage;
	}
}
