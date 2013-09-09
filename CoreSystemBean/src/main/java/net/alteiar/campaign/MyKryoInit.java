package net.alteiar.campaign;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.chat.Chat;
import net.alteiar.chat.Message;
import net.alteiar.combatTraker.CombatTraker;
import net.alteiar.dice.DiceRoller;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.map.MapBean;
import net.alteiar.map.Scale;
import net.alteiar.media.ImageBean;
import net.alteiar.newversion.server.kryo.KryoInit;
import net.alteiar.notepad.Notepad;
import net.alteiar.player.Player;
import net.alteiar.shared.ColorWrapper;
import net.alteiar.utils.file.images.TransfertImage;

import com.esotericsoftware.kryo.Kryo;

public class MyKryoInit extends KryoInit {

	@Override
	public void registerKryo(Kryo kryo) {
		super.registerKryo(kryo);

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

		kryo.register(URL.class);
	}
}
