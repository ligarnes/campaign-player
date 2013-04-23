package net.alteiar.effectBean.gui;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.utils.PanelElementSize;
import net.alteiar.campaign.player.gui.map.element.utils.PanelSelectColor;
import net.alteiar.map.elements.MapElement;
import net.alteiar.trigger.TriggerBean;

public class PanelTriggerBuilder extends PanelMapElementBuilder{
	private static final long serialVersionUID = 1L;

	private final PanelSelectColor panelColor;
	private final PanelElementSize panelSize;
	
	public PanelTriggerBuilder() {
		panelColor = new PanelSelectColor();
		panelSize = new PanelElementSize(1);

		this.setLayout(new BorderLayout());
		this.add(new JLabel("Ajouter un déclencheur"), BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.add(buildRow("Couleur: ", panelColor));
		panelCenter.add(buildRow("Rayon: ", panelSize));

		
		
		this.add(panelCenter, BorderLayout.CENTER);
	}
	
	
	@Override
	public Boolean isAvailable() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getElementDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MapElement buildMapElement(Point position) {
		// TODO Auto-generated method stub
		return null;
	}
}
