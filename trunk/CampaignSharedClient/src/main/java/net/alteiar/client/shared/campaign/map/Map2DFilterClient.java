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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.observer.ProxyClientObservableSimple;
import net.alteiar.server.shared.campaign.battle.map.element.IFilterRemote;
import net.alteiar.server.shared.geometry.Polygon2D;
import net.alteiar.server.shared.observer.campaign.map.IMapFilterObserverRemote;
import net.alteiar.thread.Task;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Map2DFilterClient extends
		ProxyClientObservableSimple<IFilterRemote> {
	private static final long serialVersionUID = 1L;
	private static final Float ALPHA_COMPOSITE_HIDE_PJ = 1.0f;
	private static final Float ALPHA_COMPOSITE_HIDE_MJ = 0.7f;

	private final int width;
	private final int height;

	private BufferedImage shapeImage;

	private BufferedImage backgroundImg;

	/**
	 * @param element
	 */
	public Map2DFilterClient(IFilterRemote element, IMap2DClient<?> map,
			int widthMap, int heightMap) {
		super(element);
		this.width = widthMap;
		this.height = heightMap;

		try {
			backgroundImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			Graphics g2 = backgroundImg.getGraphics();
			g2.setColor(Color.GRAY);
			g2.fillRect(0, 0, width, height);
			g2.dispose();

			refreshShape(remoteObject.getVisibleRectangle());
			CampaignClient.WORKER_POOL_CAMPAIGN.addTask(new Task() {
				@Override
				public void run() {
					int step = 1;
					for (int i = 0; i < width; i += step) {
						for (int j = 0; j < height; j += step) {
							int col = FastNoise.noise(i / 128f, j / 128f, 7);

							int red = col;
							int green = col;
							int blue = col;

							int RGB = red;
							RGB = (RGB << 8) + green;
							RGB = (RGB << 8) + blue;

							int widthMax = Math.min(width, i + step);
							int heightMax = Math.min(height, j + step);

							for (int x = i; x < widthMax; ++x) {
								for (int y = j; y < heightMax; ++y) {
									backgroundImg.setRGB(x, y, RGB);
								}
							}
						}
					}
					try {
						refreshShape(remoteObject.getVisibleRectangle());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}

				@Override
				public String getStartText() {
					return "creating fog of war";
				}

				@Override
				public String getFinishText() {
					return "fog of war generated";
				}
			});

			new Map2DFilterClientRemote(element);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}

	}

	private void drawHide(Graphics2D g2, Shape shape) {
		Float alpha = ALPHA_COMPOSITE_HIDE_PJ;
		if (CampaignClient.INSTANCE.getCurrentPlayer().isMj()) {
			alpha = ALPHA_COMPOSITE_HIDE_MJ;
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));

		g2.fill(shape);
	}

	private void drawVisible(Graphics2D g2, Shape shape) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 0.0f));

		g2.fill(shape);
	}

	protected void refreshShape(Polygon2D visible) {
		int realWidth = width;
		int realHeight = height;

		// create an image from graphics
		BufferedImage img = new BufferedImage(realWidth, realHeight,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setPaint(new TexturePaint(backgroundImg, new Rectangle2D.Float(0, 0,
				width, height)));

		Shape shape = new Rectangle2D.Double(0, 0, realWidth, realHeight);
		drawHide(g2, shape);
		List<Polygon> internals = visible.getInternals();
		List<Polygon> externals = visible.getExternals();

		for (Shape shap : externals) {
			drawVisible(g2, shap);
		}
		for (Shape shap : internals) {
			drawHide(g2, shap);
		}

		g2.dispose();

		this.shapeImage = img;
	}

	public BufferedImage getShape() {
		return shapeImage;
	}

	private synchronized void setVisible(Polygon2D img) {
		refreshShape(img);
		this.notifyListeners();
	}

	/**
	 * this class should be observer and will use the notify of the Map2DClient
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class Map2DFilterClientRemote extends UnicastRemoteObject implements
			IMapFilterObserverRemote {

		private static final long serialVersionUID = 2559145398149500009L;

		/**
		 * @throws RemoteException
		 */
		protected Map2DFilterClientRemote(IFilterRemote filter)
				throws RemoteException {
			super();
			filter.addFilterListener(this);
		}

		@Override
		public void filterChanged(Polygon2D img) throws RemoteException {
			setVisible(img);
		}
	}
}
