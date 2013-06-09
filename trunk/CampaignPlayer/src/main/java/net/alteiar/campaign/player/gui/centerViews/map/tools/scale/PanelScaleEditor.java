package net.alteiar.campaign.player.gui.centerViews.map.tools.scale;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.alteiar.campaign.player.gui.centerViews.map.tools.PanelZoomEditor;
import net.alteiar.dialog.PanelAlwaysValidOkCancel;
import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.documents.map.MapBean;
import net.alteiar.utils.map.Scale;
import net.alteiar.zoom.MoveZoomListener;
import net.alteiar.zoom.PanelMoveZoom;

public class PanelScaleEditor extends PanelAlwaysValidOkCancel implements
		PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private final JSpinner spinner;
	private final PanelMapPrevious zoomableMap;

	public PanelScaleEditor(MapBean map) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 58, 48, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblPixel = new JLabel("Pixel par case:");
		GridBagConstraints gbc_lblPixel = new GridBagConstraints();
		gbc_lblPixel.insets = new Insets(0, 0, 5, 5);
		gbc_lblPixel.gridx = 0;
		gbc_lblPixel.gridy = 0;
		add(lblPixel, gbc_lblPixel);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(map.getScale().getPixels(),
				new Integer(1), null, new Integer(5)));

		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				scaleChange();
			}
		});
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 0;
		add(spinner, gbc_spinner);

		zoomableMap = new PanelMapPrevious(map);
		PanelMoveZoom<PanelMapPrevious> panelPreviousZoom = new PanelMoveZoom<PanelMapPrevious>(
				zoomableMap);

		MoveZoomListener listener = new MoveZoomListener(panelPreviousZoom);
		zoomableMap.addMouseListener(listener);
		zoomableMap.addMouseMotionListener(listener);
		zoomableMap.addMouseWheelListener(listener);

		GridBagConstraints gbc_panelPrevious = new GridBagConstraints();
		gbc_panelPrevious.insets = new Insets(0, 0, 0, 5);
		gbc_panelPrevious.gridwidth = 4;
		gbc_panelPrevious.fill = GridBagConstraints.BOTH;
		gbc_panelPrevious.gridx = 0;
		gbc_panelPrevious.gridy = 2;

		JPanel panelZoom = new PanelZoomEditor(panelPreviousZoom);
		GridBagConstraints gbc_panelZoom = new GridBagConstraints();
		gbc_panelZoom.insets = new Insets(0, 0, 5, 5);
		gbc_panelZoom.fill = GridBagConstraints.BOTH;
		gbc_panelZoom.gridx = 2;
		gbc_panelZoom.gridy = 0;
		add(panelZoom, gbc_panelZoom);

		panelPreviousZoom.setPreferredSize(new Dimension(300, 300));
		panelPreviousZoom
				.setVerticalScrollPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelPreviousZoom
				.setHorizontalScrollPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		add(panelPreviousZoom, gbc_panelPrevious);
	}

	public void scaleChange() {
		int scale = (Integer) spinner.getValue();
		zoomableMap.setScale(new Scale(scale, 1.5));
	}

	public Scale getScale() {
		int scale = (Integer) spinner.getValue();
		return new Scale(scale, 1.5);
	}
}
