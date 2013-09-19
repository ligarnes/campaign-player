package net.alteiar.beans.map.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.SuppressBeanListener;
import net.alteiar.WaitBeanListener;
import net.alteiar.WaitMultipleBeansListener;
import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.beans.map.filter.squaredMap.Array2D;
import net.alteiar.beans.map.filter.squaredMap.ImageInfo;
import net.alteiar.beans.media.ImageBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class CharacterMapFilter extends MapFilter implements /* KryoSerializable, */
PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	public static final String METH_ADD_ELEMENT_VIEW_METHOD = "addElementIdView";
	public static final String METH_REMOVE_ELEMENT_VIEW_METHOD = "removeElementView";

	public static final String METH_REVALIDATE_FILTER_METHOD = "revalidateFilter";

	public static final String METH_REVALIDATE_FILTER_IMAGE_METHOD = "revalidateFilterImage";

	public static final String PROP_MAX_VISION_PROPERTY = "maxVision";
	public static final String PROP_FILTERED_IMAGE_ID_PROPERTY = "filteredImageId";

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

	@Element(required = false)
	private UniqueID filteredImageId;

	// local info, DO NOT SAVE IT, IT TAKE MANY SPACE
	private transient ImageInfo squaredMap;

	/**
	 * The current vision map, this is computed each time we need it (but we
	 * don't want to create a new large table each time
	 */
	private transient Array2D visionMap;

	/**
	 * All of this is not really clean but enable good performance even on large
	 * map
	 */
	private transient BufferedImage filterImage;
	private transient boolean isDm;
	private transient boolean somethingChange;
	private transient boolean refreshVision;

	public CharacterMapFilter(MapBean map) {
		super(map.getId());
		elementsViews = new HashSet<UniqueID>();
		maxVision = 4;

		this.squaredMap = new ImageInfo(map);

		visionMap = new Array2D(squaredMap.getImageWidth(),
				squaredMap.getImageHeight(), HIDE_VALUE);

		somethingChange = false;
		refreshVision = true;
	}

	public CharacterMapFilter(UniqueID id, UniqueID mapID,
			HashSet<UniqueID> elements, UniqueID filteredImageId, int maxVision) {
		super(id, mapID);

		elementsViews = elements;
		this.filteredImageId = filteredImageId;
		this.maxVision = maxVision;

		somethingChange = false;
		refreshVision = true;

		unserialized();
	}

	public CharacterMapFilter() {
	}

	public void unserialized() {
		HashSet<UniqueID> ids = new HashSet<UniqueID>();

		if (filteredImageId != null) {
			ids.add(filteredImageId);
		}
		ids.add(getMapId());
		for (UniqueID uniqueID : elementsViews) {
			ids.add(uniqueID);
		}

		new WaitMultipleBeansListener(ids) {
			@Override
			public void beanReceived() {
				// Create all info given the map
				squaredMap = new ImageInfo(getMap());
				visionMap = new Array2D(getSquaredMap().getImageWidth(),
						getSquaredMap().getImageHeight(), HIDE_VALUE);

				// add listener on each element to view
				ArrayList<MapElement> elements = CampaignClient.getInstance()
						.getBeans(elementsViews);
				for (MapElement element : elements) {
					element.addPropertyChangeListener(
							MapElement.PROP_POSITION_PROPERTY,
							CharacterMapFilter.this);
				}

				try {
					revalidateFilter();
				} catch (IOException e) {
					Logger.getLogger(getClass()).warn(
							"Impossible de changer le filtre", e);
				} catch (IllegalArgumentException e) {
					Logger.getLogger(getClass()).error(e.getMessage(), e);
				}

				somethingChange = true;
				refreshFilterImageThread();
			}
		};
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();

		unserialized();

		somethingChange = false;
		refreshVision = true;
	}

	@Override
	public void beanRemoved() {
		refreshVision = false;
	}

	private ImageInfo getSquaredMap() {
		return squaredMap;
	}

	public HashSet<UniqueID> getViewer() {
		return elementsViews;
	}

	public UniqueID getFilteredImageId() {
		return filteredImageId;
	}

	public void setFilteredImageId(final UniqueID filteredImageId) {
		final UniqueID oldValue = this.filteredImageId;
		if (this.notifyRemote(PROP_FILTERED_IMAGE_ID_PROPERTY, oldValue,
				filteredImageId)) {
			this.filteredImageId = filteredImageId;

			CampaignClient.getInstance().addWaitBeanListener(
					new WaitBeanListener() {
						@Override
						public UniqueID getBeanId() {
							return filteredImageId;
						}

						@Override
						public void beanReceived(BasicBean bean) {
							try {
								revalidateFilter();
								refreshFilterImage();
								notifyLocal(PROP_FILTERED_IMAGE_ID_PROPERTY,
										oldValue, filteredImageId);
							} catch (IOException e) {
								Logger.getLogger(getClass()).warn(
										"Impossible de changer le filtre", e);
							} catch (IllegalArgumentException e) {
								Logger.getLogger(getClass()).error(
										e.getMessage(), e);
							}
						}
					});
		}
	}

	private void revalidateFilter() throws IOException {
		this.getSquaredMap().fill(CLEAR_VALUE);

		if (filteredImageId == null) {
			return;
		}

		BufferedImage filter = ImageBean.getImage(filteredImageId);

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

	public void addElementIdView(final UniqueID element) {
		if (notifyRemote(METH_ADD_ELEMENT_VIEW_METHOD, null, element)) {
			elementsViews.add(element);

			CampaignClient.getInstance().addSuppressBeanListener(
					new SuppressBeanListener() {
						@Override
						public UniqueID getBeanId() {
							return element;
						}

						@Override
						public void beanRemoved(BasicBean bean) {
							System.out.println("remove map element");
							removeElementView(element);
						}
					});

			CampaignClient.getInstance().addWaitBeanListener(
					new WaitBeanListener() {
						@Override
						public UniqueID getBeanId() {
							return element;
						}

						@Override
						public void beanReceived(BasicBean bean) {
							somethingChange = true;
							bean.addPropertyChangeListener(
									MapElement.PROP_POSITION_PROPERTY,
									CharacterMapFilter.this);
						}
					});
			notifyLocal(METH_ADD_ELEMENT_VIEW_METHOD, null, element);
		}
	}

	public void removeElementView(final UniqueID element) {
		if (notifyRemote(METH_REMOVE_ELEMENT_VIEW_METHOD, null, element)) {
			elementsViews.remove(element);
			somethingChange = true;
			notifyLocal(METH_REMOVE_ELEMENT_VIEW_METHOD, null, element);
		}
	}

	public int[] getImagePixels(double zoomFactor) {
		BufferedImage img = new BufferedImage(BufferedImage.TYPE_INT_ARGB,
				getMap().getWidth(), getMap().getHeight());
		return ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	private void refreshFilterImage() {
		visionMap.fill(HIDE_VALUE);

		computeVision();

		visionMap.replaceValues(VISIBLE_VALUE, VISIBLE_COLOR);

		if (isDm) {
			visionMap.replaceValues(HIDE_VALUE, HIDDEN_DM_COLOR);
		} else {
			visionMap.replaceValues(HIDE_VALUE, HIDDEN_PLAYER_COLOR);
		}

		BufferedImage oldValue = this.filterImage;
		if (filterImage != null) {
			synchronized (filterImage) {
				filterImage = visionMap.buildImage();
			}
		} else {
			filterImage = visionMap.buildImage();
		}

		notifyLocal(METH_REVALIDATE_FILTER_IMAGE_METHOD, oldValue, filterImage);
	}

	private void refreshFilterImageThread() {
		ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
			@Override
			public void run() {
				while (refreshVision) {
					if (somethingChange) {
						somethingChange = false;
						refreshFilterImage();
					}
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						Logger.getLogger(getClass()).info("Error during sleep",
								e);
					}
				}
			}

			@Override
			public String getTaskName() {
				return "Mise à jour du filtre sur la carte: " + getMapId();
			}
		});
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor, boolean isDm) {
		if (isDm != this.isDm) {
			this.isDm = isDm;
			this.somethingChange = true;
		}

		if (filterImage != null) {
			synchronized (filterImage) {
				g.drawImage(this.filterImage, 0, 0,
						(int) (getMap().getWidth() * zoomFactor),
						(int) (getMap().getHeight() * zoomFactor), null);

			}
		} else {
			drawDefault(g, zoomFactor, isDm);
		}

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

		for (int i = 0; i <= longest; i++) {

			addVisible(image, getSquaredMap().getSquareSize(), x, y,
					visibleColor);

			if (getSquaredMap().getValue(x, y) == BLOCK_VALUE) {
				return;
			}

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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (MapElement.PROP_POSITION_PROPERTY.equals(evt.getPropertyName())) {
			somethingChange = true;
		}
	}
}
