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
package net.alteiar.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Cody Stoutenburg
 */
public class DialogOkCancel<Pane extends JPanel & PanelOkCancel> extends
		JDialog {
	private static final long serialVersionUID = 2488030992771414680L;

	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;

	private final Pane mainPanel;
	private final JPanel panelValid;
	private final JButton cancelButton;
	private final JButton okButton;
	private int returnStatus = RET_CANCEL;

	public DialogOkCancel(final Frame fm, final String title,
			final boolean modal, Pane container) {
		super(fm, title, modal);

		mainPanel = container;

		panelValid = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		initComponent(container);
	}

	public void setOkText(String ok) {
		okButton.setText(ok);
	}

	public void setCancelText(String cancel) {
		cancelButton.setText(cancel);
	}

	/**
	 * @return the return status of this dialog - one of RET_OK or RET_CANCEL
	 */
	public final int getReturnStatus() {
		return returnStatus;
	}

	public final Pane getMainPanel() {
		return mainPanel;
	}

	public final JButton getCancelButton() {
		return cancelButton;
	}

	public final JButton getOkButton() {
		return okButton;
	}

	private void initComponent(Pane container) {
		final Container pane = this.getContentPane();
		// borderlayout
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent evt) {
				closeDialog(evt);
			}
		});

		okButton.setText("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		// flow layout align rigth
		panelValid.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelValid.add(cancelButton);
		panelValid.add(okButton);

		pane.add(mainPanel);
		pane.add(panelValid);
		this.pack();
	}

	public void setLayout(int flowLayoutAlign) {
		panelValid.setLayout(new FlowLayout(flowLayoutAlign));
	}

	private void okButtonActionPerformed(final ActionEvent evt) {
		if (mainPanel.isDataValid()) {
			doClose(RET_OK);
		} else {
			JOptionPane.showConfirmDialog(this, mainPanel.getInvalidMessage(),
					"Attention", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void cancelButtonActionPerformed(final ActionEvent evt) {
		doClose(RET_CANCEL);
	}

	/** Closes the dialog */
	private void closeDialog(final WindowEvent evt) {
		doClose(RET_CANCEL);
	}

	private void doClose(final int retStatus) {
		returnStatus = retStatus;
		setVisible(false);
		dispose();
	}
}
