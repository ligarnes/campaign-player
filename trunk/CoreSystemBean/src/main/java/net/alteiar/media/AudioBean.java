package net.alteiar.media;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.thread.MyRunnable;
import net.alteiar.thread.ThreadPoolUtils;
import net.alteiar.utils.files.video.SerializableAudio;

import org.apache.log4j.Logger;
import org.simpleframework.xml.Element;

public class AudioBean extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_AUDIO_PROPERTY = "audio";

	private static final String METH_PLAY_DISTRIBUTED_METHOD = "playDistributed";
	private static final String METH_PAUSE_DISTRIBUTED_METHOD = "pauseDistributed";

	public static final String METH_PLAY_METHOD = "play";
	public static final String METH_PAUSE_METHOD = "pause";

	@Element
	private SerializableAudio audio;

	private int framePosition;

	private transient Player player;

	public AudioBean(SerializableAudio audio) {
		this.audio = audio;
		framePosition = 0;
	}

	public AudioBean() {
		audio = null;
		framePosition = 0;
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

	private Player getPlayer() {
		return player;
	}

	public void play() {
		playDistributed(0);
	}

	/**
	 * This is an internal function and should not be use directly
	 */
	public void playDistributed(int i) {
		if (notifyRemote(METH_PLAY_DISTRIBUTED_METHOD, null, 0)) {

			ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
				@Override
				public void run() {
					try {
						player = getAudio().restoreAudio();
						player.play();
					} catch (JavaLayerException e) {
						Logger.getLogger(getClass()).warn(
								"Exception when playing music", e);
					} catch (IOException e) {
						Logger.getLogger(getClass()).warn(
								"Exception when playing music", e);
					}
				}

				@Override
				public String getTaskName() {
					return "play audio";
				}
			});
			notifyLocal(METH_PLAY_METHOD, null, null);
		}
	}

	public void pause() {
		pauseDistributed(getPlayer().getPosition());
	}

	/**
	 * This is an internal function and should not be use directly
	 */
	public void pauseDistributed(int i) {
		if (notifyRemote(METH_PAUSE_DISTRIBUTED_METHOD, null, i)) {
			framePosition = i;
			getPlayer().close();
			notifyLocal(METH_PAUSE_METHOD, null, null);
		}
	}

	public void stop() {
		pauseDistributed(0);
	}
}
