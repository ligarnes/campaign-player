package net.alteiar.documents.map.battle;

import java.awt.Graphics2D;
import java.beans.PropertyVetoException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

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
	
}
