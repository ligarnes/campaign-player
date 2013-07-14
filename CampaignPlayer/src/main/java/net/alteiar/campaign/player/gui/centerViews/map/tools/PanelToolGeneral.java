package net.alteiar.campaign.player.gui.centerViews.map.tools;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.AddElementAction;
import net.alteiar.zoom.PanelMoveZoom;
import net.alteiar.zoom.PanelZoomEditor;

public class PanelToolGeneral extends JToolBar {
	private static final long serialVersionUID = 1L;

	public PanelToolGeneral(final MapEditableInfo mapInfo,
			PanelMoveZoom<?> panelZoom) {
		JButton addElement = new JButton(new AddElementAction(mapInfo));
		addElement.setHideActionText(true);

		this.add(addElement);

		this.addSeparator();
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(new PanelZoomEditor(panelZoom));
		this.add(panel);
	}
}
