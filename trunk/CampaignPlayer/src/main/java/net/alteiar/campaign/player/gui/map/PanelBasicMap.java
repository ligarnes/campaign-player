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
package net.alteiar.campaign.player.gui.map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.alteiar.CampaignClient;
import net.alteiar.WaitBeanListener;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.drawable.mouse.MouseDrawable;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.shared.UniqueID;
import net.alteiar.zoom.Zoomable;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PanelBasicMap extends JPanel implements PropertyChangeListener,
		Zoomable, ActionListener {
	private static final long serialVersionUID = -5027864086357387475L;

	protected final Map map;
	private final List<MouseDrawable> drawables;
	private final DrawInfo playerDraw;
	private Double zoomFactor;
	private Boolean showGrid;

	private final Timer refreshTime;

	public PanelBasicMap(Map map, DrawInfo draw) {
		super();
		this.playerDraw = draw;

		showGrid = true;
		drawables = new ArrayList<MouseDrawable>();

		this.map = map;
		this.map.addPropertyChangeListener(this);
		MapFilter filter = CampaignClient.getInstance().getBean(
				this.map.getFilter());
		filter.addPropertyChangeListener(this);

		for (UniqueID elementId : this.map.getElements()) {
			CampaignClient.getInstance().getBean(elementId)
					.addPropertyChangeListener(this);
		}

		this.setOpaque(false);

		zoomFactor = 0.50;

		refreshTime = new Timer(20, this);

		Dimension dim = new Dimension((int) (map.getWidth() * zoomFactor),
				(int) (map.getHeight() * zoomFactor));
		this.setPreferredSize(dim);
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

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Anti-alias!
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// draw background
		map.drawBackground(g2, zoomFactor);
		map.drawElements(g2, zoomFactor);
		if (showGrid) {
			map.drawGrid(g2, zoomFactor);
		}
		map.drawFilter(g2, zoomFactor);

		// Draw other info
		for (MouseDrawable draw : drawables) {
			draw.draw(g2, getMousePosition());
		}

		playerDraw.draw(g2);
		g2.dispose();
	}

	/**
	 * this method return the map element in the map at the position p on the
	 * panel
	 * 
	 * @param p
	 *            - the position of the map element on the panel
	 * @return the map element
	 */
	public MapElement getElementAt(Point p) {
		Collection<MapElement> elements = map.getElementsAt(p);
		// return the first element of the list
		return elements.size() > 0 ? elements.iterator().next() : null;
	}

	@Override
	public Double getZoomFactor() {
		return zoomFactor;
	}

	@Override
	public void zoom(double value) {
		zoomFactor = value;
		Dimension dim = new Dimension((int) (map.getWidth() * zoomFactor),
				(int) (map.getHeight() * zoomFactor));
		this.setPreferredSize(dim);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.revalidate();
		this.repaint();
	}

	private void mapChanged() {
		Dimension dim = new Dimension((int) (map.getWidth() * zoomFactor),
				(int) (map.getHeight() * zoomFactor));
		this.setPreferredSize(dim);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (Map.METH_ADD_ELEMENT_METHOD.equals(evt.getPropertyName())) {
			final UniqueID mapElementId = ((UniqueID) evt.getNewValue());

			CampaignClient.getInstance().addWaitBeanListener(
					new WaitBeanListener() {
						@Override
						public UniqueID getBeanId() {
							return mapElementId;
						}

						@Override
						public void beanReceived(BasicBeans bean) {
							bean.addPropertyChangeListener(PanelBasicMap.this);
							mapChanged();
						}
					});
		} else if (Map.METH_REMOVE_ELEMENT_METHOD.equals(evt.getPropertyName())) {
			UniqueID mapElementId = ((UniqueID) evt.getNewValue());
			MapElement mapElement = CampaignClient.getInstance().getBean(
					mapElementId);
			mapElement.removePropertyChangeListener(this);
			mapChanged();
		} else {
			mapChanged();
		}
	}
}
