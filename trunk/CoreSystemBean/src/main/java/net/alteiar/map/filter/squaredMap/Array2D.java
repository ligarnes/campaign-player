package net.alteiar.map.filter.squaredMap;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Arrays;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;

public class Array2D implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private int width;

	@ElementArray
	private int[] array;

	public Array2D() {

	}

	public Array2D(Array2D copy) {
		array = Arrays.copyOf(copy.array, copy.array.length);
		width = copy.width;
	}

	public Array2D(int width, int height) {
		this.width = width;
		array = new int[width * height];
	}

	public Array2D(int width, int height, int value) {
		this(width, height);
		Arrays.fill(array, value);
	}

	public void setArray(int[] values) {
		if (array.length != values.length)
			throw new IndexOutOfBoundsException(
					"the array is not the same size");
		this.array = values;
	}

	public void fill(int value) {
		Arrays.fill(array, value);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return array.length / this.width;
	}

	public void set(int x, int y, int val) {
		array[x + y * width] = val;
	}

	public int get(int x, int y) {
		return array[x + y * width];
	}

	public int[] getArray() {
		return array;
	}

	public void replaceValues(int oldValue, int newValue) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == oldValue) {
				array[i] = newValue;
			}
		}
	}

	public void print() {
		int height = array.length / width;
		for (int j = 0; j < height; ++j) {
			for (int i = 0; i < width; ++i) {
				System.out.print(get(i, j));
			}
			System.out.println();
		}
		System.out.println();
	}

	public BufferedImage buildImage() {
		BufferedImage img = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		img.setRGB(0, 0, getWidth(), getHeight(), array, 0, getWidth());

		return img;
	}
}
