package net.alteiar.campaign.player.gui.documents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.alteiar.campaign.player.gui.factory.PluginSystem;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.PanelOkCancel;

public class PanelCreateDocument extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private static PanelCreateDocument documentBuilder = new PanelCreateDocument();

	public static void createDocument() {
		DialogOkCancel<PanelCreateDocument> dlg = new DialogOkCancel<PanelCreateDocument>(
				null, "Créer un document", true, documentBuilder);

		dlg.getMainPanel().refreshElements();
		dlg.setOkText("Créer");
		dlg.setCancelText("Annuler");
		dlg.setLocationRelativeTo(null);
		// dlg.pack();
		dlg.setMaximumSize(new Dimension(600, 450));
		dlg.setMinimumSize(new Dimension(600, 450));
		dlg.setPreferredSize(new Dimension(600, 450));
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			dlg.getMainPanel().buildElement();
		}
	}

	public static ArrayList<PanelDocumentBuilder> getBuilders() {
		return PluginSystem.getInstance().getGuiDocumentFactory();
	}

	private final JPanel panelWest;
	private final JPanel panelCenter;
	private PanelDocumentBuilder builder;

	private PanelCreateDocument() {
		super(new BorderLayout());

		panelWest = new JPanel();
		panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.PAGE_AXIS));

		this.add(panelWest, BorderLayout.WEST);

		builder = getBuilders().get(0);

		panelCenter = new JPanel();
		panelCenter.add(builder);
		this.add(panelCenter, BorderLayout.CENTER);
	}

	private class ElementBuilder extends JPanel {
		private static final long serialVersionUID = 1L;

		private final JLabel lblTitle;

		public ElementBuilder(String title) {
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] { 0, 0 };
			gridBagLayout.rowHeights = new int[] { 0, 0 };
			gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
			setLayout(gridBagLayout);

			lblTitle = new JLabel(title);
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.fill = GridBagConstraints.BOTH;
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			add(lblTitle, gbc_lblTitle);
		}
	}

	private void refreshElements() {
		panelWest.removeAll();
		int maxWidth = 50;
		int maxHeight = 50;
		for (final PanelDocumentBuilder panel : getBuilders()) {
			Dimension dim = panel.getPreferredSize();

			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxHeight, dim.height);

			ElementBuilder select = new ElementBuilder(panel.getDocumentName());
			select.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selectBuilder(panel);
				}
			});
			select.setPreferredSize(new Dimension(80, 80));
			select.setMaximumSize(new Dimension(80, 80));
			select.setMinimumSize(new Dimension(80, 80));
			panelWest.add(select);
		}

		this.setPreferredSize(new Dimension(maxWidth, maxHeight));
	}

	private void selectBuilder(PanelDocumentBuilder builder) {
		panelCenter.removeAll();
		this.builder = builder;
		panelCenter.add(this.builder);
		this.revalidate();
		this.repaint();
	}

	public void buildElement() {
		builder.buildDocument();
	}

	@Override
	public Boolean isDataValid() {
		return builder.isDataValid();
	}

	@Override
	public String getInvalidMessage() {
		return builder.getInvalidMessage();
	}
}