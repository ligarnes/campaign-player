package net.alteiar.campaign.player.plugin;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class PluginCellRenderer implements ListCellRenderer<PluginInfo> {

	private final DefaultListCellRenderer renderer;

	public PluginCellRenderer() {
		renderer = new DefaultListCellRenderer();
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends PluginInfo> list,
			PluginInfo value, int index, boolean isSelected, boolean cellHasFocus) {

		String cellText = "";
		if (value != null) {
			cellText += value.getName();
		}
		return renderer.getListCellRendererComponent(list, cellText, index,
				isSelected, cellHasFocus);
	}

}