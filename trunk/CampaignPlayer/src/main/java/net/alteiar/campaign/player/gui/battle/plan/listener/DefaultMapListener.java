package net.alteiar.campaign.player.gui.battle.plan.listener;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.battle.plan.MapEditableInfo;
import net.alteiar.campaign.player.gui.battle.plan.details.MapEvent;
import net.alteiar.campaign.player.gui.battle.tools.PanelAddCharacter;
import net.alteiar.campaign.player.gui.battle.tools.PanelCreateElement;
import net.alteiar.campaign.player.gui.tools.PanelAlwaysValidOkCancel;
import net.alteiar.client.CampaignClient;
import net.alteiar.dialog.DialogOkCancel;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.element.MapElementClient;
import net.alteiar.server.document.map.element.character.CharacterCombatClient;

public class DefaultMapListener extends ActionMapListener {

	private enum FORM {
		CIRCLE, CONE, RAY
	};

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
				// Show add -> circle, rect, character, monstre...
				final CharacterClient[] availableCharacter = getAvaibleCharacter();
				/*
				 * final CharacterClient[] availableMonster = CampaignClient
				 * .getInstance().getAllMonster();
				 */
				// Menu item add character
				if (availableCharacter.length > 0) {
					popup.add(buildAddCharacter(event.getMouseEvent(),
							event.getMapPosition(), availableCharacter, false));
				}

				/*
				 * if (CampaignClient.INSTANCE.getCurrentPlayer().isMj()) { //
				 * Menu item add monster if (availableMonster.length > 0) {
				 * popup.add(buildAddCharacter(event.getMouseEvent(),
				 * event.getMapPosition(), availableMonster, true)); } }
				 */

				// Menu item add Circle
				popup.add(buildAddElement(event.getMouseEvent(),
						event.getMapPosition(), FORM.CIRCLE));
				popup.add(buildAddElement(event.getMouseEvent(),
						event.getMapPosition(), FORM.CONE));
				popup.add(buildAddElement(event.getMouseEvent(),
						event.getMapPosition(), FORM.RAY));

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
						mapListener, mapInfo, mapPosition));
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

	private JMenuItem buildAddCharacter(final MouseEvent event,
			final Point mapPosition, final CharacterClient[] availableUnit,
			final Boolean isMonster) {

		String title = "personnage";
		if (isMonster) {
			title = "monstre";
		}

		JMenuItem addCharacter = new JMenuItem("Ajouter " + title);
		addCharacter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCharacter(event, mapPosition, availableUnit, isMonster);
			}
		});

		return addCharacter;
	}

	private JMenuItem buildAddElement(final MouseEvent event,
			final Point mapPosition, final FORM form) {

		String title = "";
		switch (form) {
		case CIRCLE:
			title = "cercle";
			break;
		case CONE:
			title = "cone";
			break;
		case RAY:
			title = "rayon";
			break;
		}

		JMenuItem addCircle = new JMenuItem("Ajouter " + title);
		addCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addElement(event, mapPosition, form);
			}
		});

		return addCircle;
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
			final CharacterCombatClient character, final Boolean isDamage) {

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

	private Boolean verifyBattleContainCharacter(CharacterClient src) {
		Boolean contain = false;
		/*
		 * TODO for (CharacterClient characterCombat : battle.getAllCharacter())
		 * { if (characterCombat.getCharacter().equals(src)) { contain = true;
		 * break; } }
		 */
		return contain;
	}

	private CharacterClient[] getAvaibleCharacter() {
		List<CharacterClient> lst = new ArrayList<CharacterClient>();
		for (final CharacterClient character : CampaignClient.getInstance()
				.getCharacters()) {
			final Boolean isInBattle = verifyBattleContainCharacter(character);
			if (!isInBattle) {
				lst.add(character);
			}
		}

		CharacterClient[] res = new CharacterClient[lst.size()];
		lst.toArray(res);
		return res;
	}

	private void doDamage(MouseEvent orgEvent,
			CharacterCombatClient characterCombat, Boolean isDamage) {
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

	private void addCharacter(MouseEvent orgEvent, Point mapPosition,
			CharacterClient[] lst, boolean isMonster) {
		if (lst.length > 0) {
			PanelAddCharacter panelAdd = new PanelAddCharacter(lst, isMonster);

			String title = "Ajouter personnage";
			if (isMonster) {
				title = "Ajouter monstre";
			}

			DialogOkCancel<PanelAddCharacter> dlg = new DialogOkCancel<PanelAddCharacter>(
					null, title, true, panelAdd);

			dlg.setLocation(orgEvent.getXOnScreen() - (dlg.getWidth() / 2),
					orgEvent.getYOnScreen() - (dlg.getHeight() / 2));
			dlg.setVisible(true);
			if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
				if (isMonster) {
					mapInfo.addMonsterAt(panelAdd.getCharacter(),
							panelAdd.getInitiative(),
							panelAdd.isVisibleForAllPlayer(), mapPosition);
				} else {
					mapInfo.addCharacterAt(panelAdd.getCharacter(),
							panelAdd.getInitiative(), mapPosition);
				}
			}
		}
	}

	private void addElement(MouseEvent orgEvent, Point mapPosition, FORM form) {
		PanelCreateElement create = PanelCreateElement.getInstance();

		String title = "";
		switch (form) {
		case CIRCLE:
			title = "cercle";
			create.setNeedSize2(false);
			break;
		case CONE:
			title = "cone";
			create.setNeedSize2(false);
			break;
		case RAY:
			title = "rectangle";
			create.setNeedSize2(true);
			break;
		}

		DialogOkCancel<PanelCreateElement> dlg = new DialogOkCancel<PanelCreateElement>(
				null, "Créer un " + title, true, create);

		dlg.setLocation(orgEvent.getXOnScreen() - (dlg.getWidth() / 2),
				orgEvent.getYOnScreen() - (dlg.getHeight() / 2));
		dlg.setVisible(true);
		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			switch (form) {
			case CIRCLE:
				mapInfo.addCircle(mapPosition, create.getSize1(),
						create.getColor());
				break;
			case CONE:
				mapInfo.addCone(mapPosition, create.getSize1(),
						create.getColor());
				break;
			case RAY:
				mapInfo.addRay(mapPosition, create.getSize1(),
						create.getSize2(), create.getColor());
				break;
			}
		}
	}

	private void rotateMapElement(MapElementClient<?> rotate, Double angle) {
		rotate.setAngle(rotate.getAngle() + angle);
		// TODO rotate.applyRotate();
	}

}
