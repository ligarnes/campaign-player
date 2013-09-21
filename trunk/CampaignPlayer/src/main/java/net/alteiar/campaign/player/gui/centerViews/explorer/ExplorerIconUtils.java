package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDocument;

public class ExplorerIconUtils {
	public static String ICON_DIRECTORY_CLOSE = "gnome_fs_directory.png";
	public static String ICON_DIRECTORY_OPEN = "gnome_fs_directory_accept.png";
	public static String ICON_DOCUMENT_DEFAULT = "text_css.png";

	public static String ICON_DOCUMENT_PUBLIC = "lock_open_32.png";
	public static String ICON_DOCUMENT_PRIVATE = "lock_closed_32.png";

	public static Icon getIcon(BeanBasicDocument doc, int width, int height) {
		Icon icon = null;

		if (doc.isDirectory()) {
			icon = getDirClosedIcon(width, height);
		} else {
			icon = getFileIcon((BeanDocument) doc, width, height);
		}

		return icon;
	}

	public static Icon getFileIcon(BeanDocument doc, int width, int height) {
		BufferedImage img = PluginSystem.getInstance().getDocumentIcon(doc);

		Icon icon = null;
		if (img == null) {
			icon = HelpersImages.getIcon(ICON_DOCUMENT_DEFAULT, width, height);
		} else {
			img = net.alteiar.shared.ImageUtil.resizeImage(img, width, height,
					net.alteiar.shared.ImageUtil.NORMAL_RESOLUTION);
			icon = new ImageIcon(img);
		}
		return icon;
	}

	public static Icon getDirClosedIcon(int width, int height) {
		return HelpersImages.getIcon(ICON_DIRECTORY_CLOSE, width, height);
	}

	public static Icon getDirOpenIcon(int width, int height) {
		return HelpersImages.getIcon(ICON_DIRECTORY_OPEN, width, height);
	}

	public static Icon getLockIcon(BeanBasicDocument doc) {
		Icon icon = null;
		if (doc.getPublic()) {
			icon = HelpersImages.getIcon(ICON_DOCUMENT_PUBLIC);
		} else {
			icon = HelpersImages.getIcon(ICON_DOCUMENT_PRIVATE);
		}
		return icon;
	}
}
