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
package net.alteiar.campaign.player.gui.tools;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * @author TERAI Atsuhiro
 * @link 
 *       http://java-swing-tips.blogspot.com/2008/06/mouse-drag-auto-scrolling.html
 * 
 */
public class ComponentDragScrollListener extends Observable implements
		MouseListener, MouseMotionListener {
	protected final Cursor defaultCursor;
	private final Cursor hc = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	private final Point startPt = new Point();

	private final JViewport viewport;

	public ComponentDragScrollListener(JViewport viewport, Cursor cursor) {
		this.defaultCursor = cursor;
		this.viewport = viewport;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		JComponent jc = (JComponent) e.getSource();
		Point cp = SwingUtilities.convertPoint(jc, e.getPoint(), viewport);
		int dx = startPt.x - cp.x;
		int dy = startPt.y - cp.y;
		Point vp = viewport.getViewPosition();
		vp.translate(dx, dy);
		jc.scrollRectToVisible(new Rectangle(vp, viewport.getSize()));
		startPt.setLocation(cp);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JComponent jc = (JComponent) e.getSource();
		jc.setCursor(hc);
		Point cp = SwingUtilities.convertPoint(jc, e.getPoint(), viewport);
		startPt.setLocation(cp);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((JComponent) e.getSource()).setCursor(defaultCursor);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		((JComponent) e.getSource()).setCursor(defaultCursor);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
}
