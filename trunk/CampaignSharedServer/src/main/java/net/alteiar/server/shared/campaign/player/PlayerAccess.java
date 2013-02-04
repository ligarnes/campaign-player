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

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PlayerAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	private final List<Long> authorisation;
	private final Boolean authMJ;
	private final Boolean authAll;

	public PlayerAccess() throws RemoteException {
		this(false);
	}

	private PlayerAccess(List<Long> authList, Boolean authMj, Boolean authAll) {
		super();
		authorisation = authList;
		this.authMJ = authMj;
		this.authAll = authAll;
	}

	public PlayerAccess(Boolean authMj, Boolean authAll) {
		this(new ArrayList<Long>(), authMj, authAll);
	}

	public PlayerAccess(List<Long> authList, Boolean authMj) {
		this(authList, authMj, false);
	}

	public PlayerAccess(Boolean authAll) {
		this(new ArrayList<Long>(), false, authAll);
	}

	public PlayerAccess(List<Long> authList) {
		this(authList, false, false);
	}

	public void addPlayer(Long player) {
		authorisation.add(player);
	}

	public Boolean getAuthorizationMj() {
		return this.authMJ;
	}

	public Boolean getAuthorizationAll() {
		return this.authAll;
	}

	public List<Long> getAuthorizationList() {
		return this.authorisation;
	}

	public Boolean canAcces(Long playerId, Boolean isMj) {
		Boolean result = false;

		if (authAll) {
			result = true;
		} else if (authMJ && isMj) {
			result = true;
		} else {
			for (Long guid : authorisation) {
				if (guid.equals(playerId)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	@Override
	public String toString() {
		return "PlayerAccess [authorisation=" + authorisation + ", authMJ="
				+ authMJ + ", authAll=" + authAll + "]";
	}
}
