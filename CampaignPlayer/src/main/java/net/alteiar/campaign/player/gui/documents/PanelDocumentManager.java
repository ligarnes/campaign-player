package net.alteiar.campaign.player.gui.documents;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelDocumentManager extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelDocumentManager() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { Double.MIN_VALUE };
		setLayout(gridBagLayout);
	}

}
