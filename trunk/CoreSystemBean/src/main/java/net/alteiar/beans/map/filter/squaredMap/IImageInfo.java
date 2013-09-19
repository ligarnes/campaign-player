package net.alteiar.beans.map.filter.squaredMap;

public interface IImageInfo {
	int getSquareSize();

	/**
	 * convert the square position to the pixel position and return the value at
	 * that pixel
	 * 
	 * @param x
	 *            the square x
	 * @param y
	 *            the square y
	 * @return the pixel value corresponding to the square
	 */
	int getValueAt(int x, int y);

	/**
	 * 
	 * @param x
	 *            the pixel x
	 * @param y
	 *            the pixel y
	 * @return
	 */
	int getValue(int x, int y);

	int getWidthSquare();

	int getHeightSquare();

	int getImageWidth();

	int getImageHeight();

	// public abstract int[] getValues(TransformationFunction fct);

	// public abstract void getValues(int[] vals, TransformationFunction fct);
}
