package net.alteiar.zoom;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelZoomEditor extends JPanel implements ZoomListener {

	private static final long serialVersionUID = 1L;

	private final Zoomable zoomable;
	private final JButton btnZoomOut;
	private final JButton btnZoomIn;
	private final JLabel lblZoomvalue;

	public PanelZoomEditor(PanelMoveZoom<?> zoomable) {
		this.zoomable = zoomable;
		zoomable.addZoomListener(this);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		btnZoomOut = new JButton(new ZoomInOutAction(zoomable, +2));
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

		btnZoomIn = new JButton(new ZoomInOutAction(zoomable, -2));
		GridBagConstraints gbc_btnZoomIn = new GridBagConstraints();
		gbc_btnZoomIn.gridx = 2;
		gbc_btnZoomIn.gridy = 0;
		add(btnZoomIn, gbc_btnZoomIn);

		updateZoomValue();
	}

	protected void updateZoomValue() {
		// TODO need to get some callback when the zoom change
		Integer zoom = (int) (this.zoomable.getZoomFactor() * 100);

		DecimalFormat df2 = new DecimalFormat("00.00");
		this.lblZoomvalue.setText(df2.format(zoom) + "%");
	}

	@Override
	public void zoomChanged(Double zoomFactor) {
		updateZoomValue();
	}
}
