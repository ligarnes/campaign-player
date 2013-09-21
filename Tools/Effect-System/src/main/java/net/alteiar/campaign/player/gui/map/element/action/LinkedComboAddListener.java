package net.alteiar.campaign.player.gui.map.element.action;

import net.alteiar.campaign.player.gui.map.element.utils.PanelLinkedComboBox;

public abstract class LinkedComboAddListener {
	
	protected final PanelLinkedComboBox linkedComboBox;
	
	public LinkedComboAddListener(PanelLinkedComboBox linkedComboBox)
	{
		this.linkedComboBox=linkedComboBox;
	}
	
	public abstract void addListener();
}
