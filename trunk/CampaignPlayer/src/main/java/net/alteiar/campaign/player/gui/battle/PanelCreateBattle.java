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
package net.alteiar.campaign.player.gui.battle;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.campaign.player.fileChooser.StaticDialog;
import net.alteiar.campaign.player.gui.tools.PanelLabelText;
import net.alteiar.server.shared.campaign.battle.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelCreateBattle extends JPanel {
	private static final long serialVersionUID = 1L;

	private final PanelLabelText textFieldBattleName;
	private final JTextField labelFile;

	private File currentImage;

	public PanelCreateBattle() {
		super(new FlowLayout(FlowLayout.LEFT, 10, 0));

		textFieldBattleName = new PanelLabelText("Nom:", "sans nom", 15);
		labelFile = new JTextField(40);
		labelFile.setEditable(false);

		JPanel paneLeft = new JPanel();
		paneLeft.setLayout(new BoxLayout(paneLeft, BoxLayout.Y_AXIS));
		paneLeft.add(textFieldBattleName);

		JPanel pane = new JPanel(new FlowLayout());
		pane.add(new JLabel("Fichier"));
		pane.add(labelFile);

		JButton selectMap = new JButton("Selectionner une carte");
		selectMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseBackground();
			}
		});
		pane.add(selectMap);

		paneLeft.add(pane);

		this.add(paneLeft);
	}

	public String getBattleName() {
		return textFieldBattleName.getText();
	}

	public File getBattleBackground() {
		return currentImage;
	}

	public Scale getBattleScale() {
		return new Scale(80, 1.5);
	}

	protected void chooseBackground() {
		File file = StaticDialog.getSelectedImageFile(this);
		if (file != null) {
			try {
				labelFile.setText(file.getCanonicalPath());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			currentImage = file;
		}
		this.revalidate();
	}
}
