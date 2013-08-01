package net.alteiar.campaign.player.fileChooser;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileView;

public class ImageFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;

	/** All preview icons will be this width and height */
	private static final int ICON_SIZE = 16;

	/** This blank icon will be used while previews are loading */
	private static final Image LOADING_IMAGE = new BufferedImage(ICON_SIZE,
			ICON_SIZE, BufferedImage.TYPE_INT_ARGB);

	/**
	 * Use a weak hash map to cache images until the next garbage collection
	 * (saves memory)
	 */
	private final Map<File, ImageIcon> imageCache = new WeakHashMap<File, ImageIcon>();

	public ImageFileChooser() {
		super();
		initialize();
	}

	public ImageFileChooser(String path) {
		super(path);
		initialize();
	}

	private void initialize() {
		setFileView(new ThumbnailView());

		JPanel pane = new JPanel();

		pane.add(new JLabel("test s'est trop cool"));
		setAccessory(pane);
	}

	private class ThumbnailView extends FileView {
		/** This thread pool is where the thumnnail icon loaders run */
		private final ExecutorService executor = Executors
				.newCachedThreadPool();

		@Override
		public Icon getIcon(File file) {

			// Our cache makes browsing back and forth lightning-fast! :D
			synchronized (imageCache) {
				ImageIcon icon = imageCache.get(file);

				if (icon == null) {
					// Create a new icon with the default image
					icon = new ImageIcon(LOADING_IMAGE);

					// Add to the cache
					imageCache.put(file, icon);

					// Submit a new task to load the image and update the
					// icon
					executor.submit(new ThumbnailIconLoader(icon, file));
				}

				return icon;
			}
		}
	}

	private class ThumbnailIconLoader implements Runnable {
		private final ImageIcon icon;
		private final File file;

		public ThumbnailIconLoader(ImageIcon i, File f) {
			icon = i;
			file = f;
		}

		@Override
		public void run() {
			System.out.println("Loading image: " + file);

			// Load and scale the image down, then replace the icon's old
			// image with the new one.
			ImageIcon newIcon = new ImageIcon(file.getAbsolutePath());
			Image img = newIcon.getImage().getScaledInstance(ICON_SIZE,
					ICON_SIZE, Image.SCALE_SMOOTH);
			icon.setImage(img);

			// Repaint the dialog so we see the new icon.
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					repaint();
				}
			});
		}
	}

}
