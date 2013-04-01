package net.alteiar.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.alteiar.CampaignClient;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.AuthorizationBean.AuthorizationManagerException;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;

import org.junit.Test;

public class TestAuthorizableBasicBeans extends BasicTest {

	private static class TestDocument extends AuthorizationBean {
		private static final long serialVersionUID = 1L;

		public TestDocument() {
			super();
		}
	}

	@Test(timeout = 5000)
	public void testAuthorizableBasicBeans() {
		TestDocument autorizableBean = new TestDocument();

		CampaignClient.getInstance().addBean(autorizableBean);

		TestDocument bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId(), 200);

		assertTrue(bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));

		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		Player player = CampaignClient.getInstance().getCurrentPlayer();
		try {
			bean.removeOwner(player.getId());
			fail("Should not be able to remove the only owner");
		} catch (AuthorizationManagerException e) {
		}

		UniqueID guid = new UniqueID();
		bean.addOwner(guid);
		sleep(5);
		assertTrue("Should contain the owner", bean.getOwners().contains(guid));
		try {
			bean.removeOwner(guid);
		} catch (AuthorizationManagerException e) {
			e.printStackTrace();
			fail("Should  be able to remove one of the owner");
		}
		sleep(5);
		assertTrue("Should'nt contain the owner",
				!bean.getOwners().contains(guid));

		bean.addUser(guid);
		sleep(5);
		assertTrue("Should contain the user", bean.getUsers().contains(guid));
		bean.removeUser(guid);
		sleep(5);
		assertTrue("Should'nt contain the user", !bean.getUsers()
				.contains(guid));
	}
}
