package net.alteiar.kryo;

import java.util.HashSet;

import net.alteiar.beans.map.filter.CharacterMapFilter;
import net.alteiar.shared.UniqueID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class MapFilterSerializer extends Serializer<CharacterMapFilter> {
	@Override
	@SuppressWarnings("unchecked")
	public CharacterMapFilter read(Kryo kryo, Input in,
			Class<CharacterMapFilter> classObj) {

		UniqueID id = kryo.readObject(in, UniqueID.class);
		UniqueID mapID = kryo.readObject(in, UniqueID.class);
		HashSet<UniqueID> elements = kryo.readObject(in, HashSet.class);
		UniqueID filteredImageId = kryo.readObjectOrNull(in, UniqueID.class);
		Integer maxVision = kryo.readObject(in, Integer.class);

		return new CharacterMapFilter(id, mapID, elements, filteredImageId,
				maxVision);
	}

	@Override
	public void write(Kryo kryo, Output out, CharacterMapFilter obj) {

		kryo.writeObject(out, obj.getId());
		kryo.writeObject(out, obj.getMapId());
		kryo.writeObject(out, obj.getViewer());
		kryo.writeObjectOrNull(out, obj.getFilteredImageId(), UniqueID.class);
		kryo.writeObject(out, obj.getMaxVision());
		out.flush();
	}

}