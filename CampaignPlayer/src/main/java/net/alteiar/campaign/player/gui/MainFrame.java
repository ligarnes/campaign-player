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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.client.CampaignClient;
import net.alteiar.thread.TaskInfo;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MainFrame extends JFrame implements TaskInfo, WindowListener {
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

	private MainFrame() {
		super();

		this.addWindowListener(this);
		this.setIconImage(Helpers.getImage(Helpers
				.getPathIcons(Helpers.APP_ICON)));

		this.setMinimumSize(new Dimension(300, 300));
		westPanel = new JPanel();
		southPanel = new JPanel();

		// initComponent();
	}

	public void initComponent() {
		// east setup

		// west setup

		// center setup
		centerPanel = new MainPanel();

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
	public void setTaskName(String name) {
		currentTask.setText(name);
	}

	@Override
	public void setDescription(String description) {
		currentTask.setToolTipText(description);
	}

	@Override
	public void taskStarted() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setIndeterminate(true);
			}
		});
	}

	@Override
	public void taskFinished() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setIndeterminate(false);
			}
		});
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		CampaignClient.getInstance().disconnect();

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
