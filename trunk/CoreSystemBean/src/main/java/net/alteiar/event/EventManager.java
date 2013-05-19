package net.alteiar.event;

import java.beans.Beans;
import java.util.HashSet;
import java.util.Set;

import net.alteiar.CampaignClient;
import net.alteiar.client.DocumentManager;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.event.trigger.Trigger;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.shared.UniqueID;

public class EventManager implements DocumentManagerListener {

	private final DocumentManager manager;
	private final Set<UniqueID> triggers;

	public EventManager(DocumentManager manager) {
		this.manager = manager;
		manager.addBeanListenerClient(this);

		triggers = new HashSet<UniqueID>();
	}

	protected void initializeNewTrigger(Trigger trigger) {
		for (DocumentClient doc : manager.getDocuments()) {
			trigger.beanAdded(doc.getBeanEncapsulator().getBean());
		}
	}

	@Override
	public void beanAdded(BasicBean bean) {
		synchronized (triggers) {
			if (Beans.isInstanceOf(bean, Trigger.class)) {
				triggers.add(bean.getId());
				initializeNewTrigger((Trigger) bean);
			}

			for (UniqueID triggerId : triggers) {
				Trigger trigger = CampaignClient.getInstance().getBean(
						triggerId);
				if (trigger != null) {
					trigger.beanAdded(bean);
				}
			}
		}
	}

	@Override
	public void beanRemoved(BasicBean bean) {
		synchronized (triggers) {
			if (Beans.isInstanceOf(bean, Trigger.class)) {
				triggers.remove(bean.getId());
			}

			for (UniqueID triggerId : triggers) {
				Trigger trigger = CampaignClient.getInstance().getBean(
						triggerId);
				trigger.beanRemoved(bean);
			}
		}
	}
}
