package net.alteiar.newversion.shared.chunk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.alteiar.shared.IUniqueObject;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

public class ChunkObjectReceived {

	private final ByteArrayOutputStream datas;
	private String classname;

	public ChunkObjectReceived() {
		datas = new ByteArrayOutputStream();
	}

	public void addChunk(byte[] chunk) {
		try {
			datas.write(chunk);
		} catch (IOException e) {
			Logger.getLogger(getClass()).error(
					"impossible d'ajouter un chunk dans le stream", e);
		}
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	private byte[] getDatas() throws IOException {
		datas.close();
		return datas.toByteArray();
	}

	public <E extends IUniqueObject> E getBean(Kryo k) {
		E bean = null;

		try {
			Class<?> c = Class.forName(classname);
			ByteArrayInputStream stream = new ByteArrayInputStream(getDatas());
			Input input = new Input(stream);
			bean = (E) k.readObject(input, c);
			input.close(); // Also calls output.flush()
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bean;
	}
}