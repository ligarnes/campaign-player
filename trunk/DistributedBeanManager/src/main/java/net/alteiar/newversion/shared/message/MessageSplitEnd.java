package net.alteiar.newversion.shared.message;

import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;

public class MessageSplitEnd implements IUniqueObject {
	private UniqueID guid;
	private String classname;

	public MessageSplitEnd() {

	}

	public MessageSplitEnd(UniqueID guid, String classname) {
		this.guid = guid;
		this.classname = classname;
	}

	@Override
	public UniqueID getId() {
		return guid;
	}

	public String getClassname() {
		return classname;
	}

}
