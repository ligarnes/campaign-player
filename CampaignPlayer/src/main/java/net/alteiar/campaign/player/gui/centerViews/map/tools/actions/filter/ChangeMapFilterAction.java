package net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.Threads;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.MapAction;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.list.PanelSelectList;
import net.alteiar.map.MapBean;
import net.alteiar.map.filter.CharacterMapFilter;
import net.alteiar.map.filter.ManualMapFilter;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.map.filter.NoMapFilter;
import net.alteiar.thread.MyRunnable;

public class ChangeMapFilterAction extends MapAction {
	private static final long serialVersionUID = 1L;

	public ChangeMapFilterAction(MapEditableInfo info) {
		super(info);

		// putValue(SMALL_ICON, Helpers.getIcon(icon));
		putValue(NAME, "Changer le type filtre");
	}

	private static final String MANUAL_MAP_FILTER = "Filtre manuel";
	private static final String AUTOMATIC_MAP_FILTER = "Filtre automatique";
	private static final String NO_MAP_FILTER = "Aucun filtre";

	private class FilterAdapter {
		private final String name;

		public FilterAdapter(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// select filter
		ArrayList<FilterAdapter> adapters = new ArrayList<FilterAdapter>();
		adapters.add(new FilterAdapter(MANUAL_MAP_FILTER));
		adapters.add(new FilterAdapter(AUTOMATIC_MAP_FILTER));
		adapters.add(new FilterAdapter(NO_MAP_FILTER));

		final PanelSelectList<FilterAdapter> panel = new PanelSelectList<FilterAdapter>(
				adapters, false);

		DialogOkCancel<PanelSelectList<FilterAdapter>> dlg = new DialogOkCancel<PanelSelectList<FilterAdapter>>(
				null, "Choisir le type de filtre", true, panel);

		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {

			Threads.execute(new MyRunnable() {

				@Override
				public void run() {
					MapBean map = getMapInfo().getMap();

					MapFilter newFilter = null;

					FilterAdapter adapter = panel.getSelectedValue();

					if (MANUAL_MAP_FILTER.equals(adapter.getName())) {
						newFilter = new ManualMapFilter(map.getId());
					} else if (AUTOMATIC_MAP_FILTER.equals(adapter.getName())) {
						newFilter = new CharacterMapFilter(map);
					} else if (NO_MAP_FILTER.equals(adapter.getName())) {
						newFilter = new NoMapFilter();
					}

					CampaignClient.getInstance().addBean(newFilter);
					// wait to be sure the bean is received
					CampaignClient.getInstance().getBean(newFilter.getId(),
							10000L);

					// set the new filter
					map.setFilter(newFilter.getId());

					// delete the old filter
					CampaignClient.getInstance().removeBean(map.getFilter());
				}

				@Override
				public String getTaskName() {
					return "create new map filter";
				}
			});
		}
	}
}
