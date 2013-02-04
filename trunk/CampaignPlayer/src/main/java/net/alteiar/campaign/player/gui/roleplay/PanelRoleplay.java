package net.alteiar.campaign.player.gui.roleplay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import net.alteiar.ExceptionTool;
import net.alteiar.campaign.player.gui.tools.PanelMoveZoom;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;

public class PanelRoleplay extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JDesktopPane pane;

	public PanelRoleplay() {
		this.setLayout(new BorderLayout());

		pane = new JDesktopPane();

		JButton btn = new JButton();
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFrame();
			}
		});

		this.add(pane, BorderLayout.CENTER);
		this.add(btn, BorderLayout.WEST);
	}

	private void addFrame() {
		JInternalFrame internal = new JInternalFrame("image", true, true, true);

		PanelImage imgPane = null;
		try {
			imgPane = new PanelImage(openPdf());
		} catch (IOException e) {
			ExceptionTool.showError(e);
		}
		// PanelImage imgPane = new PanelImage(openImage());
		PanelMoveZoom<PanelImage> scroll = new PanelMoveZoom<PanelImage>(
				imgPane);

		Dimension size = new Dimension((int) Math.min(imgPane
				.getPreferredSize().getWidth(), pane.getSize().getWidth()),
				(int) Math.min(imgPane.getPreferredSize().getHeight(), pane
						.getSize().getHeight()));

		scroll.setMaximumSize(size);
		scroll.setPreferredSize(size);
		scroll.setMinimumSize(size);

		MoveZoomListener listener = new MoveZoomListener(scroll);
		imgPane.addMouseListener(listener);
		imgPane.addMouseMotionListener(listener);
		imgPane.addMouseWheelListener(listener);

		internal.getContentPane().add(scroll);

		internal.pack();
		internal.setVisible(true);
		pane.add(internal);
	}

	private BufferedImage openImage() {
		BufferedImage img = null;
		try {
			img = ImageIO
					.read(new URL(
							"http://www.fantasygrounds.com/images/screenshots/fg2-screenshot-01.jpg"));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private BufferedImage openPdf() throws IOException {
		String pdfPath = "C:\\Users\\ligarnes\\Documents\\PDFs Output\\Merge.pdf";

		BufferedImage buffImage = null;

		// laod PDF document
		PDDocument document = PDDocument.load(new File(pdfPath));

		// get all pages
		List<PDPage> pages = document.getDocumentCatalog().getAllPages();

		// for each page
		for (int i = 0; i < pages.size(); i++) {
			// single page
			PDPage singlePage = pages.get(i);

			// to BufferedImage
			buffImage = singlePage.convertToImage();
		}

		document.close();

		return buffImage;
		/*
		// config option 1:convert all document to image
		String[] args_1 = new String[3];
		args_1[0] = "-jpg";
		args_1[1] = "my_image_1";
		args_1[2] = pdfPath;

		// config option 2:convert page 1 in pdf to image
		String[] args_2 = new String[7];
		args_2[0] = "-startPage";
		args_2[1] = "1";
		args_2[2] = "-endPage";
		args_2[3] = "1";
		args_2[4] = "-outputPrefix";
		args_2[5] = "my_image_2";
		args_2[6] = pdfPath;
		
		try {
			// will output "my_image_1.jpg"
			PDFToImage.main(args_1);
			// will output "my_image_2.jpg"
			// PDFToImage.main(args_2);
		} catch (Exception e) {
			// logger.error(e.getMessage(), e);
		}*/
	}
}
