package net.alteiar.campaign.player.gui.map.battle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class PanelZoom extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private final MapEditableInfo battle;
	private final JButton btnZoomOut;
	private final JButton btnZoomIn;
	private final JLabel lblZoomvalue;

	public PanelZoom(MapEditableInfo battle) {
		this.battle = battle;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		btnZoomOut = new JButton("-");
		GridBagConstraints gbc_btnZoomOut = new GridBagConstraints();
		gbc_btnZoomOut.insets = new Insets(0, 0, 0, 5);
		gbc_btnZoomOut.gridx = 0;
		gbc_btnZoomOut.gridy = 0;
		add(btnZoomOut, gbc_btnZoomOut);

		lblZoomvalue = new JLabel("zoomValue");
		GridBagConstraints gbc_lblZoomvalue = new GridBagConstraints();
		gbc_lblZoomvalue.insets = new Insets(0, 0, 0, 5);
		gbc_lblZoomvalue.gridx = 1;
		gbc_lblZoomvalue.gridy = 0;
		add(lblZoomvalue, gbc_lblZoomvalue);
		lblZoomvalue.setHorizontalAlignment(SwingConstants.CENTER);

		btnZoomIn = new JButton("+");
		GridBagConstraints gbc_btnZoomIn = new GridBagConstraints();
		gbc_btnZoomIn.gridx = 2;
		gbc_btnZoomIn.gridy = 0;
		add(btnZoomIn, gbc_btnZoomIn);

		setupAction();
		updateZoomValue();
	}

	private void setupAction() {
		btnZoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zoomOut();
			}
		});

		btnZoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zoomIn();
			}
		});
	}

	protected void zoomIn() {
		this.battle.zoom(-2);
		updateZoomValue();
	}

	protected void zoomOut() {
		this.battle.zoom(+2);
		updateZoomValue();
	}

	protected void updateZoomValue() {
		Integer zoom = (int) (this.battle.getZoom() * 100);

		DecimalFormat df2 = new DecimalFormat("00.00");
		this.lblZoomvalue.setText(df2.format(zoom) + "%");
	}

	@Override
	public void update(Observable o, Object arg) {
		updateZoomValue();
	}
}
