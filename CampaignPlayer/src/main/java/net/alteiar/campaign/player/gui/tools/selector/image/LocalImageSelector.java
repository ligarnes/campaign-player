package net.alteiar.campaign.player.gui.tools.selector.image;

import java.io.File;

import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.utils.file.images.SerializableImage;
import net.alteiar.utils.file.images.TransfertImage;

import org.apache.log4j.Logger;

public class LocalImageSelector implements ImageSelectorStrategy {

	@Override
	public TransfertImage selectImage() {
		TransfertImage selectedImage = null;
		File imageFile = StaticDialog.getSelectedImageFile(null);

		if (imageFile != null) {
			try {
				selectedImage = new SerializableImage(imageFile);
			} catch (Exception e) {
				Logger.getLogger(getClass()).warn(
						"Problème pour crée une image", e);
			}
		}

		return selectedImage;
	}
}
