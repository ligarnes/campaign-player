package net.alteiar.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class PanelSelectColor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final Color DEFAULT_PLAYER_COLOR = Color.BLUE;

	private final JButton btnColorSelector;
	private Color currentColor;

	public PanelSelectColor() {
		super();
		currentColor = DEFAULT_PLAYER_COLOR;

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

	public void setColor(Color color){
		this.currentColor = color;
	} 
	
	public Color getColor() {
		return currentColor;
	}
}
