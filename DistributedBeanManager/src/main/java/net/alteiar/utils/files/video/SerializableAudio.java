/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.utils.files.video;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.alteiar.utils.files.SerializableFile;

/**
 * @author Cody Stoutenburg
 * 
 */
public class SerializableAudio extends SerializableFile implements
		TransfertAudio {
	private static final long serialVersionUID = 4786344613415239528L;

	// private transient Player audio;

	public SerializableAudio() {
		super();
	}

	public SerializableAudio(File file) throws IOException {
		super(file);
	}

	@Override
	public Player restoreAudio() throws IOException, JavaLayerException {
		// if (audio == null) {
		// audio = new Player(new ByteArrayInputStream(getBytes()));
		// }
		return new Player(new ByteArrayInputStream(getBytes()));
	}
}
