package net.alteiar.campaign.player.gui.tools.transferable;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import net.alteiar.campaign.player.gui.tools.adapter.BasicAdapter;

public class ObjectLabel<E extends BasicAdapter<?>> extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel label;
	private E object;

	public ObjectLabel(E obj) {
		this.setLayout(new BorderLayout());
		this.setBorder(new LineBorder(Color.BLACK, 1));

		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);

		this.setBackground(ListSource.UNSELECTED);
		label.setForeground(ListSource.TEXT_COLOR);

		this.add(label, BorderLayout.CENTER);
		this.setObect(obj);
	}

	public void setObect(E obj) {
		object = obj;
		label.setText(object.toString());
	}

	public E getObject() {
		return object;
	}
}
