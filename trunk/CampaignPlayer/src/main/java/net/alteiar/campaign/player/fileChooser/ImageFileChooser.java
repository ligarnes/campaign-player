package net.alteiar.campaign.player.fileChooser;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

public class ImageFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;

	/**
	 * Use a weak hash map to cache images until the next garbage collection
	 * (saves memory)
	 */
	private final Map<String, ImageIcon> imageCache;

	public ImageFileChooser() {
		super();

		imageCache = new WeakHashMap<String, ImageIcon>();
		initialize();
	}

	public ImageFileChooser(String path) {
		super(path);

		imageCache = new WeakHashMap<String, ImageIcon>();

		initialize();
	}

	private void initialize() {

		setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png",
				"jpeg"));
		setFileView(new ThumbnailView());

		JPanel pane = new JPanel();

		pane.add(new MiniImageView());
		setAccessory(pane);
	}

	/** This thread pool is where the thumnnail icon loaders run */
	private final ExecutorService executor = Executors.newCachedThreadPool();

	/** All preview icons will be this width and height */
	private static final int ICON_SIZE = 16;

	/** This blank icon will be used while previews are loading */
	private static final Image LOADING_IMAGE_SMALL = new BufferedImage(
			ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);

	private static final int ICON_SIZE_MAX = 300;
	private static final Image LOADING_IMAGE_LARGE = new BufferedImage(
			ICON_SIZE_MAX, ICON_SIZE_MAX, BufferedImage.TYPE_INT_ARGB);

	private class MiniImageView extends JLabel {
		private static final long serialVersionUID = 1L;

		public MiniImageView() {
			this.setPreferredSize(new Dimension(300, 300));

			ImageFileChooser.this
					.addPropertyChangeListener(new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							changeImage();
						}
					});
		}

		private void changeImage() {
			File file = ImageFileChooser.this.getSelectedFile();

			if (file == null || file.isDirectory()) {
				this.setIcon(null);
			} else {
				// Our cache makes browsing back and forth lightning-fast! :D
				String filename = file.getPath() + " - large";
				ImageIcon icon = imageCache.get(filename);

				if (icon == null) {
					// Create a new icon with the default image
					icon = new ImageIcon(LOADING_IMAGE_LARGE);

					// Add to the cache
					synchronized (imageCache) {
						imageCache.put(filename, icon);
					}

					// Submit a new task to load the image and update the
					// icon
					executor.submit(new ThumbnailIconLoader(icon, file));
				}
				this.setIcon(icon);
			}
		}
	}

	private class ThumbnailView extends FileView {

		@Override
		public Icon getIcon(File file) {

			// return super.getIcon(file);

			if (file.isDirectory()) {
				return super.getIcon(file);
			}

			// Our cache makes browsing back and forth lightning-fast! :D
			String filename = file.getPath() + " - small";

			ImageIcon icon = imageCache.get(filename);

			if (icon == null) {
				// Create a new icon with the default image
				icon = new ImageIcon(LOADING_IMAGE_SMALL);

				// Add to the cache
				synchronized (imageCache) {
					imageCache.put(filename, icon);
				}

				// Submit a new task to load the image and update the icon
				executor.submit(new ThumbnailIconLoader(icon, file));
			}
			return icon;
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
			// Load and scale the image down, then replace the icon's old
			// image with the new one.
			ImageIcon newIcon = new ImageIcon(file.getAbsolutePath());

			int width = Math.min(icon.getIconWidth(), newIcon.getIconWidth());
			int height = Math
					.min(icon.getIconHeight(), newIcon.getIconHeight());

			Image img = newIcon.getImage().getScaledInstance(width, height,
					Image.SCALE_FAST);
			icon.setImage(img);

			this.icon.setImage(img);

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
