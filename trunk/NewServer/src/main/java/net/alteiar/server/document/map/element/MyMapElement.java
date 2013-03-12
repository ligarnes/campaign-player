package net.alteiar.server.document.map.element;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class MyMapElement extends MapElementClient<IMyMapElementRemote> {
	private static final long serialVersionUID = 1L;

	private final MapElement object;

	public MyMapElement(IMyMapElementRemote remote) throws RemoteException {
		super(remote);
		this.object = remote.getObject();
		this.object.loadMapElement(this);
	}

	@Override
	public Double getWidth() {
		return object.getWidth();
	}

	@Override
	public Double getHeight() {
		return object.getHeight();
	}

	@Override
	public void draw(Graphics2D g2, double zoomFactor) {
		object.draw(g2, zoomFactor);
	}

	@Override
	public Boolean contain(Point p) {
		return object.contain(p);
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
	}
}
