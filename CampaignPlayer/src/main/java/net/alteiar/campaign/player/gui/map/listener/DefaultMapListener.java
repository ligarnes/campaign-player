package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

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
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.map.elements.MapElement;

public class DefaultMapListener extends ActionMapListener {

	private final Battle battle;

	public DefaultMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener, Battle battle) {
		super(mapInfo, mapListener);
		this.battle = battle;
	}

	@Override
	public void mouseClicked(MapEvent event) {
		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			if (event.getMapElement() != null) {
				mapListener.setCurrentListener(new MoveElementMapListener(
						getMapEditableInfo(), mapListener, event
								.getMapElement().getCenterPosition(), event
								.getMapElement()));
			}
		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			JPopupMenu popup = new JPopupMenu();
			if (event.getMapElement() != null) {
				popup.add(buildMoveElement(event.getMapPosition(),
						event.getMapElement()));

				popup.add(buildEditElement(event.getMouseEvent(),
						event.getMapElement()));
				/*
				 * List<IAction> actionsAvaible = event.getMapElement()
				 * .getActions(); if (actionsAvaible.size() > 0) {
				 * popup.addSeparator(); for (final IAction action :
				 * actionsAvaible) { JMenuItem menu = new
				 * JMenuItem(action.getName()); menu.addActionListener(new
				 * ActionListener() {
				 * 
				 * @Override public void actionPerformed(ActionEvent arg0) { try
				 * { action.doAction(); } catch (Exception e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); } } });
				 * menu.setEnabled(action.canDoAction()); popup.add(menu); } }
				 */

				popup.addSeparator();
				popup.add(buildShowHideElement(event.getMapElement()));
				popup.addSeparator();

				// need move
				popup.add(buildMenuRotate(event.getMapElement(),
						event.getMapPosition()));
				popup.add(buildMenuRemoveElement(event));
			} else {

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
			}
			popup.show(event.getMouseEvent().getComponent(), event
					.getMouseEvent().getX(), event.getMouseEvent().getY());
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

		menuItem.setSelected(mapElement.isHiddenForPlayer());
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

		JMenuItem addElement = new JMenuItem("Ajouter élément");
		addElement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelCreateMapElement.createMapElement(battle, event);
			}
		});

		return addElement;
	}

	private JMenuItem buildMoveElement(final Point mapPosition,
			final MapElement mapElement) {
		JMenuItem menuItem = new JMenuItem("Déplacer");
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

	private JMenuItem buildMenuRemoveElement(final MapEvent event) {
		String title = "element";

		JMenuItem removeElement = new JMenuItem("Supprimer " + title);
		removeElement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getMapEditableInfo().removeElement(event.getMapElement());
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
