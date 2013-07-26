package net.alteiar.media;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.files.video.SerializableAudio;

import org.simpleframework.xml.Element;

public class AudioBean extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_AUDIO_PROPERTY = "audio";

	@Element
	private SerializableAudio audio;

	public AudioBean(SerializableAudio image) {
		this.audio = image;
	}

	public AudioBean() {
		audio = null;
	}

	public SerializableAudio getAudio() {
		return audio;
	}

	public void setAudio(SerializableAudio audio) {
		SerializableAudio oldValue = this.audio;
		if (notifyRemote(PROP_AUDIO_PROPERTY, oldValue, audio)) {
			this.audio = audio;
			notifyLocal(PROP_AUDIO_PROPERTY, oldValue, audio);
		}
	}

	/**
	 * 
	 * @param id
	 * @return the buffered image or null if the document is not found
	 * @throws IOException
	 *             if we are not able to read the image
	 * @throws JavaLayerException
	 */
	public static AdvancedPlayer getAudio(UniqueID id) throws IOException,
			JavaLayerException {
		AudioBean imageBean = CampaignClient.getInstance().getBean(id);
		if (imageBean == null) {
			return null;
		}
		return imageBean.getAudio().restoreAudio();
	}
}
