package net.alteiar.newversion.client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import net.alteiar.newversion.server.document.DocumentClient;
import net.alteiar.newversion.server.document.DocumentIO;
import net.alteiar.newversion.server.document.DocumentLocal;
import net.alteiar.newversion.server.document.IDocument;
import net.alteiar.newversion.server.kryo.KryoInit;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.newversion.shared.message.MessageModifyValue;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;

public class DocumentManager {

	// Use to notify when a document is received for blocking access
	private static CountDownLatch counter = new CountDownLatch(0);

	private static synchronized CountDownLatch getCounterInstance() {
		if (counter.getCount() == 0) {
			counter = new CountDownLatch(1);
		}
		return counter;
	}

	private final HashSet<DocumentManagerListener> listeners;
	private final HashMap<UniqueID, IDocument> documents;

	private final String dirSpecific;
	private String specificPath;
	private final String globalPath;

	private final ClientGeneralDocument generalDocu;
	private final ClientAddDocument addDocu;
	private final ClientModifyDocument modDocu;

	public DocumentManager(String ipServeur, int portTCP, String dirSpecific,
			String globalPath, KryoInit init) throws IOException {
		documents = new HashMap<UniqueID, IDocument>();
		listeners = new HashSet<DocumentManagerListener>();

		this.dirSpecific = dirSpecific;
		this.globalPath = globalPath;

		generalDocu = new ClientGeneralDocument(this, ipServeur, portTCP);
		addDocu = new ClientAddDocument(this, ipServeur, portTCP + 2, init);
		modDocu = new ClientModifyDocument(this, ipServeur, portTCP + 4, init);

		generalDocu.askInitCampaign();
		ThreadPoolUtils.startClientThreadPool();
	}

	protected void initCampaign(String campaignName, UniqueID[] ids) {
		this.specificPath = campaignName;
		File dir = new File(getSpecificBeanPath());
		dir.mkdirs();

		// ask for each documents
		if (ids != null) {
			for (UniqueID id : ids) {
				loadDocument(id);
			}
		}
	}

	/**
	 * Load the document given a specific id we first try to load the document
	 * locally (global and specific)
	 * 
	 * @param guid
	 * @return
	 * @throws Exception
	 */
	private void loadDocument(UniqueID guid) {
		try {
			BasicBean localBean = searchBean(getSpecificPath(), guid);
			BasicBean globalBean = searchBean(getGlobalPath(), guid);
			if (localBean != null) {
				// load local bean if exist
				documentAdded(localBean);

			} else if (globalBean != null) {
				// load global bean if exist
				documentAdded(globalBean);
			} else {
				// load bean from the server
				addDocu.requestDocument(guid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addDocu.requestDocument(guid);
		}
	}

	public boolean isInitialized() {
		return specificPath != null;
	}

	public void stopClient() {
		generalDocu.stop();
		addDocu.stop();
		modDocu.stop();
	}

	/**
	 * Method use to send object to the server
	 * 
	 * @param objectId
	 * @param propertyName
	 * @param newValue
	 */
	public void remoteValueChanged(UniqueID objectId, String propertyName,
			Object newValue) {
		MessageModifyValue msg = new MessageModifyValue(objectId, propertyName,
				newValue);

		modDocu.modifyDocument(msg);
	}

	/**
	 * this method is call when a been is added from server
	 * 
	 * @param bean
	 */
	protected void documentAdded(BasicBean bean) {
		DocumentClient doc = new DocumentClient(this);
		doc.loadDocument(bean);

		addDocument(doc);
	}

	/**
	 * This method really add the document, it must be call only by
	 * documentAdded
	 * 
	 * @param document
	 */
	private void addDocument(IDocument document) {
		// add the document
		synchronized (documents) {
			this.documents.put(document.getId(), document);
		}

		// notify listener that a document is added
		for (DocumentManagerListener listener : getListeners()) {
			listener.beanAdded(document.getBeanEncapsulator().getBean());
		}

		// internal notify that a document is received
		getCounterInstance().countDown();
	}

	/**
	 * this method is call when a bean is modified from server
	 * 
	 * @param objId
	 * @param propertyName
	 * @param newValue
	 * @param timestamp
	 */
	protected void documentChanged(MessageModifyValue msg) {
		IDocument doc = documents.get(msg.getId());
		doc.remoteValueChanged(msg.getPropertyName(), msg.getNewValue(),
				msg.getTimestamp());
	}

	/**
	 * This method is call when a bean is deleted from server
	 * 
	 * @param msg
	 */
	protected void documentClosed(UniqueID guid) {
		IDocument doc = null;
		synchronized (documents) {
			doc = documents.remove(guid);
		}

		if (doc != null) {
			// notify listener that a document is added
			for (DocumentManagerListener listener : getListeners()) {
				listener.beanRemoved(doc.getBeanEncapsulator().getBean());
			}

			// internal notify that a document is received
			getCounterInstance().countDown();
			doc.remoteCloseDocument();
		}
	}

	public int getLocalDocumentCount() {
		return documents.size();
	}

	private BasicBean searchBean(String path, UniqueID id) {
		BasicBean beanFound = null;
		String filename = DocumentIO.validateFilename(id.toString());

		File found = searchFile(new File(path), filename);
		if (found != null) {
			try {
				beanFound = DocumentIO.loadBeanLocal(found);
			} catch (Exception e) {
				// Do not care if we cannot read the file it may be an error
				Logger.getLogger(getClass()).warn("", e);
			}
		}
		return beanFound;
	}

	protected File searchFile(File dir, String filename) {
		File found = null;
		Iterator<File> itt = Arrays.asList(dir.listFiles()).iterator();

		while (itt.hasNext() && found == null) {
			File file = itt.next();

			found = new File(file, filename);
			if (!found.exists()) {
				found = null;
			}
		}

		return found;
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBean> E getBean(UniqueID id, long timeout) {
		if (id == null) {
			throw new NullPointerException("the unique id must'nt be null");
		}
		IDocument doc = getDocument(id, -1L);

		if (doc == null) {
			// try to find it localy in campaign
			BasicBean bean = searchBean(getSpecificPath(), id);
			if (bean != null) {
				documentAdded(bean);
			} else {
				// try to find it globaly
				bean = searchBean(getGlobalPath(), id);
				if (bean != null) {
					// add to document in order to avoid search agains
					addDocument(new DocumentLocal(bean));
				}
			}
			doc = getDocument(id, timeout);
		}

		return doc == null ? null : (E) doc.getBeanEncapsulator().getBean();
	}

	public UniqueID[] getIds() {
		return documents.keySet().toArray(new UniqueID[documents.size()]);
	}

	protected IDocument getDocument(UniqueID id, Long timeout) {
		Long begin = System.currentTimeMillis();

		IDocument value = documents.get(id);

		Long current = System.currentTimeMillis();
		while (value == null && (current - begin) < timeout) {
			try {
				if (!getCounterInstance().await((timeout - (current - begin)),
						TimeUnit.MILLISECONDS)) {
					return null;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value = documents.get(id);
			current = System.currentTimeMillis();
		}
		return value;
	}

	/**
	 * Send a new document to the server and dispatch it to all players
	 * 
	 * @param bean
	 */
	public void createDocument(final BasicBean bean) {
		ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
			@Override
			public void run() {
				addDocu.createDocument(bean);
			}

			@Override
			public String getTaskName() {
				return "add document: " + bean;
			}
		});
	}

	public void deleteDocument(final UniqueID uniqueId) {
		ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
			@Override
			public void run() {
				generalDocu.closeDocument(uniqueId);
			}

			@Override
			public String getTaskName() {
				return "add document: " + uniqueId;
			}
		});
	}

	public void deleteDocument(final BasicBean bean) {
		deleteDocument(bean.getId());
	}

	public void saveLocal() throws Exception {
		synchronized (documents) {
			for (IDocument doc : documents.values()) {
				doc.save(getSpecificBeanPath());
			}
		}
	}

	public void saveGlobalBean(BasicBean bean) throws Exception {
		DocumentLocal doc = new DocumentLocal(bean);
		doc.save(getGlobalPath());
	}

	public String getSpecificBeanPath() {
		return dirSpecific + "/" + specificPath + "/beans";
	}

	public String getSpecificPath() {
		return dirSpecific + "/" + specificPath;
	}

	public String getGlobalPath() {
		return globalPath;
	}

	public void addBeanListenerClient(DocumentManagerListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeDocumentManagerClient(DocumentManagerListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	protected HashSet<DocumentManagerListener> getListeners() {
		HashSet<DocumentManagerListener> copy;
		synchronized (listeners) {
			copy = (HashSet<DocumentManagerListener>) listeners.clone();
		}
		return copy;
	}

}
