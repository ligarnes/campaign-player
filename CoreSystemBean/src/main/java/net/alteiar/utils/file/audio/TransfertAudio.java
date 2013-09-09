package net.alteiar.utils.file.audio;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public interface TransfertAudio {

	Player restoreAudio() throws IOException, JavaLayerException;
}
