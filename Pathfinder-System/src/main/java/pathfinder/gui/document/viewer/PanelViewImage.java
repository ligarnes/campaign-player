package pathfinder.gui.document.viewer;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.documents.BeanDocument;
import net.alteiar.image.ImageBean;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.zoom.MoveZoomListener;
import net.alteiar.zoom.PanelMoveZoom;

public class PanelViewImage extends PanelViewDocument {
	private static final long serialVersionUID = 1L;

	public PanelViewImage() {
		this.setLayout(new BorderLayout());
	}

	@Override
	public void setDocument(BeanDocument doc) {
		this.removeAll();

		ImageBean imgBean = doc.getBean();
		if (imgBean != null) {
			try {
				BufferedImage img = imgBean.getImage().restoreImage();

				PanelImage imgPane = new PanelImage(img);
				PanelMoveZoom<PanelImage> scroll = new PanelMoveZoom<PanelImage>(
						imgPane);

				MoveZoomListener listener = new MoveZoomListener(scroll);
				scroll.addMouseListener(listener);
				scroll.addMouseMotionListener(listener);
				scroll.addMouseWheelListener(listener);

				imgPane.addMouseListener(listener);
				imgPane.addMouseMotionListener(listener);
				imgPane.addMouseWheelListener(listener);

				this.add(scroll, BorderLayout.CENTER);
			} catch (IOException e) {
				// TODO
				// set image to a default not able to read image message
				// img = null;
				ExceptionTool.showError(e);
			}
		}
	}

}
