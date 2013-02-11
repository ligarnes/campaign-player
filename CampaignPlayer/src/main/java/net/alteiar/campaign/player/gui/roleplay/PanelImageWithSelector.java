package net.alteiar.campaign.player.gui.roleplay;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.tools.PanelMoveZoom;

public class PanelImageWithSelector extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JPanel panelImage;

	public PanelImageWithSelector() {
		setLayout(new BorderLayout(0, 0));

		JComboBox comboBoxSelect = new JComboBox();
		add(comboBoxSelect, BorderLayout.NORTH);

		panelImage = new JPanel(new BorderLayout());
		add(panelImage, BorderLayout.CENTER);

		try {
			this.selectImage(ImageIO
					.read(new URL(
							"http://www.fantasygrounds.com/images/screenshots/fg2-screenshot-01.jpg")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void selectImage(BufferedImage img) {
		panelImage.removeAll();
		PanelImage imgPane = new PanelImage(img);
		PanelMoveZoom<PanelImage> scroll = new PanelMoveZoom<PanelImage>(
				imgPane);

		MoveZoomListener listener = new MoveZoomListener(scroll);
		imgPane.addMouseListener(listener);
		imgPane.addMouseMotionListener(listener);
		imgPane.addMouseWheelListener(listener);

		panelImage.add(scroll, BorderLayout.CENTER);
	}
}
