package net.alteiar.campaign.player.gui.documents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

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
		dlg.pack();
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

		panelWest = new JPanel(new GridLayout(getBuilders().size(), 1));

		this.add(panelWest, BorderLayout.WEST);

		builder = getBuilders().get(0);
		panelCenter = new JPanel();
		panelCenter.add(builder);
		this.add(panelCenter, BorderLayout.CENTER);
	}

	private void refreshElements() {
		panelWest.removeAll();
		int maxWidth = 50;
		int maxHeight = 50;
		for (final PanelDocumentBuilder panel : getBuilders()) {
			Dimension dim = panel.getPreferredSize();

			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxHeight, dim.height);

			JButton select = new JButton(panel.getDocumentName());
			select.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectBuilder(panel);
				}
			});
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
