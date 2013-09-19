package net.alteiar.beans.map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MapUtils {

	public static Rectangle2D getSquareRectangle(MapBean map, Point position) {
		Point pos = convertPointToSquare(map, position);

		int width = map.getScale().getPixels();

		return new Rectangle2D.Double(pos.x * width, pos.y * width, width,
				width);
	}

	public static Point convertPointToSquare(MapBean map, Point position) {
		Integer squareSize = map.getScale().getPixels();
		int x = (int) Math.floor(position.x / squareSize.floatValue());
		int y = (int) Math.floor(position.y / squareSize.floatValue());

		return new Point(x, y);
	}

	public static Point2D.Double convertSquareToPoint(MapBean map,
			Point position, double zoomFactor) {
		Double squareSize = map.getScale().getPixels() * zoomFactor;
		return new Point2D.Double(squareSize * position.x, squareSize
				* position.y);
	}
}
