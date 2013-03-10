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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.alteiar.client.CampaignClient;
import net.alteiar.client.IWaitForDocumentListener;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.files.ImageClient;
import net.alteiar.server.document.map.element.MapElementClient;
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

	protected MapFilterClient getMapFilter() {
		return (MapFilterClient) CampaignClient.getInstance().getDocument(
				filter);
	}

	public BufferedImage getFilter() {
		return getMapFilter().getShape();
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

	public void removeMapElement(MapElementClient<?> map) {
		removeMapElement(map.getId());
	}

	public void removeMapElement(Long mapElement) {
		try {
			getRemote().removeMapElement(mapElement);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * only elements who are loaded
	 * 
	 * @return
	 */
	public List<MapElementClient<?>> getElements() {
		ArrayList<MapElementClient<?>> elementsCopy = new ArrayList<MapElementClient<?>>();
		synchronized (elements) {
			for (Long element : elements) {
				MapElementClient<?> mapElement = (MapElementClient<?>) CampaignClient
						.getInstance().getDocument(element);
				if (mapElement != null) {
					elementsCopy.add(mapElement);
				}
			}
		}
		return elementsCopy;
	}

	public Collection<MapElementClient<?>> getElementsAt(Point p) {
		ArrayList<MapElementClient<?>> elementsCopy = new ArrayList<MapElementClient<?>>();
		for (MapElementClient<?> element : getElements()) {
			if (element.contain(p)) {
				elementsCopy.add(element);
			}
		}
		return elementsCopy;
	}

	public void addLocalMapElement(final Long mapElementId) {
		synchronized (elements) {
			elements.add(mapElementId);
		}

		MapElementClient<?> element = (MapElementClient<?>) CampaignClient
				.getInstance().getDocument(mapElementId);
		if (element != null) {
			// the document already exist so notify now
			notifyMapElementAdded(element);
		} else {
			// Notify when element is received only
			CampaignClient.getInstance().addWaitForDocumentListener(
					new IWaitForDocumentListener() {
						@Override
						public Long getDocument() {
							return mapElementId;
						}

						@Override
						public void documentReceived(DocumentClient<?> doc) {
							notifyMapElementAdded((MapElementClient<?>) doc);
						}
					});
		}
	}

	public void removeLocalMapElement(final Long mapElementId) {
		synchronized (elements) {
			elements.remove(mapElementId);
		}

		MapElementClient<?> mapElement = (MapElementClient<?>) CampaignClient
				.getInstance().getDocument(mapElementId);
		this.notifyMapElementRemoved(mapElement);
	}

	private synchronized void setLocalScale(Scale scale) {
		this.scale = scale;
		this.notifyScaleChanged();
	}

	public void addMapListener(final IMapListener listener) {
		this.addListener(IMapListener.class, listener);

		MapFilterClient filter = getMapFilter();
		if (filter != null) {
			getMapFilter().addFilterListener(listener);
		} else {
			// add listener when element is received only
			CampaignClient.getInstance().addWaitForDocumentListener(
					new IWaitForDocumentListener() {
						@Override
						public Long getDocument() {
							return MapClient.this.filter;
						}

						@Override
						public void documentReceived(DocumentClient<?> doc) {
							((MapFilterClient) doc).addFilterListener(listener);
						}
					});
		}
	}

	public void removeMapListener(IMapListener listener) {
		this.removeListener(IMapListener.class, listener);
		MapFilterClient filter = getMapFilter();
		if (filter != null) {
			getMapFilter().removeFilterListener(listener);
		}
	}

	protected void notifyMapElementAdded(MapElementClient<?> mapElement) {
		for (IMapListener listener : getListener(IMapListener.class)) {
			listener.mapElementAdded(mapElement);
		}
	}

	protected void notifyMapElementRemoved(MapElementClient<?> mapElement) {
		for (IMapListener listener : getListener(IMapListener.class)) {
			listener.mapElementRemoved(mapElement);
		}
	}

	protected void notifyScaleChanged() {
		for (IMapListener listener : getListener(IMapListener.class)) {
			listener.mapRescale(scale);
		}
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

		@Override
		public void mapElementAdded(Long elementId) throws RemoteException {
			addLocalMapElement(elementId);
		}

		@Override
		public void mapElementRemoved(Long elementId) throws RemoteException {
			removeLocalMapElement(elementId);
		}
	}
}
