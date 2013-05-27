package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.factory.PluginSystem;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.element.PanelCreateMapElement;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.documents.map.MapBean;
import net.alteiar.map.elements.IAction;
import net.alteiar.map.elements.MapElement;

public class DefaultMapListener extends ActionMapListener {

	private final MapBean battle;

	public DefaultMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener, MapBean battle) {
		super(mapInfo, mapListener);
		this.battle = battle;
	}

	private abstract class MapElementAction {
		private final MapEvent event;
		private MapElement element;

		public MapElementAction(MapEvent event) {
			this.event = event;
		}

		public void setElement(MapElement element) {
			this.element = element;
		}

		protected MapEvent getMapEvent() {
			return event;
		}

		protected MapElement getMapElement() {
			return element;
		}

		public abstract void doAction();
	}

	private class MapElementRightClickAction extends MapElementAction {
		public MapElementRightClickAction(MapEvent event) {
			super(event);
		}

		@Override
		public void doAction() {
			MapElement element = getMapElement();
			final MapEvent event = getMapEvent();

			JPopupMenu popup = new JPopupMenu();
			popup.add(buildMoveElement(element));

			popup.add(buildEditElement(event.getMouseEvent(), element));

			popup.addSeparator();
			for (final IAction act : element.getActions()) {
				JMenuItem menu = new JMenuItem(act.getName());
				menu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							act.doAction(event.getMouseEvent().getXOnScreen(),
									event.getMouseEvent().getYOnScreen());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				menu.setEnabled(act.canDoAction());
				popup.add(menu);
			}
			popup.addSeparator();
			popup.add(buildShowHideElement(element));
			popup.addSeparator();

			// need move
			popup.add(buildMenuRotate(element, event.getMapPosition()));
			popup.add(buildMenuRemoveElement(element));
			popup.show(event.getMouseEvent().getComponent(), event
					.getMouseEvent().getX(), event.getMouseEvent().getY());
		}
	}

	private class MapElementLeftClickAction extends MapElementAction {
		public MapElementLeftClickAction(MapEvent event) {
			super(event);
		}

		@Override
		public void doAction() {
			MapElement element = getMapElement();
			mapListener.setCurrentListener(new MoveElementMapListener(
					getMapEditableInfo(), mapListener, element
							.getCenterPosition(), element));
		}
	}

	@Override
	public void mouseClicked(MapEvent event) {
		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			MapElementLeftClickAction action = new MapElementLeftClickAction(
					event);

			selectElementAction(event, action);

		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {

			if (event.getMapElements().size() > 0) {
				MapElementRightClickAction action = new MapElementRightClickAction(
						event);

				selectElementAction(event, action);

			} else {
				JPopupMenu popup = new JPopupMenu();

				popup.add(buildAddElement(event));

				if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
					popup.addSeparator();
					popup.add(buildShowHideMapListener(event.getMapPosition(),
							true));
					popup.add(buildShowHideMapListener(event.getMapPosition(),
							false));

					popup.add(buildRescale(event.getMapPosition()));
				}

				popup.addSeparator();
				popup.add(buildShowGrid());
				popup.add(buildFixGrid());
				popup.add(buildShowDistance());

				popup.show(event.getMouseEvent().getComponent(), event
						.getMouseEvent().getX(), event.getMouseEvent().getY());
			}
		}
	}

	private void selectElementAction(MapEvent event,
			final MapElementAction action) {

		List<MapElement> elements = event.getMapElements();

		if (elements.size() > 1) {
			JPopupMenu menu = new JPopupMenu();

			for (final MapElement mapElement : elements) {
				JMenuItem item = new JMenuItem(mapElement.getNameFormat());
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						action.setElement(mapElement);
						action.doAction();
					}
				});
				menu.add(item);
			}

			menu.show(event.getMouseEvent().getComponent(), event
					.getMouseEvent().getX(), event.getMouseEvent().getY());
		} else if (elements.size() > 0) {
			action.setElement(elements.get(0));
			action.doAction();
		}
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

	private JMenuItem buildShowGrid() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Afficher la grille");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getMapEditableInfo().showGrid(
						!getMapEditableInfo().getShowGrid());
				menuItem.setSelected(getMapEditableInfo().getShowGrid());
			}
		});
		menuItem.setSelected(getMapEditableInfo().getShowGrid());
		return menuItem;
	}

	private JMenuItem buildShowDistance() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Afficher les distances");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getMapEditableInfo().showDistance(
						!getMapEditableInfo().getShowDistance());
				menuItem.setSelected(getMapEditableInfo().getShowDistance());
			}
		});
		menuItem.setSelected(getMapEditableInfo().getShowDistance());
		return menuItem;
	}

	private JMenuItem buildFixGrid() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Fixer à la grille");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getMapEditableInfo()
						.fixGrid(!getMapEditableInfo().getFixGrid());
				menuItem.setSelected(getMapEditableInfo().getFixGrid());
			}
		});
		menuItem.setSelected(getMapEditableInfo().getFixGrid());
		return menuItem;
	}

	private JMenuItem buildRescale(final Point mapPosition) {
		JMenuItem menuItem = new JMenuItem("Changer l'echelle");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapListener.setCurrentListener(new RescaleMapListener(
						getMapEditableInfo(), mapListener));
			}
		});
		return menuItem;
	}

	private JMenuItem buildShowHideMapListener(final Point mapPosition,
			final Boolean isShow) {
		String title = "Afficher";
		if (!isShow) {
			title = "Cacher";
		}
		JMenuItem menuItem = new JMenuItem(title);

		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapListener
						.setCurrentListener(new ShowHidePolygonMapListener(
								getMapEditableInfo(), mapListener, mapPosition,
								isShow));
			}
		});

		return menuItem;
	}

	private JMenuItem buildAddElement(final MapEvent event) {

		JMenuItem addElement = new JMenuItem("Ajouter \u00E9l\u00E9ment");
		addElement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelCreateMapElement.createMapElement(battle, event);
			}
		});

		return addElement;
	}

	private JMenuItem buildMoveElement(final MapElement mapElement) {
		JMenuItem menuItem = new JMenuItem("D\u00E9placer");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapListener.setCurrentListener(new MoveElementMapListener(
						getMapEditableInfo(), mapListener, mapElement
								.getCenterPosition(), mapElement));
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

	private JMenuItem buildMenuRotate(final MapElement element,
			final Point mapPosition) {
		JMenu menu = new JMenu("Rotation");

		JMenuItem rotate = new JMenuItem("Manuelle");
		rotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapListener
						.setCurrentListener(new RotateMapElementListener(
								getMapEditableInfo(), mapListener, element,
								mapPosition));
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

		final PanelMapElementEditor<E> pane = PluginSystem.getInstance()
				.getMapElementEditor(mapElement);

		menuItem.setEnabled(false);
		if (pane != null) {
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					DialogOkCancel<PanelMapElementEditor<E>> dlg = new DialogOkCancel<PanelMapElementEditor<E>>(
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

	private void rotateMapElement(MapElement rotate, Double angle) {
		rotate.setAngle(rotate.getAngle() + angle);
		// TODO rotate.applyRotate();
	}

}
