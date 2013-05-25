package pathfinder.bean.spell;

import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Element;

public class Reference extends BasicBean {
	private static final long serialVersionUID = 1L;

	@Element
	private String name;

	@Element
	private String url;

	public Reference() {

	}

	public Reference(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
