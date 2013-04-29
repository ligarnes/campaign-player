package net.alteiar.effectBean;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public class EffectSuite extends Effect {
	private static final long serialVersionUID = 1L;

	public static final String PROP_ADD_EFFECT_PROPERTY = "add_Effect";
	public static final String PROP_REMOVE_EFFECT_PROPERTY = "remove_Effect";

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	protected ArrayList<Effect> effects;

	public EffectSuite(MapElement shape, Boolean oneUse,
			Class<? extends BasicBeans> typeBean, UniqueID mapId) {
		super(shape, oneUse, typeBean, mapId);
		effects = new ArrayList<Effect>();
	}

	public void removeEffect(Effect e) {
		effects.remove(e);
	}

	@Override
	public void setMapId(UniqueID mapId) {
		super.setMapId(mapId);

		if (effects != null) {
			for (Effect e : effects) {
				e.setMapId(mapId);
			}
		}
	}

	public void addEffects(ArrayList<Effect> effet) {
		Effect oldValue = null;
		try {
			this.vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_ADD_EFFECT_PROPERTY, oldValue, effet);
			effects.addAll(effet);
			this.propertyChangeSupport.firePropertyChange(
					PROP_ADD_EFFECT_PROPERTY, oldValue, effet);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addEffect(Effect effet) {
		Effect oldValue = null;
		try {
			this.vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_ADD_EFFECT_PROPERTY, oldValue, effet);
			effects.add(effet);
			this.propertyChangeSupport.firePropertyChange(
					PROP_ADD_EFFECT_PROPERTY, oldValue, effet);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void drawElement(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();

		Integer strokeSize = STROKE_SIZE_LARGE;
		if (getWidthPixels() < 100 || getHeightPixels() < 100) {
			strokeSize = STROKE_SIZE_SMALL;
		}

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (Effect effect : effects) {
			this.drawLine(g2, zoomFactor, strokeSize, effect);
		}
		g2.dispose();

	}

	protected void drawLine(Graphics2D g, double zoomFactor,
			Integer strokeSize, Effect e) {
		Point p1 = this.getCenterPosition();
		Point p2 = e.getAreaOfEffect().getCenterPosition();
		Double strokeSizeMiddle = STROKE_SIZE_LARGE / 2.0;

		double x1 = p1.getX() * zoomFactor + strokeSizeMiddle;
		double y1 = p1.getY() * zoomFactor + strokeSizeMiddle;

		double x2 = p1.getX() * zoomFactor + strokeSizeMiddle;
		double y2 = p1.getY() * zoomFactor + strokeSizeMiddle;

		Shape s = new Line2D.Double(x1, y1, x2, y2);
		// Draw the element
		// g.setColor(this.getAreaOfEffect().getColor());
		g.setColor(Color.BLACK);
		g.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.fill(s);
	}

	@Override
	public void deleteEffect() {
		for (Effect e : effects) {
			e.deleteEffect();
		}
		this.deleteEffect();
	}

	@Override
	public void activate() {
		System.out.println("activation");
		for (Effect effect : effects) {
			effect.activate();
		}
	}

	@Override
	public void desactivate() {
		System.out.println("desactivation");
		for (Effect effect : effects) {
			effect.desactivate();
			if (effect.isOneUse()) {
				effects.remove(effect);
			}
		}
	}

}
