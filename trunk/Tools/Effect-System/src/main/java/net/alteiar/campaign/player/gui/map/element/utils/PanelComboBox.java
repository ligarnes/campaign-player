package net.alteiar.campaign.player.gui.map.element.utils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class PanelComboBox extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JComboBox<String> listChoice;
	private String choice;

	public PanelComboBox(String[] list) {
		super();
		choice = "";
		listChoice = new JComboBox<String>(list);
		GridBagConstraints gbc_btnColorselector = new GridBagConstraints();
		gbc_btnColorselector.gridx = 3;
		gbc_btnColorselector.gridy = 1;
		listChoice.setPreferredSize(new Dimension(100, 20));
		add(listChoice, gbc_btnColorselector);
		listChoice.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				choice = (String) arg0.getItem();
			}

		});
	}

	public String getChoice() {
		return choice;
	}

}
