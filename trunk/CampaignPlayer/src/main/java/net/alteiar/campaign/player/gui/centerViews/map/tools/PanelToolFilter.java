package net.alteiar.campaign.player.gui.centerViews.map.tools;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter.AddRemoveElementToView;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter.ChooseFilterAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter.ShowHideAllAction;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter.ShowHideAreaAction;
import net.alteiar.map.MapBean;
import net.alteiar.map.filter.CharacterMapFilter;
import net.alteiar.map.filter.ManualMapFilter;
import net.alteiar.map.filter.MapFilter;

public class PanelToolFilter extends JToolBar {
	private static final long serialVersionUID = 1L;

	private final MapEditableInfo mapInfo;

	public PanelToolFilter(final MapEditableInfo mapInfo) {
		this.mapInfo = mapInfo;

		this.mapInfo.getMap().addPropertyChangeListener(
				MapBean.PROP_FILTER_PROPERTY, new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						initFilter();
					}
				});
		initFilter();
	}

	private void initFilter() {
		this.removeAll();

		MapFilter filter = CampaignClient.getInstance().getBean(
				mapInfo.getMap().getFilter());

		if (filter instanceof CharacterMapFilter) {
			initAutomatiqueMapFilter();
		} else if (filter instanceof ManualMapFilter) {
			initManualFilter();
		} else {
			// no filter
		}
	}

	private void initManualFilter() {

		JButton showMap = new JButton(new ShowHideAreaAction(mapInfo, true));
		this.add(showMap);

		JButton hideMap = new JButton(new ShowHideAreaAction(mapInfo, false));
		this.add(hideMap);

		JButton showAll = new JButton(new ShowHideAllAction(mapInfo, true));
		this.add(showAll);

		JButton hideAll = new JButton(new ShowHideAllAction(mapInfo, false));
		this.add(hideAll);
	}

	private void initAutomatiqueMapFilter() {
		JButton btnAddElementToView = new JButton(new AddRemoveElementToView(
				mapInfo, true, "Ajouter vision"));

		JButton btnRemoveElementToView = new JButton(
				new AddRemoveElementToView(mapInfo, false, "Enlever vision"));

		final CharacterMapFilter filter = CampaignClient.getInstance().getBean(
				mapInfo.getMap().getFilter());

		JLabel lblVision = new JLabel("Vision max:");
		final JSpinner spinnerVision = new JSpinner(new SpinnerNumberModel(
				filter.getMaxVision().intValue(), 1, Integer.MAX_VALUE, 1));
		spinnerVision.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				Integer val = (Integer) spinnerVision.getValue();
				filter.setMaxVision(val);
			}
		});

		JButton btnLoadFilter = new JButton(new ChooseFilterAction(mapInfo));

		this.add(btnLoadFilter);
		this.addSeparator();
		this.add(btnAddElementToView);
		this.add(btnRemoveElementToView);
		this.addSeparator();
		this.add(lblVision);
		this.add(spinnerVision);

	}
}
