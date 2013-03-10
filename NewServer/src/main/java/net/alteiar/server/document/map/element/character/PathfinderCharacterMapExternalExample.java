package net.alteiar.server.document.map.element.character;

public abstract class PathfinderCharacterMapExternalExample extends
		BasicCharacterMap {

	public PathfinderCharacterMapExternalExample(
			MapElementCharacterClient mapElement) {
		super(mapElement);
	}
	/*
	 * private final MapElementSizeSquare width; private final
	 * MapElementSizeSquare height;
	 * 
	 * public PathfinderCharacterMapExternalExample() { width = new
	 * MapElementSizeSquare(getCharacter().getIntValue( "square_width")); height
	 * = new MapElementSizeSquare(getCharacter().getIntValue( "square_height"));
	 * }
	 * 
	 * @Override public Double getWidth() { return width.getPixels(getScale());
	 * }
	 * 
	 * @Override public Double getHeight() { return
	 * height.getPixels(getScale()); }
	 * 
	 * @Override public void draw(Graphics2D g, double zoomFactor) { Graphics2D
	 * g2 = (Graphics2D) g.create(); BufferedImage background = getCharacter()
	 * .getImageValue("characterIcon");
	 * 
	 * int x = (int) (getX() * zoomFactor); int y = (int) (getY() * zoomFactor);
	 * int width = (int) (getWidth() * zoomFactor); int height = (int)
	 * (getHeight() * zoomFactor);
	 * 
	 * if (background != null) { g2.drawImage(background, x, y, width, height,
	 * null); } else { g2.fillRect(x, y, width, height); }
	 * 
	 * // Draw life bar // add 0.5 because cast round to lower bound int
	 * squareCount = Math.min(1, (int) ((width / (getScale().getPixels() *
	 * zoomFactor)) + 0.5));
	 * 
	 * // 5% of the character height
	 * g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	 * 0.7f)); int heightLife = Math.max((int) (5 * squareCount * zoomFactor),
	 * 5); int xLife = x; int yLife = y + height - heightLife; int widthLife =
	 * width;
	 * 
	 * Integer currentHp = getCharacter().getIntValue("currentHp"); Integer
	 * totalHp = getCharacter().getIntValue("totalHp");
	 * 
	 * Float ratio = Math.min(1.0f, currentHp / (float) totalHp); if (currentHp
	 * > 0) { Color hp = new Color(1.0f - ratio, ratio, 0); g2.setColor(hp);
	 * g2.fillRect(xLife, yLife, (int) (widthLife * ratio), heightLife);
	 * g2.setColor(Color.BLACK);
	 * 
	 * g2.setColor(Color.BLACK); g2.drawRect(xLife, yLife, widthLife - 1,
	 * heightLife - 1); }
	 * 
	 * // Highlight /* if (isHighlighted()) {
	 * g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	 * g2.setColor(Color.BLUE); Stroke org = g2.getStroke(); g2.setStroke(new
	 * BasicStroke(8)); g2.drawRect(0, 0, width.intValue(), height.intValue());
	 * g2.setStroke(org); }
	 *//*
		 * g2.dispose(); }
		 */
}
