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
package net.alteiar.campaign.player.gui.centerViews.map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import net.alteiar.WaitBeanListener;
import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.beans.map.filter.MapFilter;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.Drawable;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.Drawable.DrawableListener;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.Drawable.DrawableMouseListener;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.MapElementDrawable;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.button.ButtonDrawable;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse.MouseDrawable;
import net.alteiar.campaign.player.infos.HelpersImages;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.ImageUtil;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;
import net.alteiar.zoom.Zoomable;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelMapBasic extends JPanel implements PropertyChangeListener,
		Zoomable, ActionListener, DrawableListener {
	private static final long serialVersionUID = -5027864086357387475L;

	protected final MapBean map;

	protected final List<MapElementDrawable> drawableElements;
	protected final List<ButtonDrawable> buttons;

	private final List<MouseDrawable> drawables;
	private final DrawFilter playerDraw;
	private Double zoomFactor;
	private Boolean showGrid;

	private final Timer refreshTime;

	private final int offset;

	private final BufferedImage background;

	private boolean viewAsDm;

	public PanelMapBasic(final MapBean map, DrawFilter draw) {
		super();
		setLayout(null);
		this.background = HelpersImages.getImage(HelpersPath
				.getPathTexture("wood-texture.jpg"));

		this.playerDraw = draw;

		showGrid = true;
		drawables = new ArrayList<MouseDrawable>();
		drawableElements = new ArrayList<MapElementDrawable>();
		buttons = new ArrayList<ButtonDrawable>();

		this.map = map;
		this.map.addPropertyChangeListener(this);

		CampaignClient.getInstance().addWaitBeanListener(
				new WaitBeanListener() {
					@Override
					public UniqueID getBeanId() {
						return map.getFilter();
					}

					@Override
					public void beanReceived(BasicBean bean) {
						filterChanged();
					}
				});

		for (UniqueID elementId : this.map.getElements()) {
			MapElementDrawable drawable = new MapElementDrawable(elementId);
			drawable.addDrawableListener(this);
			drawableElements.add(drawable);
		}

		this.setOpaque(false);

		offset = 120;
		zoomFactor = 1.0;
		previousZoom = 0;
		refreshTime = new Timer(20, this);

		viewAsDm = CampaignClient.getInstance().getCurrentPlayer().isDm();

		zoom(zoomFactor);
	}

	private void filterChanged() {
		MapFilter filter = CampaignClient.getInstance().getBean(
				map.getFilter(), 15000);
		if (filter != null) {
			filter.addPropertyChangeListener(PanelMapBasic.this);
		}
	}

	public void setViewAsDm(boolean isDm) {
		// enable only if you are a dm
		if (CampaignClient.getInstance().getCurrentPlayer().isDm()) {
			viewAsDm = isDm;
		}

		this.revalidate();
		this.repaint();
	}

	public void addDrawable(MouseDrawable draw) {
		drawables.add(draw);
		refreshTime.start();

		this.revalidate();
		this.repaint();
	}

	public void removeDrawable(MouseDrawable draw) {
		drawables.remove(draw);
		if (drawables.isEmpty()) {
			refreshTime.stop();
		}
		this.revalidate();
		this.repaint();
	}

	public void removeAllDrawable() {
		drawables.clear();
		refreshTime.stop();

		this.revalidate();
		this.repaint();
	}

	public void showGrid(Boolean showGrid) {
		this.showGrid = showGrid;
		this.revalidate();
		this.repaint();
	}

	public Boolean getShowGrid() {
		return this.showGrid;
	}

	public Point2D.Double convertPointStandardToPanel(Point2D position) {
		double zoomFactor = getZoomFactor();
		return new Point2D.Double(position.getX() * zoomFactor, position.getY()
				* zoomFactor);
	}

	public Point convertPointPanelToStandard(Point click) {
		if (click == null) {
			return new Point();
		}
		double zoomFactor = getZoomFactor();
		return new Point((int) (click.x / zoomFactor),
				(int) (click.y / zoomFactor));
	}

	private BufferedImage img;
	private double previousZoom;

	protected void paintTexture(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();

		if (previousZoom != zoomFactor) {
			img = ImageUtil.resizeImage(background,
					(int) (background.getWidth() * zoomFactor),
					(int) (background.getHeight() * zoomFactor),
					ImageUtil.LOW_RESOLUTION);

			previousZoom = zoomFactor;
		}

		// Create a texture paint from the buffered image
		Rectangle r = new Rectangle(0, 0, img.getWidth(), img.getHeight());
		TexturePaint tp = new TexturePaint(img, r);

		// Add the texture paint to the graphics context.
		g2.setPaint(tp);

		// Create and render a rectangle filled with the texture.
		g2.fillRect(0, 0, getWidth(), getHeight());

		g2.dispose();
	}

	@Override
	public void paint(Graphics g) {

		Graphics2D gGlobal = (Graphics2D) g;

		paintTexture(gGlobal);

		// Anti-alias!
		gGlobal.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gGlobal.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		Graphics2D gMaps = (Graphics2D) gGlobal.create();

		// setup the position
		gMaps.translate(offset, offset);
		gMaps.scale(zoomFactor, zoomFactor);

		// draw background
		map.drawBackground(gMaps, 1.0);

		synchronized (drawableElements) {
			for (Drawable draw : drawableElements) {
				draw.draw(gMaps, 1.0, viewAsDm);
			}
		}

		if (showGrid) {
			map.drawGrid(gMaps, 1.0);
		}
		map.drawFilter(gMaps, 1.0, viewAsDm);

		// Draw other info
		for (MouseDrawable draw : drawables) {
			draw.draw(gMaps, convertMousePosition(getMousePosition()));
		}

		gMaps.dispose();
		// g2.translate(-offset, -offset);

		for (Drawable draw : buttons) {
			draw.draw(gGlobal, 1.0, viewAsDm);
		}
		playerDraw.draw(gGlobal);
		gGlobal.dispose();

		super.paint(g);
	}

	// convert using the offset
	protected Point convertMousePosition(Point mousePosition) {
		if (mousePosition == null) {
			return null;
		}
		return new Point(mousePosition.x - offset, mousePosition.y - offset);
	}

	/**
	 * this method return the map element in the map at the position p on the
	 * panel
	 * 
	 * @param p
	 *            - the position of the map element on the panel
	 * @return the map element
	 */
	public List<MapElement> getElementAt(Point p) {
		List<MapElement> elements = map.getElementsAt(p);

		if (!CampaignClient.getInstance().getCurrentPlayer().isDm()) {
			Iterator<MapElement> itt = elements.iterator();

			while (itt.hasNext()) {
				MapElement current = itt.next();
				if (current.isHiddenForPlayer()) {
					itt.remove();
				}
			}
		}
		return elements;
	}

	@Override
	public Double getZoomFactor() {
		return zoomFactor;
	}

	@Override
	public void zoom(double value) {
		zoomFactor = value;
		Dimension dim = new Dimension((int) (2 * offset + map.getWidth()
				* zoomFactor),
				(int) (2 * offset + map.getHeight() * zoomFactor));
		this.setPreferredSize(dim);
		redraw();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		redraw();
	}

	public void addButtonDrawable(ButtonDrawable draw) {
		this.buttons.add(draw);
		this.redraw();
	}

	public void addDrawableElementListener(DrawableMouseListener listener) {
		listenerList.add(DrawableMouseListener.class, listener);

		for (MapElementDrawable draw : drawableElements) {
			draw.addDrawableMouseListener(listener);
		}
	}

	private void addDrawable(MapElementDrawable draw) {
		synchronized (drawableElements) {
			drawableElements.add(draw);
		}
		draw.addDrawableListener(this);

		// add listener on the new drawable
		DrawableMouseListener[] listeners = this.listenerList
				.getListeners(DrawableMouseListener.class);
		for (DrawableMouseListener listener : listeners) {
			draw.addDrawableMouseListener(listener);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (MapBean.METH_ADD_ELEMENT_METHOD.equals(evt.getPropertyName())) {
			final UniqueID mapElementId = ((UniqueID) evt.getNewValue());
			addDrawable(new MapElementDrawable(mapElementId));
		} else if (MapBean.PROP_FILTER_PROPERTY.equals(evt.getPropertyName())) {
			Threads.execute(new MyRunnable() {
				@Override
				public void run() {
					filterChanged();
				}

				@Override
				public String getTaskName() {
					return "Wait for map filter";
				}
			});
		}
		redraw();
	}

	@Override
	public void redraw() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				revalidate();
				repaint();
			}
		});
	}

	@Override
	public void remove(Drawable drawable) {
		synchronized (drawableElements) {
			drawableElements.remove(drawable);
		}
		redraw();
	}
}
