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
package net.alteiar.client.shared.campaign;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;

import net.alteiar.ExceptionTool;
import net.alteiar.images.TransfertImage;
import net.alteiar.server.shared.campaign.IMediaManagerRemote;
import net.alteiar.thread.Task;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MediaManagerLoadTask implements Task {

	private final MediaManagerClient mediaManager;
	private final IMediaManagerRemote remoteMediaManager;

	public MediaManagerLoadTask(MediaManagerClient client,
			IMediaManagerRemote remoteMap) {
		mediaManager = client;
		this.remoteMediaManager = remoteMap;
	}

	@Override
	public String getStartText() {
		return "downloading media";
	}

	@Override
	public String getFinishText() {
		return "media downloaded";
	}

	@Override
	public void run() {
		try {
			// set all local stuff
			HashMap<Long, TransfertImage> files = remoteMediaManager
					.getAllImages();

			for (Entry<Long, TransfertImage> entry : files.entrySet()) {
				try {
					mediaManager.addRemoteImage(entry.getKey(), entry
							.getValue().restoreImage());
				} catch (IOException e) {
					ExceptionTool.showError(e,
							"Impossible de charger l'image: " + entry.getKey());
				}
			}
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}

	}
}
