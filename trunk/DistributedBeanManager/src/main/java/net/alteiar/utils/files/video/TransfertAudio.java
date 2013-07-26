package net.alteiar.utils.files.video;

import java.io.IOException;
import java.io.Serializable;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public interface TransfertAudio extends Serializable {

	AdvancedPlayer restoreAudio() throws IOException, JavaLayerException;
}
