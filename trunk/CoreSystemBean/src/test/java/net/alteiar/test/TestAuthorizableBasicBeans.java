package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.documents.AuthorizationBean.AuthorizationManagerException;
import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.test.map.TestMap;

import org.junit.Test;

public class TestAuthorizableBasicBeans extends BasicTest {

	private static int COUNT_AUTHORIZATION_CHANGED = 0;
	private static int COUNT_BEAN_CHANGED = 0;

	@Test(timeout = 5000)
	public void testAuthorizableBasicBeans() {
		DocumentImageBean autorizableBean = new DocumentImageBean();

		CampaignClient.getInstance().addNotPermaBean(autorizableBean);

		DocumentImageBean bean = CampaignClient.getInstance().getBean(
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
		bean.addUser(guid);
		bean.setPublic(true);
		sleep(5);
		assertTrue("Should contain the owner", bean.getOwners().contains(guid));
		assertTrue("Should contain the user", bean.getUsers().contains(guid));
		assertTrue("the bean should be public", bean.getPublic());

		try {
			bean.removeOwner(guid);
		} catch (AuthorizationManagerException e) {
			e.printStackTrace();
			fail("Should  be able to remove one of the owner");
		}
		bean.removeUser(guid);
		bean.setPublic(false);
		sleep(5);
		assertTrue("Should'nt contain the owner",
				!bean.getOwners().contains(guid));
		assertTrue("Should'nt contain the user", !bean.getUsers()
				.contains(guid));
		assertTrue("the bean should'nt be public", !bean.getPublic());

		HashSet<UniqueID> guids = new HashSet<UniqueID>();
		for (int i = 0; i < 100; ++i) {
			guids.add(new UniqueID());
		}
		bean.setOwners(guids);
		bean.setUsers(guids);

		sleep(10);

		assertTrue("the owners must have been set",
				bean.getOwners().equals(guids));
		assertTrue("the users must have been set", bean.getUsers()
				.equals(guids));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeanListener() {
		COUNT_AUTHORIZATION_CHANGED = 0;
		COUNT_BEAN_CHANGED = 0;

		DocumentImageBean autorizableBean = new DocumentImageBean();

		CampaignClient.getInstance().addNotPermaBean(autorizableBean);

		DocumentImageBean bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId(), 200);

		bean.addPropertyChangeListener(new AuthorizationAdapter() {
			@Override
			public void authorizationChanged(PropertyChangeEvent evt) {
				super.authorizationChanged(evt);
				COUNT_AUTHORIZATION_CHANGED++;
			}
		});

		bean.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				COUNT_BEAN_CHANGED++;
			}
		});
		bean.setImage(TestMap.createTransfertImage());

		sleep(10);
		assertEquals("The bean have changed", 1, COUNT_BEAN_CHANGED);
		assertEquals("The authorization have not changed", 0,
				COUNT_AUTHORIZATION_CHANGED);

		UniqueID guid = new UniqueID();
		bean.addOwner(guid);
		sleep(5);
		try {
			bean.removeOwner(guid);
		} catch (AuthorizationManagerException e) {
			e.printStackTrace();
			fail("must be able to remote the guid");
		}
		bean.addUser(guid);
		sleep(5);
		bean.removeUser(guid);
		sleep(5);
		bean.setPublic(true);
		sleep(5);
		bean.setPublic(false);
		sleep(5);
		HashSet<UniqueID> users = new HashSet<UniqueID>();
		users.add(new UniqueID());
		bean.setUsers(users);
		sleep(5);
		bean.setOwners(users);
		sleep(5);

		assertEquals("The authorization have not changed", 8,
				COUNT_AUTHORIZATION_CHANGED);
		assertEquals("The bean have changed", 9, COUNT_BEAN_CHANGED);
	}
}
