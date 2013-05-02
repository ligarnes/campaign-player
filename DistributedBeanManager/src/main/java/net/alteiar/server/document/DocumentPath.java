package net.alteiar.server.document;

import java.io.Serializable;

import net.alteiar.client.bean.BasicBean;

public class DocumentPath implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String path;

	public static String INVALID_FILE_CHARACTER_REPLACEMENT = "---";

	private static String validateFileName(String name) {
		String validFileName = name;

		validFileName = validFileName.replaceAll(":",
				INVALID_FILE_CHARACTER_REPLACEMENT);
		validFileName = validFileName.replaceAll("<",
				INVALID_FILE_CHARACTER_REPLACEMENT);
		validFileName = validFileName.replaceAll(">",
				INVALID_FILE_CHARACTER_REPLACEMENT);

		return validFileName;
	}

	public DocumentPath(String path, BasicBean bean) {
		this(path, bean.getId().toString());
	}

	public DocumentPath(String path, String name) {
		this.path = path;

		this.name = validateFileName(name);
	}

	public DocumentPath() {
		this.path = null;
		this.name = null;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public void setName(String name) {
		this.name = validateFileName(name);
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCompletePath() {
		return path + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentPath other = (DocumentPath) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
}
