package net.alteiar.campaign.player.gui.map.element.utils;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import net.alteiar.campaign.player.Helpers;

public class PanelSelectColor extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JButton btnColorSelector;
	private Color currentColor;

	public PanelSelectColor() {
		super();
		currentColor = Color.BLUE;

		btnColorSelector = new JButton();
		GridBagConstraints gbc_btnColorselector = new GridBagConstraints();
		gbc_btnColorselector.gridx = 3;
		gbc_btnColorselector.gridy = 1;
		add(btnColorSelector, gbc_btnColorselector);
		btnColorSelector.setIcon(Helpers.getIconColor(currentColor, 22, 22));
		btnColorSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectColor();
			}
		});
	}

	protected void selectColor() {
		Color temp = JColorChooser.showDialog(this, "Choisissez une couleur",
				currentColor);
		if (temp != null) {
			currentColor = temp;
			btnColorSelector
					.setIcon(Helpers.getIconColor(currentColor, 22, 22));
		}
	}

	public Color getColor() {
		return currentColor;
	}
}
