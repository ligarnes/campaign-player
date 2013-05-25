package pathfinder.bean.spell;

import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Element;

public class UnitValue extends BasicBean {
	private static final long serialVersionUID = 1L;

	@Element(required = false)
	private String unit;

	@Element(required = false)
	private String value;

	public UnitValue() {

	}

	public UnitValue(String unit, String value) {
		super();
		this.unit = unit;
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}