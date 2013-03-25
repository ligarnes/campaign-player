package net.alteiar.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.alteiar.CampaignClient;
import net.alteiar.factory.BeanFactory;
import net.alteiar.image.ImageBean;
import net.alteiar.player.Player;
import net.alteiar.sharedDocuments.AuthorizationBasicBeans.AuthorizationManagerException;

import org.junit.Test;

public class TestAuthorizableBasicBeans extends BasicTest {

	@Test(timeout = 5000)
	public void testAuthorizableBasicBeans() {
		ImageBean autorizableBean = new ImageBean();
		BeanFactory.createBean(autorizableBean);

		ImageBean bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId());

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
		bean.addOwner(-5L);
		sleep(10);
		try {
			bean.removeOwner(-5L);
		} catch (AuthorizationManagerException e) {
			e.printStackTrace();
			fail("Should  be able to remove one of the owner");
		}

		bean.addUser(-5L);
		bean.removeUser(-5L);
	}
}
