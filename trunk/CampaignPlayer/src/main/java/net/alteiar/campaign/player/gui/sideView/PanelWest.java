package net.alteiar.campaign.player.gui.sideView;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.ApplicationView;
import net.alteiar.panel.PanelShowHide;

public class PanelWest extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final Integer WIDTH = 250;

	private final PanelViewSelector viewSelector;
	private final JPanel inside;

	public PanelWest(Collection<ApplicationView> views) {
		viewSelector = new PanelViewSelector(views, WIDTH);

		inside = new JPanel();
		inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));

		this.add(inside);
	}

	public void updateSelectedView(ApplicationView appView) {
		inside.removeAll();

		inside.add(viewSelector);

		for (final SideView view : appView.getSideViews()) {
			final PanelShowHide showHide = new PanelShowHide(view.getName(),
					view.getView(), WIDTH, view.isShow());

			showHide.addPropertyChangeListener(
					PanelShowHide.PROP_SHOW_PROPERTY,
					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							view.setIsShow(showHide.getShow());
						}
					});
			inside.add(showHide);
			inside.add(Box.createRigidArea(new Dimension(0, 3)));
		}
	}
}
