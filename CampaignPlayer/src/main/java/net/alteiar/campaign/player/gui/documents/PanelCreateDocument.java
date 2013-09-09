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

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.documents.BeanDocument;
import net.alteiar.newversion.shared.bean.BasicBean;

public class PanelCreateDocument extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private static PanelCreateDocument documentBuilder = new PanelCreateDocument();

	public static void createDocument(final BeanDirectory parent) {
		DialogOkCancel<PanelCreateDocument> dlg = new DialogOkCancel<PanelCreateDocument>(
				null, "Cr\u00E9er un document", true, documentBuilder);

		dlg.getMainPanel().refreshElements();
		dlg.setOkText("Cr\u00E9er");
		dlg.setCancelText("Annuler");
		dlg.setLocationRelativeTo(null);
		// dlg.pack();
		dlg.setMaximumSize(new Dimension(600, 470));
		dlg.setMinimumSize(new Dimension(600, 470));
		dlg.setPreferredSize(new Dimension(600, 470));
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			try {
				dlg.getMainPanel().buildElement(parent);
			} catch (Exception ex) {
				ExceptionTool.showError(ex);
			}
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
		builder.reset();

		panelCenter = new JPanel(new BorderLayout());
		panelCenter.add(builder, BorderLayout.CENTER);
		this.add(panelCenter, BorderLayout.CENTER);
	}

	private void refreshElements() {
		builder.reset();

		panelWest.removeAll();
		int maxWidth = 50;
		int maxHeight = 50;
		for (final PanelDocumentBuilder panel : getBuilders()) {
			Dimension dim = panel.getPreferredSize();

			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxHeight, dim.height);

			ElementBuilder select = new ElementBuilder(
					panel.getDocumentBuilderName());
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
		builder.reset();
		panelCenter.add(this.builder);
		this.revalidate();
		this.repaint();
	}

	public void buildElement(BeanDirectory dir) {
		BasicBean bean = builder.buildDocument();

		if (bean != null) {
			BeanDocument doc = new BeanDocument(dir, builder.getDocumentName(),
					builder.getDocumentType(), bean);

			// TODO remove setPublic only for test
			doc.setPublic(true);
			CampaignClient.getInstance().addBean(doc);
		}
	}

	@Override
	public Boolean isDataValid() {
		return builder.isDataValid();
	}

	@Override
	public String getInvalidMessage() {
		return builder.getInvalidMessage();
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
}
