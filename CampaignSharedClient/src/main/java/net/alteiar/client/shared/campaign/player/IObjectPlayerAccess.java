package net.alteiar.client.shared.campaign.player;

import net.alteiar.server.shared.campaign.player.PlayerAccess;

public interface IObjectPlayerAccess {

	PlayerAccess getAccess();

	void setAccess(PlayerAccess access);

}
