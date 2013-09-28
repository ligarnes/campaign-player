package net.alteiar.beans.map.filter.squaredMap;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serializable;
import java.util.Arrays;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;

public class BooleanArray2D implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private int width;

	@ElementArray
	private boolean[] array;

	public BooleanArray2D() {

	}

	public BooleanArray2D(BooleanArray2D copy) {
		array = Arrays.copyOf(copy.array, copy.array.length);
		width = copy.width;
	}

	public BooleanArray2D(int width, int height) {
		this.width = width;
		array = new boolean[width * height];
	}

	public BooleanArray2D(int width, int height, boolean value) {
		this(width, height);
		Arrays.fill(array, value);
	}

	public void setArray(boolean[] values) {
		if (array.length != values.length)
			throw new IndexOutOfBoundsException(
					"the array is not the same size");
		this.array = values;
	}

	public void fill(boolean value) {
		Arrays.fill(array, value);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return array.length / this.width;
	}

	public void set(int x, int y, boolean val) {
		array[x + y * width] = val;
	}

	public boolean get(int x, int y) {
		return array[x + y * width];
	}

	public boolean[] getArray() {
		return array;
	}

	public void replaceValues(boolean oldValue, boolean newValue) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == oldValue) {
				array[i] = newValue;
			}
		}
	}

	public BufferedImage buildImage(int colorTrue, int colorFalse) {
		BufferedImage img = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		int[] colors = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			if (array[i]) {
				colors[i] = colorTrue;
			} else {
				colors[i] = colorFalse;
			}
		}

		int[] outPixels = ((DataBufferInt) img.getRaster().getDataBuffer())
				.getData();
		System.arraycopy(colors, 0, outPixels, 0, colors.length);

		return img;
	}
}
