package net.alteiar.map.filter.squaredMap;

import java.io.Serializable;

import net.alteiar.map.MapBean;

import org.simpleframework.xml.Element;

public class ImageInfo implements IImageInfo, Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private Array2D maps;

	@Element
	private int squareSize;

	public ImageInfo() {

	}

	public ImageInfo(ImageInfo info) {
		this.maps = new Array2D(info.maps);
		this.squareSize = info.squareSize;
	}

	public ImageInfo(MapBean img) {
		maps = new Array2D(img.getWidth(), img.getHeight(), 0);
		squareSize = 1;
	}

	public void fill(int value) {
		maps.fill(value);
	}

	@Override
	public int getSquareSize() {
		return squareSize;
	}

	@Override
	public int getWidthSquare() {
		return maps.getWidth();
	}

	@Override
	public int getHeightSquare() {
		return maps.getHeight();
	}

	@Override
	public int getImageWidth() {
		return getSquareSize() * getWidthSquare();
	}

	@Override
	public int getImageHeight() {
		return getSquareSize() * getHeightSquare();
	}

	/**
	 * 
	 * @param x
	 *            - x position on original image
	 * @param y
	 *            - y position on original image
	 * @param value
	 */
	public void setValueAt(int x, int y, int value) {
		int blockX = x / squareSize;
		int blockY = y / squareSize;

		setValue(blockX, blockY, value);
	}

	public void setValueAt(int x, int y, int width, int height, int value) {
		int blockX = x / squareSize;
		int blockY = y / squareSize;

		setValue(blockX, blockY, width, height, value);
	}

	@Override
	public int getValueAt(int x, int y) {
		int blockX = x / squareSize;
		int blockY = y / squareSize;
		return getValue(blockX, blockY);
	}

	@Override
	public int getValue(int x, int y) {
		return maps.get(x, y);
	}

	/**
	 * 
	 * @param x
	 *            - x position on orginal image
	 * @param y
	 *            - y position on original image
	 * @return the value of the block at the original image position
	 */
	public void setValue(int x, int y, int value) {
		maps.set(x, y, value);
	}

	public void setValue(final int xBegin, final int yBegin, final int width,
			final int height, final int value) {
		for (int x = xBegin; x < (xBegin + width); x++) {
			for (int y = yBegin; y < (yBegin + height); y++) {
				maps.set(x, y, value);
			}
		}
	}
}
