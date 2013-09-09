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
package net.alteiar.newversion.server;

import java.io.IOException;

import net.alteiar.thread.ThreadPoolUtils;

/**
 * @author Cody Stoutenburg
 * 
 */
public final class ServerDocuments {

	private final String campaignPath;

	private final ServerGeneralDocument serverGeneral;
	private final ServerAddDocument serverAdd;
	private final ServerAddDocument serverMod;

	public ServerDocuments(int portTcp, String campaignPath) throws IOException {
		super();

		this.campaignPath = campaignPath;

		serverGeneral = new ServerGeneralDocument(this, portTcp);

		serverAdd = new ServerAddDocument(portTcp + 2);
		serverMod = new ServerAddDocument(portTcp + 4);

		ThreadPoolUtils.startServerThreadPool();
	}

	public void stopServer() {
		ThreadPoolUtils.getServerPool().shutdown();

		serverGeneral.stop();
		serverAdd.stop();
		serverMod.stop();
	}

	public String getSpecificPath() {
		return campaignPath;
	}
}
