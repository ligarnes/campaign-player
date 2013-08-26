package net.alteiar.campaign.player.gui.centerViews.map.listener.mapElement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.centerViews.map.tools.actions.filter.AddRemoveElementToView;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.map.elements.IAction;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.CharacterMapFilter;
import net.alteiar.map.filter.MapFilter;

import org.apache.log4j.Logger;

public class DefaultState extends MapElementListenerState {

	public DefaultState(MapEditableInfo info, MapElementListener listener) {
		super(info, listener);
	}

	@Override
	public void mouseClicked(final MouseEvent event, MapElement element) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			move(element);
		} else if (SwingUtilities.isRightMouseButton(event)) {
			JPopupMenu popup = new JPopupMenu();
			popup.add(buildMoveElement(element));

			popup.add(buildEditElement(event, element));

			popup.addSeparator();
			for (final IAction act : element.getActions()) {
				JMenuItem menu = new JMenuItem(act.getName());
				menu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							act.doAction(event.getXOnScreen(),
									event.getYOnScreen());
						} catch (Exception e1) {
							Logger.getLogger(getClass()).warn(
									"Une erreur s'est produite lors de l'exécution de l'action "
											+ act.getName(), e1);
						}
					}
				});
				menu.setEnabled(act.canDoAction());
				popup.add(menu);
			}
			popup.addSeparator();
			popup.add(buildShowHideElement(element));

			// depend on filter
			MapFilter filter = CampaignClient.getInstance().getBean(
					getMapEditableInfo().getMap().getFilter());
			if (filter instanceof CharacterMapFilter) {
				Boolean haveVision = ((CharacterMapFilter) filter).getViewer()
						.contains(element.getId());

				JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
						new AddRemoveElementToView(getMapEditableInfo(),
								element, !haveVision));
				menuItem.setSelected(haveVision);
				popup.add(menuItem);
			}
			popup.addSeparator();

			// need move
			// popup.add(buildMenuRotate(element));
			popup.add(buildMenuRemoveElement(element));
			popup.show(event.getComponent(), event.getXOnScreen(),
					event.getYOnScreen());

			popup.setLocation(event.getXOnScreen(), event.getYOnScreen());
		}
	}

	private JMenuItem buildMoveElement(final MapElement mapElement) {
		JMenuItem menuItem = new JMenuItem("D\u00E9placer");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				move(mapElement);
			}
		});
		return menuItem;
	}

	private JMenuItem buildMenuRemoveElement(final MapElement element) {
		String title = "element";

		JMenuItem removeElement = new JMenuItem("Supprimer " + title);
		removeElement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getMapEditableInfo().removeElement(element);
			}
		});

		return removeElement;
	}

	private JMenuItem buildShowHideElement(final MapElement mapElement) {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Afficher le personnage aux joueurs");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapElement.setHiddenForPlayer(!mapElement.isHiddenForPlayer());
				menuItem.setSelected(mapElement.isHiddenForPlayer());
			}
		});

		menuItem.setSelected(!mapElement.isHiddenForPlayer());
		return menuItem;
	}

	private JMenuItem buildMenuRotate(final MapElement element) {
		JMenu menu = new JMenu("Rotation");

		JMenuItem rotate = new JMenuItem("Manuelle");
		rotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setState(new RotateState(getMapEditableInfo(), getListener(),
						element));
			}
		});

		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				element.setAngle(0.0);
				// TODO element.applyRotate();
			}
		});

		JMenuItem menu45 = new JMenuItem("45 degres");
		menu45.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rotateMapElement(element, 45.0);
			}
		});
		JMenuItem menu90 = new JMenuItem("90 degres");
		menu90.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rotateMapElement(element, 90.0);
			}
		});
		JMenuItem menu180 = new JMenuItem("180 degres");
		menu180.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rotateMapElement(element, 180.0);
			}
		});

		menu.add(reset);
		menu.add(menu45);
		menu.add(menu90);
		menu.add(menu180);
		menu.add(rotate);

		return menu;
	}

	public <E extends MapElement> JMenuItem buildEditElement(
			final MouseEvent event, final E mapElement) {
		JMenuItem menuItem = new JMenuItem("Editer");

		final PanelMapElementEditor pane = PluginSystem.getInstance()
				.getMapElementEditor(mapElement);

		menuItem.setEnabled(false);
		if (pane != null) {
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					DialogOkCancel<PanelMapElementEditor> dlg = new DialogOkCancel<PanelMapElementEditor>(
							null, "Editer", true, pane);
					dlg.setOkText("Editer");
					dlg.setCancelText("Annuler");

					dlg.pack();
					dlg.setLocationRelativeTo(null);
					dlg.setLocation(event.getLocationOnScreen());
					dlg.setVisible(true);

					if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
						dlg.getMainPanel().applyModification();
					}
				}
			});

			menuItem.setEnabled(true);
		}
		return menuItem;
	}

	private void move(MapElement mapElement) {
		setState(new MoveState(getMapEditableInfo(), getListener(), mapElement));
	}

	private void rotateMapElement(MapElement rotate, Double angle) {
		rotate.setAngle(rotate.getAngle() + angle);
		// TODO rotate.applyRotate();
	}
}
