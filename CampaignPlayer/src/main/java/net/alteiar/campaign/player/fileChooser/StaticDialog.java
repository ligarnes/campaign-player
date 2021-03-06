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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.alteiar.campaign.player.infos.Helpers;

/**
 * @author Cody Stoutenburg
 * 
 */
public class StaticDialog {

	public static File getSelectedImageFile(Component comp) {
		String path = Helpers.getGlobalProperties().getPathImages();
		JFileChooser chooser = new ImageFileChooser(path);

		File file = null;
		int returnVal = chooser.showOpenDialog(comp);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			Helpers.getGlobalProperties().setPathImages(file.getParent());
			Helpers.getGlobalProperties().save();
		}

		return file;
	}

	public static File getSelectedAudioFile(Component comp) {
		String path = Helpers.getGlobalProperties().getPathAudio();
		JFileChooser chooser = new JFileChooser(path);
		chooser.setFileFilter(new FileNameExtensionFilter("Audio", "mp3"));

		File file = null;
		int returnVal = chooser.showOpenDialog(comp);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			Helpers.getGlobalProperties().setPathAudio(file.getParent());
			Helpers.getGlobalProperties().save();
		}

		return file;
	}
}
