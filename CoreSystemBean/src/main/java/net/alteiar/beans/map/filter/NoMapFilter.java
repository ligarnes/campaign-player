package net.alteiar.beans.map.filter;

import java.awt.Graphics2D;

public class NoMapFilter extends MapFilter {
	private static final long serialVersionUID = 1L;

	@Override
	public void draw(Graphics2D g, double zoomFactor, boolean isDm) {
		// do nothing
	}

}
