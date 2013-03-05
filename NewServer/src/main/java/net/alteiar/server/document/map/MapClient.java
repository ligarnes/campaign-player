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
package net.alteiar.server.document.map;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.files.ImageClient;
import net.alteiar.server.document.map.filter.MapFilterClient;
import net.alteiar.shared.ExceptionTool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapClient<E extends IMapRemote> extends DocumentClient<E> {
	private static final long serialVersionUID = 1L;

	private transient MapRemoteListener listener;

	private final String mapName;
	private final Integer width;
	private final Integer height;

	private final Long background;
	private final Long filter;
	private Scale scale;

	private final HashSet<Long> elements;

	protected MapClient(E map2D) throws RemoteException {
		super(map2D);

		mapName = map2D.getName();
		width = map2D.getWidth();
		height = map2D.getHeight();

		background = map2D.getBackground();
		filter = map2D.getFilter();

		scale = map2D.getScale();

		elements = map2D.getMapElements();
	}

	public String getName() {
		return mapName;
	}

	public BufferedImage getBackground() {
		return ((ImageClient) CampaignClient.getInstance().getDocument(
				background)).getImage();
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		if (!this.scale.equals(scale)) {
			try {
				this.getRemote().setScale(scale);
			} catch (RemoteException e) {
				ExceptionTool.showError(e);
			}
		}
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	@Override
	protected void closeDocument() throws RemoteException {
		getRemote().removeMapListener(listener);
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
		listener = new MapRemoteListener(getRemote());
	}

	public BufferedImage getFilter() {
		MapFilterClient mapFilter = (MapFilterClient) CampaignClient
				.getInstance().getDocument(filter);
		return mapFilter.getShape();
	}

	public void showPolygon(Point[] cwPts) {
		MapFilterClient mapFilter = (MapFilterClient) CampaignClient
				.getInstance().getDocument(filter);
		mapFilter.showPolygon(cwPts);
	}

	public void hidePolygon(Point[] cwPts) {
		MapFilterClient mapFilter = (MapFilterClient) CampaignClient
				.getInstance().getDocument(filter);
		mapFilter.hidePolygon(cwPts);
	}

	public void showRectangle(Integer x, Integer y, Integer width,
			Integer height) {
		Point[] cwPts = new Point[4];
		cwPts[0] = new Point(x, y);
		cwPts[1] = new Point(x, y + height);
		cwPts[2] = new Point(x + width, y + height);
		cwPts[3] = new Point(x + width, y);
		showPolygon(cwPts);
	}

	public void hideRectangle(Integer x, Integer y, Integer width,
			Integer height) {
		Point[] cwPts = new Point[4];
		cwPts[0] = new Point(x, y);
		cwPts[1] = new Point(x, y + height);
		cwPts[2] = new Point(x + width, y + height);
		cwPts[3] = new Point(x + width, y);
		hidePolygon(cwPts);
	}

	public void addMapElement(Long mapElement) {
		try {
			getRemote().addMapElement(mapElement);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeMapElement(Long mapElement) {
		try {
			getRemote().removeMapElement(mapElement);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @Override public IMapElementClient[] getAllElements() {
	 * allElements.incCounter(); IMapElementClient[] elements = new
	 * IMapElementClient[allElements.size()]; allElements.toArray(elements);
	 * allElements.decCounter(); return elements; }
	 * 
	 * @Override public IMapElementClient getElementAt(Point location) {
	 * IMapElementClient element = null; for (IMapElementClient mapElement :
	 * getAllElements()) { if (mapElement.isInside(location)) { element =
	 * mapElement; break; } } return element; }
	 * 
	 * @Override public void addCircle(MapElementSize radius, Color color, Point
	 * position) { try { ICircleRemote circle =
	 * remoteObject.createCircle(position, color, radius);
	 * remoteObject.addMapElement(circle); } catch (RemoteException e) {
	 * ExceptionTool.showError(e); } }
	 * 
	 * @Override public void addCone(MapElementSize radius, Color color, Point
	 * position) { try { IConeRemote cone = remoteObject.createCone(position,
	 * color, radius); remoteObject.addMapElement(cone); } catch
	 * (RemoteException e) { ExceptionTool.showError(e); } }
	 * 
	 * @Override public void addRay(MapElementSize longueur, MapElementSize
	 * largueur, Color color, Point position) { try { IRayRemote ray =
	 * remoteObject.createRay(position, color, longueur, largueur);
	 * 
	 * remoteObject.addMapElement(ray); } catch (RemoteException e) {
	 * ExceptionTool.showError(e); } }
	 * 
	 * @Override public void removeMapElement(IMapElementClient mapElements) {
	 * try { remoteObject.removedMapElement(mapElements.getRemoteReference()); }
	 * catch (RemoteException e) { ExceptionTool.showError(e); } }
	 */

	public synchronized void addLocalMapElement(Long mapElement) {
		elements.add(mapElement);
		// TODO notifyMapChanged();
	}

	public synchronized void removeLocalMapElement(Long mapElement) {
		elements.remove(mapElement);
		// TODO notifyMapChanged();
	}

	private synchronized void setLocalScale(Scale scale) {
		this.scale = scale;
		// TODO notify this.notifyMapChanged();
	}

	private class MapRemoteListener extends UnicastRemoteObject implements
			IMapListenerRemote {
		private static final long serialVersionUID = 1L;

		protected MapRemoteListener(IMapRemote remote) throws RemoteException {
			super();
			remote.addMapListener(this);
		}

		@Override
		public void mapRescale(Scale scale) throws RemoteException {
			setLocalScale(scale);
		}
	}
}
