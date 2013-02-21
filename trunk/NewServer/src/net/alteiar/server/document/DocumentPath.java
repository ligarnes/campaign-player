package net.alteiar.server.document;

import java.io.Serializable;

public class DocumentPath implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Long currentGUID = 0L;

	private static final Long generateNextGUID() {
		long guid = currentGUID;
		currentGUID++;
		return guid;
	}

	private final Long id;
	private final String name;
	private final String path;

	public DocumentPath(String path, String name) {
		this.id = generateNextGUID();
		this.path = path;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getCompletePath() {
		return path + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentPath other = (DocumentPath) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
