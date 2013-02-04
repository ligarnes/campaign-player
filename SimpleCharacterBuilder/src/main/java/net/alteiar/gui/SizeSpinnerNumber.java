package net.alteiar.gui;

import javax.swing.AbstractSpinnerModel;

public class SizeSpinnerNumber extends AbstractSpinnerModel {
	private static final long serialVersionUID = 1L;
	private Float value;

	public SizeSpinnerNumber() {
		this.value = Float.valueOf(1.0F);
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		if ((value == null) || (!(value instanceof Float))) {
			throw new IllegalArgumentException("illegal value");
		}
		if (!value.equals(this.value)) {
			this.value = ((Float) value);
			fireStateChanged();
		}
	}

	@Override
	public Float getNextValue() {
		Float next = this.value;
		if (this.value.floatValue() >= 1.0F)
			next = Float.valueOf(next.floatValue() + 1.0F);
		else {
			next = Float.valueOf(next.floatValue() + 0.1F);
		}
		return next;
	}

	@Override
	public Float getPreviousValue() {
		Float next = this.value;
		if (this.value.doubleValue() <= 1.0D)
			next = Float.valueOf(next.floatValue() - 0.1F);
		else {
			next = Float.valueOf(next.floatValue() - 1.0F);
		}
		return next.floatValue() > 0.0F ? next : null;
	}
}
