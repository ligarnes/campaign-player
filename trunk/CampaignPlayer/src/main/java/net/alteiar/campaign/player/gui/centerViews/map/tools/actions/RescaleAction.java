package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import java.awt.event.ActionEvent;

import net.alteiar.beans.map.Scale;
import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.scale.PanelScaleEditor;
import net.alteiar.dialog.DialogOkCancel;

public class RescaleAction extends MapAction {
	private static final long serialVersionUID = 1L;

	public RescaleAction(MapEditableInfo info) {
		super(info);
		putValue(NAME, "Changer l'echelle");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DialogOkCancel<PanelScaleEditor> dlg = new DialogOkCancel<PanelScaleEditor>(
				MainFrame.FRAME, "Changer l'échelle", true,
				new PanelScaleEditor(getMapInfo().getMap()));

		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			Scale scale = dlg.getMainPanel().getScale();
			this.getMapInfo().getMap().setScale(scale);
		}
	}
}
