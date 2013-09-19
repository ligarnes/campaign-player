package net.alteiar.campaign.player.gui.centerViews.explorer.mine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.documents.BeanBasicDocument;

public class PanelDocumentEditable extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel lblIcon;
	private final JTextField textField;

	public PanelDocumentEditable(final MyTreeCellEditor editor) {
		setOpaque(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblIcon = new JLabel();
		lblIcon.setMaximumSize(new Dimension(32, 32));
		lblIcon.setMinimumSize(new Dimension(32, 32));
		lblIcon.setPreferredSize(new Dimension(32, 32));
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.anchor = GridBagConstraints.EAST;
		gbc_lblIcon.gridheight = 2;
		gbc_lblIcon.insets = new Insets(0, 0, 0, 5);
		gbc_lblIcon.gridx = 0;
		gbc_lblIcon.gridy = 0;
		add(lblIcon, gbc_lblIcon);

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(152, 25));
		textField.setMinimumSize(new Dimension(152, 25));
		textField.setMaximumSize(new Dimension(152, 2147483647));
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.stopCellEditing();
			}
		});
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textField, gbc_textField);

		JLabel lblShared = new JLabel("");
		lblShared.setOpaque(true);
		lblShared.setPreferredSize(new Dimension(32, 32));
		lblShared.setMinimumSize(new Dimension(32, 32));
		lblShared.setMaximumSize(new Dimension(32, 32));
		GridBagConstraints gbc_lblShared = new GridBagConstraints();
		gbc_lblShared.gridheight = 2;
		gbc_lblShared.insets = new Insets(0, 0, 5, 0);
		gbc_lblShared.gridx = 2;
		gbc_lblShared.gridy = 0;
		add(lblShared, gbc_lblShared);

		JLabel lblOwner = new JLabel("(cody)");
		lblOwner.setMaximumSize(new Dimension(31, 10));
		lblOwner.setMinimumSize(new Dimension(31, 10));
		lblOwner.setPreferredSize(new Dimension(31, 10));
		lblOwner.setFont(new Font("Tahoma", Font.PLAIN, 8));
		GridBagConstraints gbc_lblOwner = new GridBagConstraints();
		gbc_lblOwner.insets = new Insets(0, 0, 0, 5);
		gbc_lblOwner.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblOwner.gridx = 1;
		gbc_lblOwner.gridy = 1;
		add(lblOwner, gbc_lblOwner);

		lblOwner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("mouse click");
			}
		});

	}

	public void setIcon(Icon icon) {
		lblIcon.setIcon(icon);
	}

	public void setBean(BeanBasicDocument doc) {
		textField.setText(doc.getDocumentName());
	}

	public String getDocumentName() {
		return textField.getText();
	}
}
