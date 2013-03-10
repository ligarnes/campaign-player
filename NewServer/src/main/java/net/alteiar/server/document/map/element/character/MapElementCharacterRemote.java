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
package net.alteiar.server.document.map.element.character;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.MapElementRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapElementCharacterRemote extends MapElementRemote implements
		IMapElementCharacterRemote {
	private static final long serialVersionUID = -3573905342323034939L;

	private final Long characterClient;

	public MapElementCharacterRemote(MapClient<?> map, Point position,
			Long characterClient) throws RemoteException {
		this(map.getId(), position, characterClient);
	}

	public MapElementCharacterRemote(Long map, Point position, Long characterClient)
			throws RemoteException {
		super(map, position);

		this.characterClient = characterClient;
	}

	@Override
	public Long getCharacterClient() throws RemoteException {
		return this.characterClient;
	}

	@Override
	public DocumentClient<?> buildProxy() throws RemoteException {
		return new MapElementCharacterClient(this);
	}
}
