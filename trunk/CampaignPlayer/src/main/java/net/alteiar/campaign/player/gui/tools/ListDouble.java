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

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.tools.adapter.BasicAdapter;
import net.alteiar.campaign.player.gui.tools.transferable.ListSource;
import net.alteiar.campaign.player.gui.tools.transferable.ObjectLabel;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ListDouble<E extends BasicAdapter<?>> extends JPanel {
	private static final long serialVersionUID = 1L;

	public static String EVENT_ADD = ListSource.EVENT_ADD;
	public static String EVENT_REMOVE = ListSource.EVENT_REMOVE;

	public static Color UNSELECTED = new Color(115, 53, 28);
	public static Color SELECTED = new Color(65, 33, 18);

	public static Color TEXT_COLOR = new Color(219, 213, 137);

	private final ListSource panelOrigin;
	private final ListSource panelDest;
	private List<E> allValues;

	public ListDouble(List<E> data, String titleComplete, String titleNew) {
		allValues = data;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 55, 55, 0 };
		gridBagLayout.rowHeights = new int[] { 14, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblTitlecomplete = new JLabel(titleComplete);
		GridBagConstraints gbc_lblTitlecomplete = new GridBagConstraints();
		gbc_lblTitlecomplete.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitlecomplete.gridx = 0;
		gbc_lblTitlecomplete.gridy = 0;
		add(lblTitlecomplete, gbc_lblTitlecomplete);

		JLabel lblTitlenew = new JLabel(titleNew);
		GridBagConstraints gbc_lblTitlenew = new GridBagConstraints();
		gbc_lblTitlenew.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitlenew.gridx = 1;
		gbc_lblTitlenew.gridy = 0;
		add(lblTitlenew, gbc_lblTitlenew);

		panelOrigin = new ListSource();
		panelOrigin.setLayout(new GridLayout(allValues.size(), 1));

		GridBagConstraints gbc_scrollPaneOrigin = new GridBagConstraints();
		gbc_scrollPaneOrigin.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneOrigin.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneOrigin.gridx = 0;
		gbc_scrollPaneOrigin.gridy = 1;
		add(panelOrigin, gbc_scrollPaneOrigin);

		panelDest = new ListSource();
		GridBagConstraints gbc_scrollPaneDest = new GridBagConstraints();
		gbc_scrollPaneDest.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneDest.gridx = 1;
		gbc_scrollPaneDest.gridy = 1;
		add(panelDest, gbc_scrollPaneDest);

		panelDest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyAction(e);
			}
		});
		panelDest.setLayout(new GridLayout(allValues.size(), 1));

		GridBagConstraints gbc_panelHeader = new GridBagConstraints();
		gbc_panelHeader.insets = new Insets(0, 0, 5, 5);
		gbc_panelHeader.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelHeader.gridx = 0;
		gbc_panelHeader.gridy = 1;
		GridBagLayout gbl_panelHeader = new GridBagLayout();
		gbl_panelHeader.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelHeader.rowHeights = new int[] { 0, 0 };
		gbl_panelHeader.columnWeights = new double[] { 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_panelHeader.rowWeights = new double[] { 0.0, Double.MIN_VALUE };

		reset();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		panelDest.setEnabled(enabled);
		panelOrigin.setEnabled(enabled);
	}

	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	public ActionListener[] getActionListeners() {
		return listenerList.getListeners(ActionListener.class);
	}

	private void notifyAction(ActionEvent event) {
		for (ActionListener listener : getActionListeners()) {
			listener.actionPerformed(event);
		}
	}

	public void setAllData(List<E> allData) {
		this.allValues = allData;

		reset();
	}

	public void reset() {
		this.panelOrigin.removeAll();
		this.panelDest.removeAll();
		for (E val : allValues) {
			ObjectLabel<E> lbl = new ObjectLabel<E>(val);
			panelOrigin.add(lbl);
		}
	}

	public void setSelectedData(Collection<E> selected) {
		this.panelOrigin.removeAll();
		this.panelDest.removeAll();

		for (E value : allValues) {
			if (selected.contains(value)) {
				panelDest.add(new ObjectLabel<E>(value));
			} else {
				panelOrigin.add(new ObjectLabel<E>(value));
			}
		}
		this.revalidate();
	}

	public List<E> getSelectedData() {
		List<E> result = new ArrayList<E>();
		for (int i = 0; i < this.panelDest.getComponentCount(); ++i) {
			Component comp = panelDest.getComponent(i);

			if (comp instanceof ObjectLabel<?>) {
				ObjectLabel<E> lbl = (ObjectLabel<E>) comp;
				result.add(lbl.getObject());
			}
		}

		return result;
	}
}
