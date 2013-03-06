/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg. All rights reserved.
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
package net.alteiar.campaign.player.gui;

import java.awt.GridLayout;
import java.rmi.RemoteException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.server.document.character.ICharacterRemote;

public class PanelCreateCharacterSheet extends JPanel {
	private static final long serialVersionUID = -2286637648122082031L;

	private final JLabel m_labelNom;
	private final JTextField m_textNom;

	private final JLabel m_labelPvTotal;
	private final JTextField m_textPvTotal;

	private final JLabel m_labelPvActuel;
	private final JTextField m_textPvActuel;

	private final JLabel m_labelModificateurInit;
	private final JTextField m_textModificateurInit;

	private final JLabel m_labelCa;
	private final JTextField m_textCa;

	private final JLabel m_labelCaDepourvu;
	private final JTextField m_textCaDepourvu;

	private final JLabel m_labelCaContact;
	private final JTextField m_textCaContact;

	private final JLabel m_labelCache;
	private final JCheckBox m_boxCache;

	public PanelCreateCharacterSheet() {
		m_labelNom = new JLabel("nom");
		m_textNom = new JTextField();

		m_labelPvTotal = new JLabel("pv totaux");
		m_textPvTotal = new JTextField();

		m_labelPvActuel = new JLabel("pv actuelle");
		m_textPvActuel = new JTextField();

		m_labelModificateurInit = new JLabel("modificateur d'initiative");
		m_textModificateurInit = new JTextField();

		m_labelCa = new JLabel("CA");
		m_textCa = new JTextField();

		m_labelCaDepourvu = new JLabel("CA D�pourvu");
		m_textCaDepourvu = new JTextField();

		m_labelCaContact = new JLabel("CA Contact");
		m_textCaContact = new JTextField();

		m_labelCache = new JLabel("cach�");
		m_boxCache = new JCheckBox();

		initGui();
	}

	private void initGui() {
		this.setLayout(new GridLayout(2, 7, 10, 5));

		this.add(m_labelNom);
		this.add(m_labelPvTotal);
		this.add(m_labelPvActuel);
		this.add(m_labelModificateurInit);
		this.add(m_labelCa);
		this.add(m_labelCaDepourvu);
		this.add(m_labelCaContact);
		this.add(m_labelCache);

		this.add(m_textNom);
		this.add(m_textPvTotal);
		this.add(m_textPvActuel);
		this.add(m_textModificateurInit);
		this.add(m_textCa);
		this.add(m_textCaDepourvu);
		this.add(m_textCaContact);
		this.add(m_boxCache);
	}

	public ICharacterRemote getCharacter() throws NumberFormatException,
			RemoteException {
		return null;// CampaignClient.INSTANCE.createCharacter(m_textNom.getText());
	}

	public Boolean isHidden() {
		return m_boxCache.isSelected();
	}
}
