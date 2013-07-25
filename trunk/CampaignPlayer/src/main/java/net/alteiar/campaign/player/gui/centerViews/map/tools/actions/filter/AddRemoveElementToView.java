package net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.MapAction;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.dialog.list.PanelSelectList;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.CharacterMapFilter;
import net.alteiar.shared.UniqueID;

public class AddRemoveElementToView extends MapAction {
	private static final long serialVersionUID = 1L;

	private final MapElement element;
	private final Boolean addToView;

	public AddRemoveElementToView(MapEditableInfo info, Boolean addToView) {
		this(info, null, addToView);
	}

	public AddRemoveElementToView(MapEditableInfo info, MapElement element,
			Boolean addToView) {
		this(info, element, addToView, "Source de vision");
	}

	public AddRemoveElementToView(MapEditableInfo info, Boolean addToView,
			String name) {
		this(info, null, addToView, name);
	}

	public AddRemoveElementToView(MapEditableInfo info, MapElement element,
			Boolean addToView, String name) {
		super(info);

		this.element = element;
		this.addToView = addToView;

		/*
		 * String name = "Enlever vision"; if (addToView) { name =
		 * "Ajouter vision"; }
		 */

		putValue(NAME, name);
		// putValue(SMALL_ICON, Helpers.getIcon(icon));
	}

	private Collection<UniqueID> getVisionElements() {
		CharacterMapFilter filter = CampaignClient.getInstance().getBean(
				getMapInfo().getMap().getFilter());
		return filter.getViewer();
	}

	private Collection<UniqueID> getNonVisionElements() {
		HashSet<UniqueID> values = getMapInfo().getMap().getElements();
		values.removeAll(getVisionElements());
		return values;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (element != null) {
			doAction(element.getId());
		} else {
			Collection<UniqueID> mapElements = null;
			String title = "";

			if (addToView) {
				mapElements = getNonVisionElements();
				title = "Ajouter visions";
			} else {
				mapElements = getVisionElements();
				title = "Enlever visions";
			}

			ArrayList<ElementViewAdapter> adapters = new ArrayList<ElementViewAdapter>();

			for (UniqueID element : mapElements) {
				adapters.add(new ElementViewAdapter(element));
			}

			final PanelSelectList<ElementViewAdapter> panel = new PanelSelectList<ElementViewAdapter>(
					adapters, true);

			DialogOkCancel<PanelSelectList<ElementViewAdapter>> dlg = new DialogOkCancel<PanelSelectList<ElementViewAdapter>>(
					null, title, true, panel);
			dlg.setLocationRelativeTo(null);
			dlg.pack();
			dlg.setVisible(true);

			if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
				List<ElementViewAdapter> selecteds = panel.getSelectedValues();
				for (ElementViewAdapter elementViewAdapter : selecteds) {
					doAction(elementViewAdapter.getElementId());
				}
			}
		}
	}

	private void doAction(UniqueID element) {
		CharacterMapFilter filter = CampaignClient.getInstance().getBean(
				getMapInfo().getMap().getFilter());
		if (addToView) {
			filter.addElementIdView(element);
		} else {
			filter.removeElementView(element);
		}
	}

	private class ElementViewAdapter {
		private final UniqueID elementId;
		private final String name;

		public ElementViewAdapter(UniqueID id) {
			super();
			this.elementId = id;

			MapElement element = CampaignClient.getInstance().getBean(id);
			this.name = element.getNameFormat();
		}

		public UniqueID getElementId() {
			return elementId;
		}

		@Override
		public String toString() {
			return name;
		}
	}

}
