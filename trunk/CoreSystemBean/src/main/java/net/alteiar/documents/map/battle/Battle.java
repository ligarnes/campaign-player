package net.alteiar.documents.map.battle;

import java.awt.Graphics2D;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import net.alteiar.documents.map.Map;

public class Battle extends Map {
	@Attribute
	private static final long serialVersionUID = 1L;

	private static final String PROP_TURN_PROPERTY = "turn";
	
	@Element
	private Integer turn;

	public Battle(String name) {
		super(name);
		turn = 0;
	}

	// ///////////////// LOCAL METHODS ///////////////////////
	public void nextTurn() {
		this.setTurn(getTurn() + 1);
	}

	@Override
	public void drawGrid(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		Double squareSize = this.getScale().getPixels() * zoomFactor;

		Double width = this.getWidth() * zoomFactor;
		Double height = this.getHeight() * zoomFactor;

		for (double i = 0; i < width; i += squareSize) {
			g2.drawLine((int) i, 0, (int) i, height.intValue());
		}
		for (double i = 0; i < height; i += squareSize) {
			g2.drawLine(0, (int) i, width.intValue(), (int) i);
		}
		g2.dispose();
	}

	// ///////////////// BEAN METHODS ///////////////////////
	public Integer getTurn() {
		return turn;
	}

	public void setTurn(Integer turn) {
		Integer oldValue = this.turn;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_TURN_PROPERTY,
					oldValue, turn);
			this.turn = turn;
			propertyChangeSupport.firePropertyChange(PROP_TURN_PROPERTY,
					oldValue, turn);
		} catch (PropertyVetoException e) {
			// e.printStackTrace();
		}
	}
	
	@Override
	public void save(File f) throws Exception {
		Serializer serializer = new Persister();
		serializer.write(this, f);
	}

	@Override
	public void loadDocument(File f) throws IOException, Exception {
		Serializer serializer = new Persister();
		Battle temp= serializer.read(Battle.class, f);
		this.setId(temp.getId());
		this.setOwners(temp.getOwners());
		this.setPublic(temp.getPublic());
		this.setUsers(temp.getUsers());
		this.setName(temp.getName());
		this.setBackground(temp.getBackground());
		this.setElements(temp.getElements());
		this.setFilter(temp.getFilter());
		this.setHeight(temp.getHeight());
		this.setScale(temp.getScale());
		this.setWidth(temp.getWidth());
		this.turn=temp.getTurn();
	}
}
