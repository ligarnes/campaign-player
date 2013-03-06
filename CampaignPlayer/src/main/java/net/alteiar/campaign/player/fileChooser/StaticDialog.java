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
package net.alteiar.campaign.player.fileChooser;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.shared.ExceptionTool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class StaticDialog {
	private static JFileChooser FILE_CHOOSER_OPEN_IMAGE = null;
	private static JFileChooser FILE_CHOOSER_OPEN_CHARACTER = null;

	public static File getSelectedImageFile(Component comp) {
		if (FILE_CHOOSER_OPEN_IMAGE == null) {
			// setup the dialog
			String path = Helpers.getGlobalProperties().getMapPath();
			FILE_CHOOSER_OPEN_IMAGE = new JFileChooser(path);
			FILE_CHOOSER_OPEN_IMAGE.setFileFilter(new FileFilterImage());
		}

		File file = null;
		int returnVal = StaticDialog.FILE_CHOOSER_OPEN_IMAGE
				.showOpenDialog(comp);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = StaticDialog.FILE_CHOOSER_OPEN_IMAGE.getSelectedFile();
			try {
				Helpers.getGlobalProperties().setMapPath(file.getParent());
				Helpers.getGlobalProperties().save();
			} catch (IOException e) {
				// fail to save properties, just too bad
				ExceptionTool.showWarning(e,
						"Impossible de sauvegarder le chemin de votre images");
			}
		}

		return file;
	}

	public static File getSelectedCharacter(Component comp) {
		if (FILE_CHOOSER_OPEN_CHARACTER == null) {
			// setup the dialog
			String path = Helpers.getGlobalProperties().getCharacterPath();
			FILE_CHOOSER_OPEN_CHARACTER = new JFileChooser(path);
			// TODO fixme restore
			// FILE_CHOOSER_OPEN_CHARACTER
			// .setFileFilter(new CharacterFileFilter());
		}

		File file = null;
		int returnVal = StaticDialog.FILE_CHOOSER_OPEN_CHARACTER
				.showOpenDialog(comp);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = StaticDialog.FILE_CHOOSER_OPEN_CHARACTER.getSelectedFile();
			try {
				Helpers.getGlobalProperties()
						.setCharacterPath(file.getParent());
				Helpers.getGlobalProperties().save();
			} catch (IOException e) {
				// fail to save properties, just too bad
				ExceptionTool.showWarning(e,
						"Impossible de sauvegarder le chemin de votre images");
			}
		}

		return file;
	}
}
