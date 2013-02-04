package net.alteiar.campaign.player.gui.tools.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.alteiar.campaign.player.gui.battle.plan.MapEditableInfo;

public class PanelZoom extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private final MapEditableInfo battle;
	private final JPanel panelZoom;
	private final JButton btnZoomOut;
	private final JButton btnZoomIn;
	private final JLabel lblZoomvalue;

	public PanelZoom(MapEditableInfo battle) {
		this.battle = battle;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 25, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		panelZoom = new JPanel();
		GridBagConstraints gbc_panelZoom = new GridBagConstraints();
		gbc_panelZoom.insets = new Insets(0, 0, 5, 0);
		gbc_panelZoom.fill = GridBagConstraints.BOTH;
		gbc_panelZoom.gridx = 0;
		gbc_panelZoom.gridy = 2;
		add(panelZoom, gbc_panelZoom);
		GridBagLayout gbl_panelZoom = new GridBagLayout();
		gbl_panelZoom.columnWidths = new int[] { 60, 0, 60, 0 };
		gbl_panelZoom.rowHeights = new int[] { 0, 0 };
		gbl_panelZoom.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelZoom.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelZoom.setLayout(gbl_panelZoom);

		btnZoomOut = new JButton("-");
		GridBagConstraints gbc_btnZoomOut = new GridBagConstraints();
		gbc_btnZoomOut.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnZoomOut.insets = new Insets(0, 0, 0, 5);
		gbc_btnZoomOut.gridx = 0;
		gbc_btnZoomOut.gridy = 0;
		panelZoom.add(btnZoomOut, gbc_btnZoomOut);

		lblZoomvalue = new JLabel("zoomValue");
		lblZoomvalue.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblZoomvalue = new GridBagConstraints();
		gbc_lblZoomvalue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblZoomvalue.insets = new Insets(0, 0, 0, 5);
		gbc_lblZoomvalue.gridx = 1;
		gbc_lblZoomvalue.gridy = 0;
		panelZoom.add(lblZoomvalue, gbc_lblZoomvalue);

		btnZoomIn = new JButton("+");
		GridBagConstraints gbc_btnZoomIn = new GridBagConstraints();
		gbc_btnZoomIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnZoomIn.gridx = 2;
		gbc_btnZoomIn.gridy = 0;
		panelZoom.add(btnZoomIn, gbc_btnZoomIn);

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
		this.lblZoomvalue.setText(zoom + "%");
	}

	@Override
	public void update(Observable o, Object arg) {
		updateZoomValue();
	}
}
