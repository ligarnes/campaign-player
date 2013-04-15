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
package net.alteiar.campaign.player.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.logger.LoggerConfig;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MainFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 7112970933851075952L;

	public static final MainFrame FRAME = new MainFrame();

	// East

	// Center
	private MainPanel centerPanel;

	// West
	private final JPanel westPanel;

	// south
	private final JPanel southPanel;
	private JLabel currentTask;
	private JProgressBar progressBar;

	//
	private final KeyListener listeners;

	private MainFrame() {
		super();

		this.addWindowListener(this);
		this.setIconImage(Helpers.getImage(Helpers
				.getPathIcons(Helpers.APP_ICON)));

		this.setMinimumSize(new Dimension(800, 600));
		westPanel = new JPanel();
		southPanel = new JPanel();

		final PanelLog logsClient = new PanelLog(LoggerConfig.CLIENT_LOGGER);
		final PanelLog logsServer = new PanelLog(LoggerConfig.SERVER_LOGGER);

		listeners = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == 'A') {
					JDialog dlg = new JDialog(MainFrame.FRAME);
					dlg.setTitle("Log du client");
					dlg.add(logsClient);
					dlg.pack();
					dlg.setVisible(true);
				}

				if (e.getKeyChar() == 'B') {
					JDialog dlg = new JDialog(MainFrame.FRAME);
					dlg.setTitle("Log du server");
					dlg.add(logsServer);
					dlg.pack();
					dlg.setVisible(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		};

		// initComponent();
	}

	public void initComponent() {
		// east setup

		// west setup

		// center setup
		centerPanel = new MainPanel();
		centerPanel.addKeyListener(listeners);

		// frame setup
		this.setTitle("Campaign Player");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		// south setup
		currentTask = new JLabel("rien");
		progressBar = new JProgressBar();

		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(currentTask);
		southPanel.add(progressBar);

		this.add(centerPanel, BorderLayout.CENTER);
		this.add(westPanel, BorderLayout.WEST);
		this.add(southPanel, BorderLayout.SOUTH);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public MainPanel getMainPanel() {
		return this.centerPanel;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		CampaignClient.leaveGame();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// Restaure frame
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// Reduire frame
	}
}
