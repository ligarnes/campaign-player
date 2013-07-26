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
package net.alteiar.campaign.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RMISecurityManager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javazoom.jl.decoder.JavaLayerException;
import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.connection.StartGameDialog;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.utils.files.video.SerializableAudio;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Main {

	public static void testAudio() {
		try {
			SerializableAudio mp3player = new SerializableAudio(
					new File(
							"C:\\Users\\ligarnes\\Music\\Era I\\01 - Ameno (Remix).mp3"));

			mp3player.restoreAudio().play();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JavaLayerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}/*
		 * try { File mp3 = new File(
		 * "C:\\Users\\ligarnes\\Music\\Era I\\01 - Ameno (Remix).mp3");
		 * 
		 * File ogg = new File(
		 * "C:\\Users\\ligarnes\\Music\\Apocalyptica\\07 Inquisiton Symphony.ogg"
		 * );
		 * 
		 * // Open an audio input stream. AudioInputStream audioIn =
		 * AudioSystem.getAudioInputStream(ogg); // Get a sound clip resource.
		 * Clip clip = AudioSystem.getClip(); // Open audio clip and load
		 * samples from the audio input stream. clip.open(audioIn);
		 * clip.start(); } catch (UnsupportedAudioFileException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 * catch (LineUnavailableException e) { e.printStackTrace(); }
		 */catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("java.security.policy", "./ressources/security.txt");
		// Create and install a security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		// Initialize log4j
		DOMConfigurator.configure("./ressources/log/log4j.xml");

		testAudio();
		if (true) {
			return;
		}

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			Logger.getLogger(Main.class).warn(
					"Impossible de changer le look and feel", e);
		} catch (ClassNotFoundException e) {
			Logger.getLogger(Main.class).warn(
					"Impossible de changer le look and feel", e);
		} catch (InstantiationException e) {
			Logger.getLogger(Main.class).warn(
					"Impossible de changer le look and feel", e);
		} catch (IllegalAccessException e) {
			Logger.getLogger(Main.class).warn(
					"Impossible de changer le look and feel", e);
		}

		// Touch to load plugin class
		PluginSystem.getInstance();

		StartGameDialog startGameDialog = new StartGameDialog(MainFrame.FRAME,
				"Campaign Player", true);

		startGameDialog.setVisible(true);

		if (startGameDialog.isReadyToStart()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					MainFrame.FRAME.initComponent();
					MainFrame.FRAME.setVisible(true);
				}
			});
		} else {
			System.exit(0);
		}
	}

	public static void showStat() {
		Runtime runtime = Runtime.getRuntime();
		Integer byteToMega = 1048576;
		System.out.print("used : "
				+ ((runtime.totalMemory() - runtime.freeMemory()) / byteToMega)
				+ "mb ");
		System.out.print("  committed : "
				+ (runtime.totalMemory() / byteToMega) + "mb ");
		System.out.println("  max : " + (runtime.maxMemory() / byteToMega)
				+ "mb ");
	}
}
