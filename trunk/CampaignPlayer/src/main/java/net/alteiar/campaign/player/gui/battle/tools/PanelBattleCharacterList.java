package net.alteiar.campaign.player.gui.battle.tools;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.BattleClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.battle.character.CharacterCombatInitiativeComparator;
import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.observer.campaign.battle.IBattleObserver;
import net.alteiar.client.shared.observer.campaign.battle.character.ICharacterCombatObserver;

public class PanelBattleCharacterList extends JPanel implements
		IBattleObserver, ICharacterCombatObserver {
	private static final long serialVersionUID = 1L;

	private final HashMap<Long, PanelBattleCharacter> allCharactersPanel;

	private final PanelBattleNextTurn nextTurnPanel;

	public PanelBattleCharacterList(IBattleClient battle) {
		super();
		this.setPreferredSize(new Dimension(60, 60));

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setOpaque(false);

		allCharactersPanel = new HashMap<Long, PanelBattleCharacter>();

		// battle.setInitiativeEachTurn(true);

		battle.addBattleListener(this);
		for (ICharacterCombatClient character : battle.getAllCharacter()) {
			allCharactersPanel.put(character.getId(), new PanelBattleCharacter(
					battle, character));
		}
		nextTurnPanel = new PanelBattleNextTurn(battle);

		refreshPanel();
	}

	private void refreshPanel() {

		List<PanelBattleCharacter> allPanels = new ArrayList<PanelBattleCharacter>(
				allCharactersPanel.values());

		Collections.sort(allPanels, new Comparator<PanelBattleCharacter>() {
			CharacterCombatInitiativeComparator comparator = new CharacterCombatInitiativeComparator();

			@Override
			public int compare(PanelBattleCharacter o1, PanelBattleCharacter o2) {
				return comparator.compare(o2.getCharacter(), o1.getCharacter());
			}
		});

		this.removeAll();
		for (PanelBattleCharacter panel : allPanels) {
			if (CampaignClient.INSTANCE.getCurrentPlayer().isMj()
					|| panel.getCharacter().isVisibleForPlayer()) {
				this.add(panel);
			}
		}
		this.add(nextTurnPanel);

		this.revalidate();
		this.repaint();
	}

	@Override
	public void characterChange(ICharacterCombatClient character) {
		// Do not care
	}

	@Override
	public void highLightChange(ICharacterCombatClient character,
			Boolean isHighlighted) {
		// Do not care
	}

	@Override
	public void visibilityChange(ICharacterCombatClient character) {
		refreshPanel();
	}

	@Override
	public void initiativeChange(ICharacterCombatClient character) {
		refreshPanel();
	}

	@Override
	public void characterAdded(BattleClient battle,
			ICharacterCombatClient character) {

		character.addCharacterCombatListener(this);

		allCharactersPanel.put(character.getId(), new PanelBattleCharacter(
				battle, character));

		refreshPanel();
	}

	@Override
	public void characterRemove(BattleClient battle,
			ICharacterCombatClient character) {
		character.removeCharacterCombatListener(this);

		allCharactersPanel.remove(character.getId());
		refreshPanel();
	}

	@Override
	public void turnChanged(BattleClient battle) {
		// Do not care
	}

	@Override
	public void positionChanged(ICharacterCombatClient character) {
		// Do not care
	}

	@Override
	public void rotationChanged(ICharacterCombatClient character) {
		// Do not care
	}
}
