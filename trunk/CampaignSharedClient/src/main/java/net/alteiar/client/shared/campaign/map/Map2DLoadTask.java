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
package net.alteiar.client.shared.campaign.map;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import net.alteiar.client.shared.observer.campaign.map.element.BaseMapElementClient;
import net.alteiar.server.shared.campaign.battle.map.IMap2DRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IFilterRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IMapElementObservableRemote;
import net.alteiar.thread.Task;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Map2DLoadTask implements Task {

	private final Map2DClient<?> mapClient;
	private final IMap2DRemote remoteMap;

	public Map2DLoadTask(Map2DClient<?> client, IMap2DRemote remoteMap) {
		mapClient = client;
		this.remoteMap = remoteMap;
	}

	@Override
	public String getStartText() {
		return "downloading map";
	}

	@Override
	public String getFinishText() {
		return "map downloaded";
	}

	@Override
	public void run() {
		try {
			// set all local stuff
			mapClient.setLocalScale(remoteMap.getScale());

			BufferedImage img = remoteMap.getBackground().restoreImage();
			mapClient.setLocalBackground(img);

			// mapClient
			IFilterRemote remote = remoteMap.getFilter();
			Map2DFilterClient client = new Map2DFilterClient(remote, mapClient,
					img.getWidth(), img.getHeight());
			mapClient.setLocalFilter(client);

			for (IMapElementObservableRemote mapElement : remoteMap
					.getAllElements()) {
				BaseMapElementClient element = Map2DClient.loadMapElement(
						mapElement, mapClient);

				if (element != null) {
					mapClient.addLocalMapElement(element);
				}
			}

			mapClient.setMapLoaded(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
