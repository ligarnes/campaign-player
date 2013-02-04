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
package net.alteiar.server.shared.campaign.player;

import java.rmi.RemoteException;

import net.alteiar.server.shared.observer.campaign.PlayerObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PlayerRemote extends PlayerObservableRemote implements IPlayerRemote {

	private static final long serialVersionUID = 8285683561473463471L;

	private final String name;
	private final Boolean isMj;

	/**
	 * @throws RemoteException
	 */
	public PlayerRemote(String name, boolean isMj) throws RemoteException {
		super();
		this.name = name;
		this.isMj = isMj;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public Boolean getIsMj() throws RemoteException {
		return isMj;
	}
}
