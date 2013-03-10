package net.alteiar.server.document.map.element.character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.element.MapElementClient;

public class MapElementCharacterClient extends
		MapElementClient<IMapElementCharacterRemote> {
	private static final long serialVersionUID = 1L;

	private final Long characterClient;
	private final BasicCharacterMap characterDraw;

	public MapElementCharacterClient(IMapElementCharacterRemote remote)
			throws RemoteException {
		super(remote);

		characterClient = remote.getCharacterClient();

		characterDraw = new PathfinderCharacterMapSimple(this);
	}

	public CharacterClient getCharacter() {
		return (CharacterClient) CampaignClient.getInstance().getDocument(
				characterClient);
	}

	/*
	 * TODO public ICharacter getCharacter() { return (ICharacter)
	 * CampaignClient.getInstance().getDocument( characterClient); }
	 */

	@Override
	protected Scale getScale() {
		return super.getScale();
	}

	@Override
	public Double getWidth() {
		return characterDraw.getWidth();
	}

	@Override
	public Double getHeight() {
		return characterDraw.getHeight();
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor) {
		characterDraw.draw(g, zoomFactor);
		/*
		 * Graphics2D g2 = (Graphics2D) g.create(); BufferedImage background =
		 * getCharacter().getImage();
		 * 
		 * int x = (int) (getX() * zoomFactor); int y = (int) (getY() *
		 * zoomFactor); int width = (int) (getWidth() * zoomFactor); int height
		 * = (int) (getHeight() * zoomFactor);
		 * 
		 * // 1 case x 1 case if (background != null) { g2.drawImage(background,
		 * x, y, width, height, null); } else { g2.fillRect(x, y, width,
		 * height); }
		 * 
		 * // Draw life bar // add 0.5 because cast round to lower bound int
		 * squareCount = Math.min(1, (int) ((width / (getScale().getPixels() *
		 * zoomFactor)) + 0.5));
		 * 
		 * // 5% of the character height
		 * g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		 * 0.7f)); int heightLife = Math.max((int) (5 * squareCount *
		 * zoomFactor), 5); int xLife = x; int yLife = y + height - heightLife;
		 * int widthLife = width;
		 * 
		 * Integer currentHp = getCharacter().getCurrentHp(); Integer totalHp =
		 * getCharacter().getTotalHp();
		 * 
		 * Float ratio = Math.min(1.0f, currentHp / (float) totalHp); if
		 * (currentHp > 0) { Color hp = new Color(1.0f - ratio, ratio, 0);
		 * g2.setColor(hp); g2.fillRect(xLife, yLife, (int) (widthLife * ratio),
		 * heightLife); g2.setColor(Color.BLACK);
		 * 
		 * g2.setColor(Color.BLACK); g2.drawRect(xLife, yLife, widthLife - 1,
		 * heightLife - 1); }
		 * 
		 * // Highlight /* if (isHighlighted()) {
		 * g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		 * 1f)); g2.setColor(Color.BLUE); Stroke org = g2.getStroke();
		 * g2.setStroke(new BasicStroke(8)); g2.drawRect(0, 0, width.intValue(),
		 * height.intValue()); g2.setStroke(org); }
		 */
		// g2.dispose();*/
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
	}

	@Override
	public Boolean contain(Point p) {
		Rectangle2D rect = new Rectangle2D.Double(getX(), getY(), getWidth(),
				getHeight());
		return rect.contains(p);
	}
}
