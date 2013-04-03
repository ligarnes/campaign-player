package pathfinder.gui.document.viewer;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.zoom.MoveZoomListener;
import net.alteiar.zoom.PanelMoveZoom;

public class PanelViewImage extends PanelViewDocument<DocumentImageBean> {
	private static final long serialVersionUID = 1L;

	public PanelViewImage(DocumentImageBean bean) {
		super(bean);
		this.setLayout(new BorderLayout());

		try {
			BufferedImage img = bean.getImage().restoreImage();

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
