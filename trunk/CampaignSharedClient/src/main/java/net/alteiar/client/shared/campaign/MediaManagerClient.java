package net.alteiar.client.shared.campaign;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.observer.ProxyClientObservableSimple;
import net.alteiar.server.shared.campaign.IMediaManagerRemote;
import net.alteiar.server.shared.observer.campaign.IMediaObserverRemote;
import net.alteiar.thread.Task;

public class MediaManagerClient extends
		ProxyClientObservableSimple<IMediaManagerRemote> implements
		IMediaManagerClient {
	private static final long serialVersionUID = 1L;
	private final HashMap<Long, BufferedImage> images;
	private BufferedImage defaultImage;

	public MediaManagerClient(IMediaManagerRemote remote) {
		super(remote);
		images = new HashMap<Long, BufferedImage>();
		try {
			defaultImage = ImageIO.read(getClass().getResourceAsStream(
					"/net/alteiar/client/shared/campaign/Male.png"));
		} catch (IOException e) {
			ExceptionTool.showError(e);
		}

		try {
			remote.addMediaManagerListener(new MediaObserver());
			CampaignClient.WORKER_POOL_CAMPAIGN
					.addTask(new MediaManagerLoadTask(this, remoteObject));
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public BufferedImage getImage(Long guid) {
		return images.get(guid);
	}

	@Override
	public BufferedImage getDefaultImage() {
		return defaultImage;
	}

	protected void addRemoteImage(Long key, BufferedImage img) {
		images.put(key, img);
		this.notifyListeners();
	}

	private class MediaObserver extends UnicastRemoteObject implements
			IMediaObserverRemote {
		private static final long serialVersionUID = 1L;

		protected MediaObserver() throws RemoteException {
			super();
		}

		@Override
		public void mediaAdded(final Long guid) throws RemoteException {
			CampaignClient.WORKER_POOL_CAMPAIGN.addTask(new Task() {
				@Override
				public void run() {
					try {
						BufferedImage img = remoteObject.getImage(guid)
								.restoreImage();
						addRemoteImage(guid, img);
					} catch (RemoteException e) {
						ExceptionTool.showError(e);
					}
				}

				@Override
				public String getStartText() {
					return "downloading image";
				}

				@Override
				public String getFinishText() {
					return "image downloaded";
				}
			});
		}

		@Override
		public void mediaRemoved(Long guid) throws RemoteException {

		}

	}
}
