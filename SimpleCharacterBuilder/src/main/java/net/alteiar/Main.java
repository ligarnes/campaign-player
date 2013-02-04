/*
 * Main.java
 * Copyright 2009 Connor Petty <cpmeister@users.sourceforge.net>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Created on Sep 1, 2009, 6:17:59 PM
 */
package net.alteiar;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.JAXBException;

import net.alteiar.gui.FrameCharacterBuilder;

/**
 * 
 * @author Connor Petty <cpmeister@users.sourceforge.net>
 */
public final class Main {

	/**
	 * @param args
	 *            the command line arguments
	 * @throws IOException
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws JAXBException, IOException {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FrameCharacterBuilder frame = new FrameCharacterBuilder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		/*
		PathfinderCharacter character = new PathfinderCharacter();

		character.setAc(10);
		character.setAcFlatFooted(12);
		character.setAcTouch(8);

		character.setStrength(10);
		character.setDexterity(10);
		character.setConstitution(10);
		character.setIntelligence(10);
		character.setWisdom(10);
		character.setCharisma(10);
		
		character.setHp(5);
		*/
		// PathfinderCharacterFacade character = CharacterIO.readFile(new File(
		// "./garde.xml"));
		// character.
		// CharacterIO.writeFile(new File("./perso.xml"), character);
	}
}
