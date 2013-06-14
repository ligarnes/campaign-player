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
			fireActionPerfomed();
		}
	}

	public void setColor(Color color) {
		this.currentColor = color;
		btnColorSelector.setIcon(Helpers.getIconColor(currentColor, 22, 22));
	}

	public Color getColor() {
		return currentColor;
	}

	public void addActionListener(ActionListener listener) {
		listenerList.add(ActionListener.class, listener);
	}

	public void removeActionListener(ActionListener listener) {
		listenerList.remove(ActionListener.class, listener);
	}

	private void fireActionPerfomed() {
		ActionListener[] listeners = listenerList
				.getListeners(ActionListener.class);

		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"colorChanged");
		for (ActionListener actionListener : listeners) {
			actionListener.actionPerformed(evt);
		}

	}
}
