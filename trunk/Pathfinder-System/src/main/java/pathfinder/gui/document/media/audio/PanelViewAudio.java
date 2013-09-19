package pathfinder.gui.document.media.audio;

import java.awt.BorderLayout;

import net.alteiar.beans.media.AudioBean;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.documents.BeanDocument;

public class PanelViewAudio extends PanelViewDocument {
	private static final long serialVersionUID = 1L;

	private final PanelMp3Player player;

	public PanelViewAudio() {
		this.setLayout(new BorderLayout());

		player = new PanelMp3Player();
		this.add(player);
	}

	@Override
	public void setDocument(BeanDocument doc) {
		AudioBean audioBean = doc.getBean();
		if (audioBean != null) {
			player.setAudioBean(audioBean);
		}
	}

}
