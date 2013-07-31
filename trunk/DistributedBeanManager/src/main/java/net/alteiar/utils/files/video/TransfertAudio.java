package net.alteiar.utils.files.video;

import java.io.IOException;
import java.io.Serializable;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public interface TransfertAudio extends Serializable {

	Player restoreAudio() throws IOException, JavaLayerException;
}
