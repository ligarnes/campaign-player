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
package net.alteiar.campaign.player.gui.connection;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import net.alteiar.CampaignClient;
import net.alteiar.server.document.DocumentIO;

public class PanelLoadLoadingCampaign extends PanelStartGameDialog implements
		ActionListener {
	private static final long serialVersionUID = 1L;

	private final JProgressBar progressBar;
	private final Timer time;

	private int totalBeans;
	private Boolean clientCreated;

	public PanelLoadLoadingCampaign(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);

		clientCreated = false;

		time = new Timer(100, this);
		time.start();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 450, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 17, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblChargementDeLa = new JLabel("Chargement de la campagne");
		lblChargementDeLa.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblChargementDeLa = new GridBagConstraints();
		gbc_lblChargementDeLa.insets = new Insets(0, 0, 5, 0);
		gbc_lblChargementDeLa.gridx = 0;
		gbc_lblChargementDeLa.gridy = 0;
		add(lblChargementDeLa, gbc_lblChargementDeLa);

		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		add(progressBar, gbc_progressBar);
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return new PanelChoosePlayer(getDialog(), null);
	}

	protected int countValidFiles() {
		int count = 0;
		final CampaignClient c = CampaignClient.getInstance();
		File baseDir = new File(c.getCampaignName());
		if (baseDir.exists()) {
			for (File dir : baseDir.listFiles()) {
				if (dir.isDirectory()) {
					for (File fi : dir.listFiles()) {
						if (fi.isFile() && DocumentIO.isFileValid(fi)) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final CampaignClient c = CampaignClient.getInstance();

		if (c != null) {
			if (!clientCreated) {
				totalBeans = countValidFiles();
				progressBar.setMaximum(totalBeans * 2);
				clientCreated = true;
			}

			if (clientCreated) {
				int count = c.getRemoteDocumentCount();
				count += c.getLocalDocumentCount();
				progressBar.setValue(count);
				if (count >= (2 * totalBeans)) {
					nextState();
					time.stop();
				}
			}
		}
	}
}
