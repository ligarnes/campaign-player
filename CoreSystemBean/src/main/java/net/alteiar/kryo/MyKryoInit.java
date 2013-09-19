package net.alteiar.kryo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.beans.chat.Chat;
import net.alteiar.beans.chat.Message;
import net.alteiar.beans.combatTraker.CombatTraker;
import net.alteiar.beans.dice.DiceRoller;
import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.Scale;
import net.alteiar.beans.map.filter.CharacterMapFilter;
import net.alteiar.beans.map.filter.ManualMapFilter;
import net.alteiar.beans.map.filter.MapFilter;
import net.alteiar.beans.map.filter.MyPolygonFilter;
import net.alteiar.beans.map.filter.NoMapFilter;
import net.alteiar.beans.map.filter.squaredMap.Array2D;
import net.alteiar.beans.map.filter.squaredMap.ImageInfo;
import net.alteiar.beans.media.ImageBean;
import net.alteiar.beans.notepad.Notepad;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.newversion.server.kryo.KryoInit;
import net.alteiar.player.Player;
import net.alteiar.shared.ColorWrapper;
import net.alteiar.utils.file.images.TransfertImage;

import com.esotericsoftware.kryo.Kryo;

public class MyKryoInit extends KryoInit {

	private final ArrayList<Class<?>[]> classes;

	public MyKryoInit() {
		classes = new ArrayList<>();
	}

	public void addPluginClasses(Class<?>[] plugin) {
		this.classes.add(plugin);
	}

	@Override
	public void registerKryo(Kryo kryo) {
		super.registerKryo(kryo);

		// custom serializer for CharacterMapFilter
		kryo.addDefaultSerializer(CharacterMapFilter.class,
				MapFilterSerializer.class);

		kryo.register(Notepad.class);
		kryo.register(Player.class);
		kryo.register(CombatTraker.class);
		kryo.register(ColorWrapper.class);
		kryo.register(BeanDirectory.class);

		kryo.register(HashSet.class);
		kryo.register(ArrayList.class);
		kryo.register(DiceRoller.class);

		kryo.register(Chat.class);

		kryo.register(Message.class);

		// map
		kryo.register(MapBean.class);
		kryo.register(ImageBean.class);
		kryo.register(TransfertImage.class);
		kryo.register(Scale.class);

		// map filter
		kryo.register(Array2D.class);
		kryo.register(ImageInfo.class);
		kryo.register(MapFilter.class);
		kryo.register(CharacterMapFilter.class);
		kryo.register(ManualMapFilter.class);
		kryo.register(MyPolygonFilter.class);
		kryo.register(NoMapFilter.class);

		kryo.register(URL.class);

		for (Class<?>[] plugin : classes) {
			for (Class<?> classPlugin : plugin) {
				kryo.register(classPlugin);
			}
		}
	}
}
