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
package net.alteiar.campaign.player.gui.tools;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelLabelText extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField textField;

	public PanelLabelText(String label, Integer column) {
		this(label, "", column);
	}

	public PanelLabelText(String label, String initialValue, Integer column) {
		super(new FlowLayout());

		textField = new JTextField(initialValue, column);

		this.add(new JLabel(label));
		this.add(textField);
	}

	public String getText() {
		return textField.getText();
	}

}