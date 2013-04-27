package net.alteiar.effectBean.gui.effect.trigger;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.effectBean.xml.EffectChoiceListGenerator;
import net.alteiar.shared.UniqueID;
import net.alteiar.trigger.TriggerBean;

public class TriggerEditor extends PanelMapElementEditor<TriggerBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JComboBox listEffect;
	private final JButton addButton;
	private final ArrayList<Effect> effets;
	private final ArrayList<Integer> selectedEffects;
	private Integer selectedEffect;

	public TriggerEditor(TriggerBean mapElement) throws ClassNotFoundException {
		super(mapElement);
		this.add(new JLabel("Lier des effets au déclencheur:"),
				BorderLayout.NORTH);
		selectedEffect = 1;
		Map map = CampaignClient.getInstance().getBean(mapElement.getMapId());
		HashSet<UniqueID> elements = map.getElements();
		effets = new ArrayList<Effect>();
		selectedEffects = new ArrayList<Integer>();
		for (UniqueID element : elements) {
			BasicBeans effect = CampaignClient.getInstance().getBean(element);
			if (Beans.isInstanceOf(effect, Effect.class)) {
				effets.add((Effect) effect);
			}
		}
		effets.get(selectedEffect).setSelected(true);
		String[] effectList = new String[effets.size()];
		EffectChoiceListGenerator eGenerator = new EffectChoiceListGenerator();
		ArrayList<Class<?>> classList = eGenerator
				.getEffectClasses(EffectChoiceListGenerator.path);
		ArrayList<String> effectNameList = eGenerator
				.getEffectName(EffectChoiceListGenerator.path);
		for (int j = 0; j < effets.size(); j++) {
			for (int i = 0; i < classList.size(); i++) {
				if (Beans.isInstanceOf(effets.get(j), classList.get(i))) {
					effectList[j] = effectNameList.get(i);
				}
			}
		}

		listEffect = new JComboBox(effectList);
		listEffect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				effets.get(selectedEffect).setSelected(false);
				JComboBox temp = (JComboBox) e.getSource();
				selectedEffect = temp.getSelectedIndex();
				effets.get(selectedEffect).setSelected(true);
			}

		});
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				selectedEffects.add(selectedEffect);
			}

		});
		this.add(new JLabel("Ajouter un effet au déclencheur:"),
				BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.add(buildRow("Choix de l'effet: ", listEffect));
		panelCenter
				.add(buildRow("Ajout de l'effet au déclencheur: ", addButton));
		this.add(panelCenter, BorderLayout.CENTER);

	}

	private static JPanel buildRow(String name, JPanel panel) {
		JPanel container = new JPanel(new FlowLayout());
		container.add(new JLabel(name));
		container.add(panel);
		return container;
	}

	private static JPanel buildRow(String name, JComponent panel) {
		JPanel container = new JPanel(new FlowLayout());
		container.add(new JLabel(name));
		container.add(panel);
		return container;
	}

	public Boolean isDataValid() {
		return true;
	}

	public String getInvalidMessage() {
		return "";
	}

	@Override
	public void applyModification() {

		TriggerBean trigger = getMapElement();
		EffectSuite effect = (EffectSuite) trigger.getEffect();
		Set<Integer> set = new HashSet();
		set.addAll(selectedEffects);
		ArrayList<Integer> distinctList = new ArrayList(set);
		for (Integer indexEffect : distinctList) {
			effect.addEffect(effets.get(indexEffect));
		}
	}

}
