package net.alteiar.test.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.documents.image.DocumentImageBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.test.NewCampaignTest;
import net.alteiar.test.beans.map.TestMap;

import org.junit.Test;

public class TestAuthorizableBasicBeans extends NewCampaignTest {

	private static int COUNT_AUTHORIZATION_CHANGED = 0;
	private static int COUNT_BEAN_CHANGED = 0;

	@Test(timeout = 5000)
	public void testAuthorizableBeansName() {
		String expName = "test-document-name";
		String newExpName = "test-document-name-new";
		DocumentImageBean autorizableBean = new DocumentImageBean(expName, null);

		CampaignClient.getInstance().addNotPermaBean(autorizableBean);

		DocumentImageBean bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId(), 200);

		assertTrue("the bean name should be same to original name",
				expName.equals(bean.getDocumentName()));
		assertTrue("the bean name should'nt be same to new name",
				!newExpName.equals(bean.getDocumentName()));

		bean.setDocumentName(newExpName);
		sleep(10);

		assertTrue("the bean name should'nt be same to original name",
				!expName.equals(bean.getDocumentName()));
		assertTrue("the bean name should be same to new name",
				newExpName.equals(bean.getDocumentName()));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeansOwner() {
		DocumentImageBean autorizableBean = new DocumentImageBean(
				"test-document-name", null);

		CampaignClient.getInstance().addNotPermaBean(autorizableBean);

		DocumentImageBean bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId(), 200);

		assertTrue(bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		try {
			bean.setOwner(null);
			fail("a null pointer exception must be raised");
		} catch (NullPointerException e) {
		}

		assertTrue(bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		UniqueID idNewOwner = new UniqueID();
		bean.setOwner(idNewOwner);
		sleep(10);

		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(!bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeansModifier() {
		DocumentImageBean autorizableBean = new DocumentImageBean(
				"test-document-name", null);

		CampaignClient.getInstance().addNotPermaBean(autorizableBean);

		DocumentImageBean bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId(), 200);

		assertTrue(bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// Change the owner to no one
		bean.setOwner(new UniqueID());
		sleep(10);

		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(!bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// Add us as modifier
		UniqueID playerId = CampaignClient.getInstance().getCurrentPlayer()
				.getId();
		bean.addModifier(playerId);
		sleep(10);

		assertTrue("Should contain the modifier",
				bean.getModifiers().contains(playerId));
		assertTrue(bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// remove us as modifier
		bean.removeModifier(playerId);
		sleep(10);

		assertTrue("Should'nt contain the modifier", !bean.getModifiers()
				.contains(playerId));
		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(!bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// set list of modifier
		HashSet<UniqueID> guids = new HashSet<UniqueID>();
		for (int i = 0; i < 100; ++i) {
			guids.add(new UniqueID());
		}
		bean.setModifiers(guids);
		sleep(10);

		assertTrue("the owners must have been set",
				bean.getModifiers().equals(guids));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeansUsers() {
		DocumentImageBean autorizableBean = new DocumentImageBean(
				"test-document-name", null);

		CampaignClient.getInstance().addNotPermaBean(autorizableBean);

		DocumentImageBean bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId(), 200);

		assertTrue(bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// Change the owner to no one
		bean.setOwner(new UniqueID());
		sleep(10);

		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(!bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// Add us as user
		UniqueID playerId = CampaignClient.getInstance().getCurrentPlayer()
				.getId();
		bean.addUser(playerId);
		sleep(10);

		assertTrue("Should contain the user", bean.getUsers()
				.contains(playerId));
		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// remove us as user
		bean.removeUser(playerId);
		sleep(10);

		assertTrue("Should'nt contain the user",
				!bean.getUsers().contains(playerId));
		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(!bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// set list of users
		HashSet<UniqueID> guids = new HashSet<UniqueID>();
		for (int i = 0; i < 100; ++i) {
			guids.add(new UniqueID());
		}
		bean.setUsers(guids);
		sleep(10);

		assertTrue("the users must have been set", bean.getUsers()
				.equals(guids));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeanPublic() {
		DocumentImageBean autorizableBean = new DocumentImageBean(
				"test-document-name", null);

		CampaignClient.getInstance().addNotPermaBean(autorizableBean);

		DocumentImageBean bean = CampaignClient.getInstance().getBean(
				autorizableBean.getId(), 200);

		assertTrue(bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// Change the owner to no one
		bean.setOwner(new UniqueID());
		sleep(10);

		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(!bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// set to public bean
		bean.setPublic(true);
		sleep(10);

		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));

		// set to private bean
		bean.setPublic(false);
		sleep(10);

		assertTrue(!bean.isAllowedToApplyChange(CampaignClient.getInstance()
				.getCurrentPlayer()));
		assertTrue(!bean.isAllowedToSee(CampaignClient.getInstance()
				.getCurrentPlayer()));
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
		bean.addModifier(guid);
		sleep(5);
		bean.removeModifier(guid);

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
		bean.setModifiers(users);
		sleep(5);

		assertEquals("The authorization have not changed", 8,
				COUNT_AUTHORIZATION_CHANGED);
		assertEquals("The bean have changed", 9, COUNT_BEAN_CHANGED);

		CampaignClient.getInstance().removeBean(bean);
	}
}
