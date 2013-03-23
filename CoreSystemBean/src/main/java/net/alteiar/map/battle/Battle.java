package net.alteiar.map.battle;

import java.beans.PropertyVetoException;

import net.alteiar.map.Map;

public class Battle extends Map {
	private static final long serialVersionUID = 1L;

	private static final String PROP_TURN_PROPERTY = "turn";

	private Integer turn;

	public Battle() {
		super();
	}

	public Battle(String name, Long background, Integer width, Integer height) {
		super(name, background, width, height);
		turn = 0;
	}

	// ///////////////// LOCAL METHODS ///////////////////////
	public void nextTurn() {
		this.setTurn(getTurn() + 1);
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
			e.printStackTrace();
		}
	}
}
