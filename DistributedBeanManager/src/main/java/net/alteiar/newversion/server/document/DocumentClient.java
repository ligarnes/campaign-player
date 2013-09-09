package net.alteiar.newversion.server.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.alteiar.newversion.client.DocumentManager;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.newversion.shared.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public class DocumentClient implements PropertyChangeListener {

	private BeanEncapsulator bean;
	private String filename;

	private final DocumentManager listener;

	public DocumentClient(DocumentManager manager) {
		this.listener = manager;
		filename = null;// = remote.getFilename();
	}

	public void remoteValueChanged(String propertyName, Object newValue,
			Long timestamp) {
		bean.valueChange(propertyName, newValue, timestamp);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		listener.remoteValueChanged(bean.getBean().getId(),
				evt.getPropertyName(), evt.getNewValue());

		/*
		 * // Property change ask remote
		 * ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
		 * 
		 * @Override public void run() { try {
		 * remote.setBeanValue(evt.getPropertyName(), (Serializable)
		 * evt.getNewValue()); } catch (RemoteException e) {
		 * Logger.getLogger(getClass()).error("Connexion perdu", e); } }
		 * 
		 * @Override public String getTaskName() { return evt.getPropertyName()
		 * + " change task"; } });
		 */

	}

	public UniqueID getId() {
		return bean.getId();
	}

	public BeanEncapsulator getBeanEncapsulator() {
		return this.bean;
	}

	public String getFilename() {
		return this.filename;
	}

	/**
	 * this method is call when the document is close on the server
	 */
	public final void remoteCloseDocument() {
		this.bean.beanRemoved();
		this.bean.removePropertyChangeListener(this);
	}

	public void loadDocument(BasicBean bean) {
		this.filename = DocumentIO.validateFilename(bean.getId().toString());

		this.bean = new BeanEncapsulator(bean);
		this.bean.addPropertyChangeListener(this);
	}

	public void save(String path) throws Exception {
		DocumentIO.saveDocument(bean.getBean(), path, getFilename());
	}
}
