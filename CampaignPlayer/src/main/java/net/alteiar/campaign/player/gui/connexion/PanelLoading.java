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
package net.alteiar.campaign.player.gui.connexion;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.infos.Languages;

public class PanelLoading extends PanelStartGameDialog implements
		ActionListener {
	private static final long serialVersionUID = 1L;

	private final JProgressBar progressBar;
	private final Timer time;

	public PanelLoading(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		time = new Timer(100, this);
		time.start();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 450, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 17, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblChargementDeLa = new JLabel(
				Languages.getText("loading_campaign"));
		lblChargementDeLa.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblChargementDeLa = new GridBagConstraints();
		gbc_lblChargementDeLa.insets = new Insets(0, 0, 5, 0);
		gbc_lblChargementDeLa.gridx = 0;
		gbc_lblChargementDeLa.gridy = 0;
		add(lblChargementDeLa, gbc_lblChargementDeLa);

		progressBar = new JProgressBar();

		progressBar.setIndeterminate(true);

		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		add(progressBar, gbc_progressBar);
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return new PanelCreateOrChoosePlayer(getDialog());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final CampaignClient c = CampaignClient.getInstance();

		if (c != null && c.isLoaded()) {
			nextState();
			time.stop();
		}
	}
}
