package net.alteiar.newversion.client;

import java.io.IOException;

import net.alteiar.newversion.server.kryo.KryoInit;
import net.alteiar.newversion.server.kryo.NetworkKryoInit;
import net.alteiar.newversion.shared.chunk.ChunkObjectSend;
import net.alteiar.newversion.shared.chunk.ChunkSendFactory;
import net.alteiar.newversion.shared.message.MessageModifyValue;
import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class ClientModifyDocument extends DocuManager {

	private final Kryo serializer;

	private final DocumentManager manager;
	private final ChunkSendFactory factory;
	private final Client client;

	public ClientModifyDocument(DocumentManager manager, String ipServeur,
			int portTCP, KryoInit init) throws IOException {
		super(BYTE_TO_1MO);

		this.manager = manager;

		client = new Client(BYTE_TO_1MO, BYTE_TO_1MO);
		client.start();
		client.connect(10000, ipServeur, portTCP, portTCP + 1);

		NetworkKryoInit newInit = new NetworkKryoInit();
		newInit.registerKryo(client.getKryo());

		// init.registerKryo(client.getKryo());
		client.addListener(this);

		serializer = new Kryo();
		init.registerKryo(serializer);
		factory = new ChunkSendFactory(serializer);
	}

	@Override
	public void stop() {
		client.close();
		client.stop();
	}

	public void modifyDocument(final MessageModifyValue bean) {
		final ChunkObjectSend sended = factory.generateMessages(bean);

		sendObject(client, sended);
	}

	@Override
	protected void objectReceived(IUniqueObject bean) {
		manager.documentChanged((MessageModifyValue) bean);
	}

	@Override
	protected Kryo getKryo() {
		return serializer;
	}

	@Override
	protected int getMaxChunkSize() {
		return factory.getChunkSize();
	}

	@Override
	protected ChunkObjectSend getObjectSend(UniqueID id) {
		return null;
	}

}
