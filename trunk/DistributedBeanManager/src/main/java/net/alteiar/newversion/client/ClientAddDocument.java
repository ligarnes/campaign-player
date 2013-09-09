package net.alteiar.newversion.client;

import java.io.IOException;

import net.alteiar.newversion.server.kryo.KryoInit;
import net.alteiar.newversion.server.kryo.NetworkKryoInit;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.newversion.shared.chunk.ChunkObjectSend;
import net.alteiar.newversion.shared.chunk.ChunkSendFactory;
import net.alteiar.newversion.shared.request.RequestObject;
import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class ClientAddDocument extends DocuManager {

	private final DocumentManager manager;
	private final ChunkSendFactory factory;
	private final Client client;

	private final Kryo serializer;

	public ClientAddDocument(DocumentManager manager, String ipServeur,
			int portTCP, KryoInit init) throws IOException {
		super(BYTE_TO_1MO * 10);
		this.manager = manager;

		client = new Client(BYTE_TO_1MO * 10, BYTE_TO_1MO * 10);
		client.start();
		client.connect(10000, ipServeur, portTCP, portTCP + 1);

		NetworkKryoInit newInit = new NetworkKryoInit();
		newInit.registerKryo(client.getKryo());
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

	public void createDocument(final BasicBean bean) {
		// System.out.println("send bean: " + bean);
		// System.out.println("send bean id: " + bean.getId());

		final ChunkObjectSend sended = factory.generateMessages(bean);
		sendObject(client, sended);
	}

	public void requestDocument(final UniqueID id) {
		client.sendTCP(new RequestObject(id));
	}

	@Override
	protected void objectReceived(IUniqueObject bean) {
		// System.out.println("bean received: " + bean);
		// System.out.println("bean received id: " + bean.getId());
		manager.documentAdded((BasicBean) bean);
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
		return factory.generateMessages(manager.getBean(id, -1));
	}

}
