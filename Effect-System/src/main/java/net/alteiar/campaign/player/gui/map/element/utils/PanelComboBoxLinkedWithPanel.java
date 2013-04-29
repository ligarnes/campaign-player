package net.alteiar.campaign.player.gui.map.element.utils;

import java.util.ArrayList;

import javax.swing.JPanel;

public class PanelComboBoxLinkedWithPanel extends PanelLinkedComboBox {
	private static final long serialVersionUID = 1L;

	private ArrayList<ArrayList<JPanel>> modelList;
	private ArrayList<Integer> currentPans;

	public PanelComboBoxLinkedWithPanel(ArrayList<ArrayList<String[]>> list,
			ArrayList<ArrayList<JPanel>> panelList) {
		super(list);
		modelList = panelList;
		currentPans = new ArrayList<Integer>();
		for (int i = 0; i < modelList.size(); i++) {
			for (int j = 0; j < modelList.get(i).size(); j++) {
				JPanel pan = panelList.get(i).get(j);
				if (j == 0) {
					pan.setVisible(true);
					currentPans.add(i, j);
				} else {
					pan.setVisible(false);
				}
				add(pan);
			}
		}
	}

	public ArrayList<ArrayList<JPanel>> getModelList() {
		return modelList;
	}

	public ArrayList<JPanel> getModelListChoices(int indexPanel) {
		return modelList.get(indexPanel);
	}

	public JPanel getModelListChoice(int indexPanel, int indexChoice) {
		return modelList.get(indexPanel).get(indexChoice);
	}

	public void setmodelList(ArrayList<ArrayList<JPanel>> modelList) {
		this.modelList = modelList;
	}

	public void getModelListChoices(int indexPanel,
			ArrayList<JPanel> modelListChoices) {
		modelList.set(indexPanel, modelListChoices);
	}

	public void getModelListChoice(int indexPanel, int indexChoice,
			JPanel choice) {
		modelList.get(indexPanel).set(indexChoice, choice);
	}

	public ArrayList<Integer> getCurrentPans() {
		return currentPans;
	}

	public Integer getCurrentPan(int indexPanel) {
		return currentPans.get(indexPanel);
	}

	public void setCurrentPans(ArrayList<Integer> pans) {
		this.currentPans = pans;
	}

	public void getCurrentPan(int indexPanel, Integer pan) {
		currentPans.set(indexPanel, pan);
	}

	public void setModelToPanel(int indexPanel, int indexModelPanel) {
		modelList.get(indexPanel).get(currentPans.get(indexPanel))
				.setVisible(false);
		modelList.get(indexPanel).get(indexModelPanel).setVisible(true);
		currentPans.set(indexPanel, indexModelPanel);
	}

}
