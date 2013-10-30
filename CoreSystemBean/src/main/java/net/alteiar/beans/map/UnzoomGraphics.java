package net.alteiar.beans.map;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class UnzoomGraphics {

	private final Graphics2D org;
	private final Graphics2D newGraph;

	private final double zoomFactor;
	private final AffineTransform orgTransform;

	public UnzoomGraphics(Graphics2D org) {
		this.org = org;
		orgTransform = org.getTransform();

		zoomFactor = orgTransform.getScaleX();
		org.setTransform(new AffineTransform());

		double translateX = orgTransform.getTranslateX();
		double translateY = orgTransform.getTranslateY();

		newGraph = (Graphics2D) org.create();
		newGraph.translate(translateX, translateY);

	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public Graphics2D generateGraphics() {
		return newGraph;
	}

	public void disposeGraphics() {
		newGraph.dispose();
		org.setTransform(orgTransform);
	}
}