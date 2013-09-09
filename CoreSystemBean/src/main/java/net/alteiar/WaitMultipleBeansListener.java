package net.alteiar;

import java.util.Collection;
import java.util.HashSet;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

public abstract class WaitMultipleBeansListener {
	private final HashSet<UniqueID> waitAll;

	public WaitMultipleBeansListener(Collection<UniqueID> beans) {
		waitAll = new HashSet<UniqueID>(beans);

		for (UniqueID uniqueID : beans) {
			CampaignClient.getInstance().addWaitBeanListener(
					new SingleWaitBeanListener(uniqueID));
		}
	}

	public void oneBeanReceived(BasicBean bean) {
		waitAll.remove(bean.getId());
		if (waitAll.isEmpty()) {
			beanReceived();
		}
	}

	public abstract void beanReceived();

	private class SingleWaitBeanListener implements WaitBeanListener {
		private final UniqueID id;

		public SingleWaitBeanListener(UniqueID id) {
			this.id = id;
		}

		@Override
		public UniqueID getBeanId() {
			return id;
		}

		@Override
		public void beanReceived(BasicBean bean) {
			oneBeanReceived(bean);
		}
	}
}