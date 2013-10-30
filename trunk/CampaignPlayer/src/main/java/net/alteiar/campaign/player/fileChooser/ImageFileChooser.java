package net.alteiar.campaign.player.fileChooser;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.thread.MyRunnable;

public class ImageFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;

	/**
	 * Use a weak hash map to cache images until the next garbage collection
	 * (saves memory)
	 */
	private final Map<String, ImageIcon> imageCache;

	private MiniImageView imageView;

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

		JPanel pane = new JPanel();

		imageView = new MiniImageView();
		pane.add(imageView);
		setAccessory(pane);
	}

	/** This thread pool is where the thumnnail icon loaders run */
	// private final ExecutorService executor = Executors.newCachedThreadPool();

	private static final int ICON_SIZE_MAX = 300;

	private class MiniImageView extends JLabel {
		private static final long serialVersionUID = 1L;

		public MiniImageView() {
			this.setPreferredSize(new Dimension(ICON_SIZE_MAX, ICON_SIZE_MAX));

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
				String filename = file.getPath();
				ImageIcon icon = imageCache.get(filename);

				if (icon == null) {
					// Create a new icon with the default image
					icon = new ImageIcon(new BufferedImage(ICON_SIZE_MAX,
							ICON_SIZE_MAX, BufferedImage.TYPE_INT_ARGB));

					// Add to the cache
					synchronized (imageCache) {
						imageCache.put(filename, icon);
					}

					// Submit a new task to load the image and update the icon
					Threads.execute(new ThumbnailIconLoader(icon, file));
				} else {
					this.setIcon(icon);

					// Repaint the dialog so we see the new icon.
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							revalidate();
							repaint();
						}
					});
				}
			}
		}
	}

	private class ThumbnailIconLoader implements MyRunnable {
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

			imageView.changeImage();

		}

		@Override
		public String getTaskName() {
			return "run thumbnail icon " + file.getPath();
		}
	}

}
