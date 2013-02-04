package net.alteiar.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CharacterFileFilter extends FileFilter {
	public static final String PCGEN_EXTENSION = ".pcg";
	public static final String[] EXTENTION_FILE = new String[] { ".psr",
			PCGEN_EXTENSION };

	@Override
	public boolean accept(File f) {
		Boolean accept = false;
		if (f.isDirectory()) {
			accept = true;
		}

		for (String ext : EXTENTION_FILE) {
			if (f.getName().endsWith(ext)) {
				accept = true;
				break;
			}
		}
		return accept;
	}

	@Override
	public String getDescription() {
		return "Fichier de personnage";
	}

}
