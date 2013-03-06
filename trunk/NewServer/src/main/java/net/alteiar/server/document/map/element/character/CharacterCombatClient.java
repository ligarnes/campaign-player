package net.alteiar.server.document.map.element.character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.element.MapElementClient;
import net.alteiar.server.document.map.element.size.MapElementSizeSquare;

public class CharacterCombatClient extends
		MapElementClient<ICharacterElementRemote> {
	private static final long serialVersionUID = 1L;

	private final Long characterClient;
	private final MapElementSizeSquare width;
	private final MapElementSizeSquare height;

	public CharacterCombatClient(ICharacterElementRemote remote)
			throws RemoteException {
		super(remote);

		characterClient = remote.getCharacterClient();

		width = new MapElementSizeSquare(1.0);
		height = new MapElementSizeSquare(1.0);
	}

	public CharacterClient getCharacter() {
		return (CharacterClient) CampaignClient.getInstance().getDocument(
				characterClient);
	}

	@Override
	public Double getWidth() {
		return width.getPixels(getScale());
	}

	@Override
	public Double getHeight() {
		return height.getPixels(getScale());
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		BufferedImage background = getCharacter().getImage();

		int x = (int) (getX() * zoomFactor);
		int y = (int) (getY() * zoomFactor);
		int width = (int) (getWidth() * zoomFactor);
		int height = (int) (getHeight() * zoomFactor);

		// 1 case x 1 case
		if (background != null) {
			g2.drawImage(background, x, y, width, height, null);
		} else {
			g2.fillRect(x, y, width, height);
		}
		g2.dispose();
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
