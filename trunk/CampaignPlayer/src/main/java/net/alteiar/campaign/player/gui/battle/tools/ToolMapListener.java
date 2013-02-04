package net.alteiar.campaign.player.gui.battle.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

import net.alteiar.campaign.player.gui.battle.plan.MapEditableInfo;
import net.alteiar.campaign.player.gui.battle.plan.details.MapEvent;
import net.alteiar.campaign.player.gui.battle.plan.details.MapListener;
import net.alteiar.campaign.player.gui.battle.plan.listener.GlobalMapListener;
import net.alteiar.campaign.player.gui.battle.plan.listener.RescaleMapListener;
import net.alteiar.campaign.player.gui.battle.plan.listener.ShowHidePolygonMapListener;
import net.alteiar.campaign.player.gui.tools.DialogOkCancel;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;

public class ToolMapListener extends Observable implements MapListener {

	public enum Tools {
		ADD_CHARACTER, ADD_MONSTER, ADD_CIRCLE, ADD_RAY, ADD_CONE, CHANGE_SCALE, SHOW, HIDE, DO_NOTHING
	};

	private final MapEditableInfo mapInfo;
	private final IBattleClient battle;
	private final GlobalMapListener mapListener;
	private Tools toolState;

	public ToolMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener, IBattleClient battle) {
		toolState = Tools.ADD_CHARACTER;
		this.mapListener = mapListener;
		this.mapInfo = mapInfo;
		this.battle = battle;
	}

	private Boolean verifyBattleContainCharacter(ICharacterSheetClient src) {
		Boolean contain = false;
		for (ICharacterCombatClient characterCombat : battle.getAllCharacter()) {
			if (characterCombat.getCharacter().equals(src)) {
				contain = true;
				break;
			}
		}
		return contain;
	}

	public List<ICharacterSheetClient> getAvaibleCharacter() {
		List<ICharacterSheetClient> lst = new ArrayList<ICharacterSheetClient>();
		for (final ICharacterSheetClient character : CampaignClient.INSTANCE
				.getAllCharacter()) {
			final Boolean isInBattle = verifyBattleContainCharacter(character);
			if (!isInBattle) {
				lst.add(character);
			}
		}

		return lst;
	}

	public void setToolState(Tools tool) {
		this.toolState = tool;
		this.setChanged();
		this.notifyObservers();
	}

	public Tools getToolState() {
		return this.toolState;
	}

	@Override
	public void mouseClicked(MapEvent event) {

		switch (toolState) {
		case ADD_CHARACTER:
			addCharacter(event.getMouseEvent(), getAvaibleCharacter(),
					event.getMapPosition());
			mapListener.defaultListener();
			break;
		case ADD_MONSTER:
			addCharacter(event.getMouseEvent(),
					CampaignClient.INSTANCE.getAllMonster(),
					event.getMapPosition());
			mapListener.defaultListener();
			break;
		case ADD_CIRCLE:
		case ADD_CONE:
		case ADD_RAY:
			addElement(event.getMouseEvent(), event.getMapPosition());
			mapListener.defaultListener();
			break;
		case CHANGE_SCALE:
			mapListener.setCurrentListener(new RescaleMapListener(mapListener,
					mapInfo, event.getMapPosition()));
			break;
		case SHOW:
			mapListener.setCurrentListener(new ShowHidePolygonMapListener(
					mapListener, mapInfo, event.getMapPosition(), true));
			break;
		case HIDE:
			mapListener.setCurrentListener(new ShowHidePolygonMapListener(
					mapListener, mapInfo, event.getMapPosition(), false));
			break;
		case DO_NOTHING:
			break;
		}

		setToolState(Tools.DO_NOTHING);
	}

	private void addElement(MouseEvent orgEvent, Point mapPosition) {
		PanelCreateElement create = PanelCreateElement.getInstance();

		String title = "";
		switch (toolState) {
		case ADD_CIRCLE:
			title = "cercle";
			create.setNeedSize2(false);
			break;
		case ADD_CONE:
			title = "cone";
			create.setNeedSize2(false);
			break;
		case ADD_RAY:
			title = "rectangle";
			create.setNeedSize2(true);
			break;
		default:
			return;
		}

		DialogOkCancel<PanelCreateElement> dlg = new DialogOkCancel<PanelCreateElement>(
				null, "Créer un " + title, true, create);

		dlg.setLocation(orgEvent.getXOnScreen() - (dlg.getWidth() / 2),
				orgEvent.getYOnScreen() - (dlg.getHeight() / 2));
		dlg.setVisible(true);
		if (dlg.getReturnStatus() == DialogOkCancel.RET_OK) {
			switch (toolState) {
			case ADD_CIRCLE:
				mapInfo.addCircle(mapPosition, create.getSize1(),
						create.getColor());
				break;
			case ADD_CONE:
				mapInfo.addCone(mapPosition, create.getSize1(),
						create.getColor());
				break;
			case ADD_RAY:
				mapInfo.addRay(mapPosition, create.getSize1(),
						create.getSize2(), create.getColor());
				break;
			default:
				return;
			}
		}
	}

	private void addCharacter(MouseEvent orgEvent,
			Collection<ICharacterSheetClient> lst, Point mapPosition) {
		if (lst.size() > 0) {
			Boolean isMonster = toolState.equals(Tools.ADD_MONSTER);
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

	@Override
	public void mousePressed(MapEvent event) {
	}

	@Override
	public void mouseReleased(MapEvent event) {
	}

	@Override
	public void mouseElementEntered(MapEvent event) {
	}

	@Override
	public void mouseElementExited(MapEvent event) {
	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
	}

	@Override
	public void mouseMove(MouseEvent e, Point mapPosition) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
	}
}
