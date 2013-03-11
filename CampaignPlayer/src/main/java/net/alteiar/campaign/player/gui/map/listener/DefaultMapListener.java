package net.alteiar.campaign.player.gui.map.listener;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.element.PanelCreateMapElement;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.campaign.player.gui.tools.PanelAlwaysValidOkCancel;
import net.alteiar.client.CampaignClient;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.element.MapElementClient;
import net.alteiar.server.document.map.element.character.MapElementCharacterClient;

public class DefaultMapListener extends ActionMapListener {

	private final BattleClient battle;
	private final MapEditableInfo mapInfo;

	public DefaultMapListener(GlobalMapListener mapListener,
			BattleClient battle, MapEditableInfo mapInfo) {
		super(mapListener);
		this.battle = battle;
		this.mapInfo = mapInfo;
	}

	@Override
	public void mouseClicked(MapEvent event) {
		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			if (event.getMapElement() != null) {
				mapListener.setCurrentListener(new MoveElementMapListener(
						mapListener, mapInfo, event.getMapElement()
								.getCenterPosition(), event.getMapElement()));
			}
		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			JPopupMenu popup = new JPopupMenu();
			if (event.getMapElement() != null) {
				popup.add(buildMoveElement(event.getMapPosition(),
						event.getMapElement()));

				if (event.getCharacter() != null) {
					// add specifc to character
					popup.addSeparator();
					popup.add(buildMenuDamage(event.getMouseEvent(),
							event.getCharacter(), true));
					popup.add(buildMenuDamage(event.getMouseEvent(),
							event.getCharacter(), false));
				}
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

	private JMenuItem buildShowHideElement(final MapElementClient<?> mapElement) {

		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Afficher le personnage aux joueurs");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapElement.setIsHidden(!mapElement.getIsHidden());
				menuItem.setSelected(mapElement.getIsHidden());
			}
		});

		menuItem.setSelected(mapElement.getIsHidden());
		return menuItem;
	}

	private JMenuItem buildShowGrid() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Afficher la grille");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapInfo.showGrid(!mapInfo.getShowGrid());
				menuItem.setSelected(mapInfo.getShowGrid());
			}
		});
		menuItem.setSelected(mapInfo.getShowGrid());
		return menuItem;
	}

	private JMenuItem buildShowDistance() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Afficher les distances");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapInfo.showDistance(!mapInfo.getShowDistance());
				menuItem.setSelected(mapInfo.getShowDistance());
			}
		});
		menuItem.setSelected(mapInfo.getShowDistance());
		return menuItem;
	}

	private JMenuItem buildFixGrid() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(
				"Fixer à la grille");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapInfo.fixGrid(!mapInfo.getFixGrid());
				menuItem.setSelected(mapInfo.getFixGrid());
			}
		});
		menuItem.setSelected(mapInfo.getFixGrid());
		return menuItem;
	}

	private JMenuItem buildRescale(final Point mapPosition) {
		JMenuItem menuItem = new JMenuItem("Changer l'echelle");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapListener.setCurrentListener(new RescaleMapListener(
						mapListener, mapInfo));
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
				mapListener.setCurrentListener(new ShowHidePolygonMapListener(
						mapListener, mapInfo, mapPosition, isShow));
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
			final MapElementClient<?> mapElement) {
		JMenuItem menuItem = new JMenuItem("Deplacer");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapListener.setCurrentListener(new MoveElementMapListener(
						mapListener, mapInfo, mapElement.getCenterPosition(),
						mapElement));
			}
		});
		return menuItem;
	}

	private JMenuItem buildMenuRemoveElement(final MapEvent event) {
		String title = "element";
		if (event.getCharacter() != null) {
			title = "personnage";
		}

		JMenuItem removeElement = new JMenuItem("Supprimer " + title);
		removeElement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (event.getCharacter() != null) {
					// TODO fixme mapInfo.removeCharacter(event.getCharacter());
				} else {
					mapInfo.removeElement(event.getMapElementClient());
				}
			}
		});

		return removeElement;
	}

	private JMenuItem buildMenuRotate(final MapElementClient<?> element,
			final Point mapPosition) {
		String title = "element";
		// if (MapElementUtils.isCharacter(element)) {
		// title = "personnage";
		// }

		JMenu menu = new JMenu("Rotation " + title);

		JMenuItem rotate = new JMenuItem("Manuelle");
		rotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapListener.setCurrentListener(new RotateMapListener(
						mapListener, element, mapPosition));
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

	private JMenuItem buildMenuDamage(final MouseEvent orgEvent,
			final MapElementCharacterClient character, final Boolean isDamage) {

		String title = "Dégât";
		if (!isDamage) {
			title = "Soins";
		}

		JMenuItem menuItem = new JMenuItem(title);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDamage(orgEvent, character, isDamage);
			}
		});

		return menuItem;
	}

	private void doDamage(MouseEvent orgEvent,
			MapElementCharacterClient characterCombat, Boolean isDamage) {
		final JTextField textFieldDegat = new JTextField(5);

		String builder = "dégâts";
		if (!isDamage) {
			builder = "soins";
		}
		final String title = builder.toString();

		PanelAlwaysValidOkCancel panelDegat = new PanelAlwaysValidOkCancel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean isDataValid() {
				Boolean isValid = true;
				try {
					Integer.valueOf(textFieldDegat.getText());
				} catch (NumberFormatException ex) {
					isValid = false;
				}
				return isValid;
			}

			@Override
			public String getInvalidMessage() {
				return "les " + title + " doivents être des chiffres";
			}
		};
		panelDegat.setLayout(new FlowLayout());
		panelDegat.add(textFieldDegat);
		DialogOkCancel<PanelAlwaysValidOkCancel> dialog = new DialogOkCancel<PanelAlwaysValidOkCancel>(
				null, title, true, panelDegat);
		dialog.setLocation(orgEvent.getXOnScreen() - (dialog.getWidth() / 2),
				orgEvent.getYOnScreen() - (dialog.getHeight() / 2));
		dialog.setVisible(true);

		if (dialog.getReturnStatus() == DialogOkCancel.RET_OK) {
			Integer degat = Integer.valueOf(textFieldDegat.getText());

			CharacterClient characterSheet = characterCombat.getCharacter();
			if (isDamage) {
				characterSheet.setCurrentHp(characterSheet.getCurrentHp()
						- degat);
			} else {
				characterSheet.setCurrentHp(characterSheet.getCurrentHp()
						+ degat);
			}
		}
	}

	private void rotateMapElement(MapElementClient<?> rotate, Double angle) {
		rotate.setAngle(rotate.getAngle() + angle);
		// TODO rotate.applyRotate();
	}

}
