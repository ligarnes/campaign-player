package net.alteiar.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.server.document.DocumentLoader;
import net.alteiar.server.document.DocumentPath;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.IDocumentRemoteListener;
import net.alteiar.shared.UniqueID;

public class DocumentClient implements Serializable, PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final IDocumentRemote remote;
	private transient IDocumentRemoteListener documentListener;

	private BeanEncapsulator bean;
	private DocumentPath path;
	private Boolean perma=false; 
	
	public DocumentClient(IDocumentRemote remote) throws RemoteException {
		this.remote = remote;
	}

	public void remoteValueChanged(String propertyName, Object newValue) {
		bean.valueChange(propertyName, newValue);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			remote.setBeanValue(evt.getPropertyName(), evt.getNewValue());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UniqueID getId() {
		return bean.getId();
	}

	public BeanEncapsulator getBeanEncapsulator() {
		return this.bean;
	}

	public DocumentPath getDocumentPath() {
		return this.path;
	}

	private final void remoteCloseDocument() {
		try {
			this.bean.beanRemoved();
			this.bean.removePropertyChangeListener(this);
			this.remote.removeDocumentListener(documentListener);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadDocument() throws Exception {
		DocumentPath documentPath = null;
		try {
			documentListener = new DocumentListener();

			this.remote.addDocumentListener(documentListener);
			documentPath = remote.getPath();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// localPath = the local scenario directory
		// path = localPath + getDocumentPath().getCompletePath();

		File localFile = new File(documentPath.getCompletePath());
		try {
			if (localFile.exists()) {
				// load local bean
				DocumentLoader.loadDocumentLocal(localFile);
			} else {
				loadDocumentRemote();
			}
			bean.addPropertyChangeListener(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void loadDocumentRemote() throws Exception {
		bean = this.remote.getBean();
		if(this.remote.isPerma())
		{
			this.savePerma(remote.getPath().getName());
		}
	}
	
	public void saveLocal() throws Exception
	{
		System.out.println("bean="+bean.getBean());
		System.out.println("documentPath="+path);
		System.out.println("documentPath name="+path.getName());
		System.out.println("documentPath path="+path.getPath());
		String path=DocumentLoader.SaveDocument(bean.getBean(),this.path.getPath(),this.path.getName()+bean.getBean().getId().toString()+".xml");
		this.path.setPath(path);
	}

	public void savePerma(String name) throws Exception
	{
			String path=DocumentLoader.SaveDocument(bean.getBean(),DocumentPath.permaPath,name);
			this.path.setName(name);
			this.path.setPath(path);
			perma=true;
			
	}
	
	public Boolean isPerma()
	{
		return perma;
	}
	
	private class DocumentListener extends UnicastRemoteObject implements
			IDocumentRemoteListener {
		private static final long serialVersionUID = 1L;

		protected DocumentListener() throws RemoteException {
			super();
		}

		@Override
		public void beanValueChanged(String propertyName, Object newValue)
				throws RemoteException {
			remoteValueChanged(propertyName, newValue);
		}
		
		public void savePermaListener(String name)
				throws RemoteException {
			try {
				savePerma(name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void documentClosed() throws RemoteException {
			remoteCloseDocument();
		}
	}
}
