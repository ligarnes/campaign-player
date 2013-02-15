/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.client.shared.campaign.battle;

import java.io.File;
import java.rmi.RemoteException;

import net.alteiar.images.SerializableFile;
import net.alteiar.server.shared.campaign.IServerCampaign;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.thread.Task;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CreateBattleTask implements Task {
	private final IServerCampaign campaign;
	private final String name;
	private final SerializableFile map;
	private final Scale scale;

	public CreateBattleTask(IServerCampaign campaign, String name, File map,
			Scale scale) {
		super();
		this.campaign = campaign;
		this.name = name;
		this.map = new SerializableFile(map);
		this.scale = scale;
	}

	public CreateBattleTask(IServerCampaign campaign, String name,
			SerializableFile map, Scale scale) {
		super();
		this.campaign = campaign;
		this.name = name;
		this.map = map;
		this.scale = scale;
	}

	@Override
	public void run() {
		try {
			campaign.createBattle(name, map, scale);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getStartText() {
		return "Create battle";
	}

	@Override
	public String getFinishText() {
		return "send data";
	}

}
