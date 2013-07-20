package net.alteiar.map.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.map.MapBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.squaredMap.Array2D;
import net.alteiar.map.filter.squaredMap.ImageInfo;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class CharacterMapFilter extends MapFilter {
	private static final long serialVersionUID = 1L;

	public static final String METH_ADD_ELEMENT_VIEW_METHOD = "addElementIdView";
	public static final String METH_REMOVE_ELEMENT_VIEW_METHOD = "removeElementView";

	public static final String METH_REVALIDATE_FILTER_METHOD = "revalidateFilter";

	public static final String PROP_MAX_VISION_PROPERTY = "maxVision";

	private static int CLEAR_VALUE = 0;
	private static int BLOCK_VALUE = 1;

	private static int HIDE_VALUE = 0;
	private static int VISIBLE_VALUE = 1;

	private static int HIDDEN_DM_COLOR = (new Color(0f, 0f, 0f, 0.8f)).getRGB();
	private static int HIDDEN_PLAYER_COLOR = (new Color(0f, 0f, 0f, 1.0f))
			.getRGB();
	private static int VISIBLE_COLOR = (new Color(0f, 0f, 0f, 0.1f)).getRGB();

	@ElementList
	private HashSet<UniqueID> elementsViews;

	@Element
	private Integer maxVision;

	@Element
	private ImageInfo squaredMap;

	/**
	 * The current vision map, this is computed each time we need it (but we
	 * don't want to create a new large table each time
	 */
	private transient Array2D visionMap;

	public CharacterMapFilter(MapBean map) {
		super(map.getId());
		elementsViews = new HashSet<UniqueID>();
		maxVision = 4;

		this.squaredMap = new ImageInfo(map);

		visionMap = new Array2D(squaredMap.getImageWidth(),
				squaredMap.getImageHeight(), HIDE_VALUE);
	}

	public CharacterMapFilter() {

	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();

		visionMap = new Array2D(getSquaredMap().getImageWidth(),
				getSquaredMap().getImageHeight(), HIDE_VALUE);
	}

	private ImageInfo getSquaredMap() {
		return squaredMap;
	}

	public HashSet<UniqueID> getViewer() {
		return elementsViews;
	}

	public void setMapFilter(BufferedImage filter) {
		this.getSquaredMap().fill(CLEAR_VALUE);

		if (filter.getWidth() != getSquaredMap().getImageWidth()
				|| filter.getHeight() != getSquaredMap().getImageHeight()) {
			throw new IllegalArgumentException(
					"the image is not the same size as the map");
		}

		int blockingValue = Color.BLACK.getRGB();
		for (int x = 0; x < getSquaredMap().getImageWidth(); x++) {
			for (int y = 0; y < getSquaredMap().getImageHeight(); y++) {

				int rgb = filter.getRGB(x, y);

				if (rgb == blockingValue) {
					getSquaredMap().setValue(x, y, BLOCK_VALUE);
				} else {
					getSquaredMap().setValue(x, y, CLEAR_VALUE);
				}
			}
		}

		// we change the filter, now we need to notify everyone that a change
		// occur
		revalidateFilter(squaredMap);
	}

	public void revalidateFilter(ImageInfo info) {
		if (this.notifyRemote(METH_REVALIDATE_FILTER_METHOD, null, info)) {
			this.squaredMap = info;
			this.notifyLocal(METH_REVALIDATE_FILTER_METHOD, null, info);
		}
	}

	public Integer getMaxVision() {
		return maxVision;
	}

	public void setMaxVision(Integer maxVision) {
		Integer oldValue = maxVision;
		if (notifyRemote(PROP_MAX_VISION_PROPERTY, oldValue, maxVision)) {
			this.maxVision = maxVision;
			notifyLocal(PROP_MAX_VISION_PROPERTY, oldValue, maxVision);
		}
	}

	public void addElementView(MapElement element) {
		addElementIdView(element.getId());
	}

	public void addElementIdView(UniqueID element) {
		if (notifyRemote(METH_ADD_ELEMENT_VIEW_METHOD, null, element)) {
			elementsViews.add(element);
			notifyLocal(METH_ADD_ELEMENT_VIEW_METHOD, null, element);
		}
	}

	public void removeElementView(UniqueID element) {
		if (notifyRemote(METH_REMOVE_ELEMENT_VIEW_METHOD, null, element)) {
			elementsViews.remove(element);
			notifyLocal(METH_REMOVE_ELEMENT_VIEW_METHOD, null, element);
		}
	}

	public int[] getImagePixels(double zoomFactor) {
		BufferedImage img = new BufferedImage(BufferedImage.TYPE_INT_ARGB,
				getMap().getWidth(), getMap().getHeight());
		return ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor, boolean isDm) {
		visionMap.fill(HIDE_VALUE);

		computeVision();

		visionMap.replaceValues(VISIBLE_VALUE, VISIBLE_COLOR);

		if (isDm) {
			visionMap.replaceValues(HIDE_VALUE, HIDDEN_DM_COLOR);
		} else {
			visionMap.replaceValues(HIDE_VALUE, HIDDEN_PLAYER_COLOR);
		}

		BufferedImage img = visionMap.buildImage();
		g.drawImage(img, 0, 0, (int) (getMap().getWidth() * zoomFactor),
				(int) (getMap().getHeight() * zoomFactor), null);
	}

	private void computeVision() {
		ArrayList<MapElement> elements = CampaignClient.getInstance().getBeans(
				elementsViews);

		for (MapElement element : elements) {
			computeVision(visionMap, VISIBLE_VALUE, element.getCenterPosition());
		}
	}

	private void computeVision(Array2D image, int visibleColor, Point position) {
		int squareX = position.x;
		int squareY = position.y;

		int visionSquare = (int) ((maxVision + 0.5) * getMap().getScale()
				.getPixels());

		int minX = Math.max(0, squareX - visionSquare);
		int maxX = Math.min(getSquaredMap().getWidthSquare() - 1, squareX
				+ visionSquare);

		int minY = Math.max(0, squareY - visionSquare);
		int maxY = Math.min(getSquaredMap().getHeightSquare() - 1, squareY
				+ visionSquare);

		for (int x = minX; x < maxX; ++x) {
			line(image, position, new Point(x, minY), maxVision, visibleColor);
			line(image, position, new Point(x, maxY), maxVision, visibleColor);
		}

		for (int y = minY; y < maxY; ++y) {
			line(image, position, new Point(minX, y), maxVision, visibleColor);
			line(image, position, new Point(maxX, y), maxVision, visibleColor);
		}

		line(image, position, new Point(maxX, maxY), maxVision
				* getMap().getScale().getPixels(), visibleColor);
	}

	private void addVisible(Array2D image, int squareSize, int x, int y,
			int visibleColor) {
		int realX = x * squareSize;
		int realY = y * squareSize;

		for (int k = 0; k < squareSize; ++k) {
			for (int k1 = 0; k1 < squareSize; ++k1) {
				image.set(realX + k, realY + k1, visibleColor);
			}
		}
	}

	private void line(Array2D image, Point begin, Point end, int currentVision,
			int visibleColor) {
		int w = end.x - begin.x;
		int h = end.y - begin.y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;
			dx2 = 0;
		}

		int x = begin.x;
		int y = begin.y;
		int numerator = longest >> 1;

		// int prevX = x;
		// int prevY = y;

		// int cost = 0;
		// double total = 0;
		for (int i = 0; i <= longest; i++) {

			addVisible(image, getSquaredMap().getSquareSize(), x, y,
					visibleColor);

			if (getSquaredMap().getValue(x, y) == BLOCK_VALUE) {// Integer.MAX_VALUE)
				// {
				return;
			}
			/*
			 * int diff = Math.abs(x - prevX) + Math.abs(y - prevY); cost +=
			 * getSquaredMap().getValue(x, y) * diff; total += diff;
			 * 
			 * if (total > 0) { prevX = x; prevY = y;
			 * 
			 * double avg = cost / total; double dist = new Point(x,
			 * y).distance(begin) * avg;
			 * 
			 * 
			 * if (dist > currentVision) { return; }
			 * 
			 * }
			 */

			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}
	}
}
