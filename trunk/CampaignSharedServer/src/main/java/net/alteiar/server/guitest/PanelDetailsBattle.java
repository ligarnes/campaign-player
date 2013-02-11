package net.alteiar.server.guitest;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JLabel;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;
import net.alteiar.server.shared.campaign.battle.ICharacterCombatRemote;

public class PanelDetailsBattle extends PanelDetails {
	private static final long serialVersionUID = 1L;

	public PanelDetailsBattle() {
		super("combats");

	}

	@Override
	protected String[] getNames() throws RemoteException {
		List<IBattleRemote> battles = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getBattles();

		String[] names = new String[battles.size()];
		for (int i = 0; i < battles.size(); ++i) {
			names[i] = battles.get(i).getName();
		}
		return names;
	}

	private IBattleRemote getSelectedBattle() throws RemoteException {
		IBattleRemote battle = null;

		List<IBattleRemote> battles = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getBattles();
		String aName = getSelectedName();
		for (IBattleRemote iBattle : battles) {
			if (iBattle.getName().equals(aName)) {
				battle = iBattle;
				break;
			}
		}

		return battle;
	}

	@Override
	protected void askChange() throws RemoteException {
		refreshBattle();
	}

	@Override
	protected void askRefresh() throws RemoteException {
		refreshBattle();
	}

	private void refreshBattle() throws RemoteException {
		IBattleRemote battle = getSelectedBattle();

		if (battle != null) {
			this.getPanelData().removeAll();

			List<ICharacterCombatRemote> characters = battle.getAllCharacter();
			for (ICharacterCombatRemote iCharacterCombatRemote : characters) {
				Boolean isVisible = iCharacterCombatRemote.isVisibleForPlayer();
				String name = iCharacterCombatRemote.getCharacterSheet()
						.getCharacterFacade().getName();

				JLabel lbl = new JLabel(name + " -> " + isVisible);

				this.getPanelData().add(lbl);
			}
		}
	}
}
