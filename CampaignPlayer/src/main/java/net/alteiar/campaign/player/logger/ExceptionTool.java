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
package net.alteiar.campaign.player.logger;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 * @author Romain Bouleis
 * 
 */
public class ExceptionTool {

	public static void showWarning(Throwable e, String msg) {
		// stock la trace d'execution dans une String
		StringWriter sw = new StringWriter();

		if (e != null) {
			e.printStackTrace(new PrintWriter(sw));
		}

		// cree un JTextArea pour afficher le contenu de l'exception
		JTextArea textArea = new JTextArea(sw.toString(), 20, 40);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory
				.createTitledBorder("D\u00E9tails de l'exception"));
		final JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVisible(false);

		// boutton details
		JButton details = new JButton("D\u00E9tails...");
		details.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JComponent) {
					JComponent comp = (JComponent) e.getSource();
					Container dialog = comp.getRootPane().getParent();
					if (dialog instanceof JDialog) {
						scrollPane.setVisible(!scrollPane.isVisible());
						((JDialog) dialog).pack();
					}
				}
			}
		});

		// boutton continuer
		JButton ok = new JButton("Continuer");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JComponent) {
					JComponent comp = (JComponent) e.getSource();
					Container dialog = comp.getRootPane().getParent();
					if (dialog instanceof JDialog) {
						((JDialog) dialog).dispose();
					}
				}
			}
		});

		// placement des composants
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.add(ok);
		buttonsPanel.add(details);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		if (msg == null) {
			msg = e.getMessage();
		}
		// affichage du message
		JOptionPane.showOptionDialog(null, msg, "Attention",
				JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,
				new Object[] { mainPanel }, ok);

	}

	public static void showError(RemoteException ex) {
		showError(ex,
				"Probl\u00E8me de r\u00E9seaux, le serveur est injoignable");
	}

	public static void showError(Exception e) {
		showError(e, null);
	}

	/**
	 * 
	 * @param e
	 * @param msg
	 *            if msg is null print e.getMessage()
	 */
	public static void showError(Throwable e, String msg) {
		e.printStackTrace();

		// stock la trace d'execution dans une String
		StringWriter sw = new StringWriter();

		if (e != null) {
			e.printStackTrace(new PrintWriter(sw));
		}

		// cree un JTextArea pour afficher le contenu de l'exception
		JTextArea textArea = new JTextArea(sw.toString(), 20, 40);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory
				.createTitledBorder("D\u00E9tails de l'exception"));
		final JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVisible(false);

		// boutton details
		JButton details = new JButton("D\u00E9tails...");
		details.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JComponent) {
					JComponent comp = (JComponent) e.getSource();
					Container dialog = comp.getRootPane().getParent();
					if (dialog instanceof JDialog) {
						scrollPane.setVisible(!scrollPane.isVisible());
						((JDialog) dialog).pack();
					}
				}
			}
		});

		// boutton continuer
		JButton ok = new JButton("Continuer");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JComponent) {
					JComponent comp = (JComponent) e.getSource();
					Container dialog = comp.getRootPane().getParent();
					if (dialog instanceof JDialog) {
						((JDialog) dialog).dispose();
					}
				}
			}
		});

		// boutton exit
		JButton cancel = new JButton("Arreter");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// placement des composants
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.add(ok);
		buttonsPanel.add(cancel);
		buttonsPanel.add(details);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		if (msg == null) {
			msg = e.getMessage();
		}
		// affichage du message
		JOptionPane.showOptionDialog(null, msg, "Erreur",
				JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null,
				new Object[] { mainPanel }, ok);
	}
}
