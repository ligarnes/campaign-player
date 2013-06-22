package net.alteiar.test.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.documents.AuthorizationAdapter;
import net.alteiar.documents.BeanDocument;
import net.alteiar.image.ImageBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.test.NewCampaignNoGMTest;
import net.alteiar.test.beans.map.TestMap;

import org.junit.Test;

public class TestAuthorizableBasicBeans extends NewCampaignNoGMTest {

	private static int COUNT_AUTHORIZATION_CHANGED = 0;
	private static int COUNT_BEAN_CHANGED = 0;

	@Test(timeout = 5000)
	public void testAuthorizableBeansName() {
		String expName = "test-document-name";
		String newExpName = "test-document-name-new";

		ImageBean img = new ImageBean();
		BeanDocument autorizableBean = new BeanDocument(expName,
				"document-type", img);

		CampaignClient.getInstance().addBean(autorizableBean);

		BeanDocument bean = CampaignClient.getInstance().getBean(
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
		BeanDocument autorizableBean = new BeanDocument("test-document-name",
				"document-type", new ImageBean());

		Player currentPlayer = CampaignClient.getInstance().getCurrentPlayer();

		autorizableBean = addBean(autorizableBean);

		assertTrue(autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		try {
			autorizableBean.setOwner(null);
			fail("a null pointer exception must be raised");
		} catch (NullPointerException e) {
		}

		assertTrue(autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		autorizableBean.setOwner(new UniqueID());
		sleep(10);

		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(!autorizableBean.isAllowedToSee(currentPlayer));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeansModifier() {
		BeanDocument autorizableBean = new BeanDocument("test-document-name",
				"document-type", new ImageBean());

		Player currentPlayer = CampaignClient.getInstance().getCurrentPlayer();
		autorizableBean = addBean(autorizableBean);

		assertTrue(autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		// Change the owner to no one
		autorizableBean.setOwner(new UniqueID());
		sleep(10);

		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(!autorizableBean.isAllowedToSee(currentPlayer));

		// Add us as modifier
		UniqueID playerId = currentPlayer.getId();
		autorizableBean.addModifier(playerId);
		sleep(10);

		assertTrue("Should contain the modifier", autorizableBean
				.getModifiers().contains(playerId));
		assertTrue(autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		// remove us as modifier
		autorizableBean.removeModifier(playerId);
		sleep(10);

		assertTrue("Should'nt contain the modifier", !autorizableBean
				.getModifiers().contains(playerId));
		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(!autorizableBean.isAllowedToSee(currentPlayer));

		// set list of modifier
		HashSet<UniqueID> guids = new HashSet<UniqueID>();
		for (int i = 0; i < 100; ++i) {
			guids.add(new UniqueID());
		}
		autorizableBean.setModifiers(guids);
		sleep(10);

		assertTrue("the owners must have been set", autorizableBean
				.getModifiers().equals(guids));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeansGameMaster() {
		BeanDocument autorizableBean = new BeanDocument(
				"test-game-master-change-see-right", "document-type",
				new ImageBean());
		autorizableBean = addBean(autorizableBean);

		Player fakeGameMaster = new Player("GameMaster", true, Color.black);
		CampaignClient.getInstance().addBean(fakeGameMaster);
		fakeGameMaster = CampaignClient.getInstance().getBean(
				fakeGameMaster.getId(), 10);
		assertTrue(fakeGameMaster != null);
		// Current Player is GM and is supposed to be able to change and
		// to see every bean
		assertTrue(autorizableBean.isAllowedToApplyChange(fakeGameMaster));
		assertTrue(autorizableBean.isAllowedToSee(fakeGameMaster));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeansUsers() {
		BeanDocument autorizableBean = new BeanDocument("test-document-name",
				"document-type", new ImageBean());

		Player currentPlayer = CampaignClient.getInstance().getCurrentPlayer();
		autorizableBean = addBean(autorizableBean);

		assertTrue(autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		// Change the owner to no one
		autorizableBean.setOwner(new UniqueID());
		sleep(10);

		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(!autorizableBean.isAllowedToSee(currentPlayer));

		// Add us as user
		UniqueID playerId = currentPlayer.getId();
		autorizableBean.addUser(playerId);
		sleep(10);

		assertTrue("Should contain the user", autorizableBean.getUsers()
				.contains(playerId));
		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		// remove us as user
		autorizableBean.removeUser(playerId);
		sleep(10);

		assertTrue("Should'nt contain the user", !autorizableBean.getUsers()
				.contains(playerId));
		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(!autorizableBean.isAllowedToSee(currentPlayer));

		// set list of users
		HashSet<UniqueID> guids = new HashSet<UniqueID>();
		for (int i = 0; i < 100; ++i) {
			guids.add(new UniqueID());
		}
		autorizableBean.setUsers(guids);
		sleep(10);

		assertTrue("the users must have been set", autorizableBean.getUsers()
				.equals(guids));

		autorizableBean.setOwner(currentPlayer.getId());
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeanPublic() {
		BeanDocument autorizableBean = new BeanDocument("test-document-name",
				"document-type", new ImageBean());

		Player currentPlayer = CampaignClient.getInstance().getCurrentPlayer();
		autorizableBean = addBean(autorizableBean);

		assertTrue(autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		// Change the owner to no one
		autorizableBean.setOwner(new UniqueID());
		sleep(10);

		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(!autorizableBean.isAllowedToSee(currentPlayer));

		// set to public bean
		autorizableBean.setPublic(true);
		sleep(10);

		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(autorizableBean.isAllowedToSee(currentPlayer));

		// set to private bean
		autorizableBean.setPublic(false);
		sleep(10);

		assertTrue(!autorizableBean.isAllowedToApplyChange(currentPlayer));
		assertTrue(!autorizableBean.isAllowedToSee(currentPlayer));
	}

	@Test(timeout = 5000)
	public void testAuthorizableBeanListener() {
		COUNT_AUTHORIZATION_CHANGED = 0;
		COUNT_BEAN_CHANGED = 0;

		BeanDocument autorizableBean = new BeanDocument("test-document-name",
				"document-type", new ImageBean(TestMap.createTransfertImage()));

		autorizableBean = addBean(autorizableBean);

		autorizableBean.addPropertyChangeListener(new AuthorizationAdapter() {
			@Override
			public void authorizationChanged(PropertyChangeEvent evt) {
				super.authorizationChanged(evt);
				COUNT_AUTHORIZATION_CHANGED++;
			}
		});

		autorizableBean.getBean().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						COUNT_BEAN_CHANGED++;
					}
				});
		((ImageBean) autorizableBean.getBean()).setImage(TestMap
				.createTransfertImage());

		sleep(100);
		assertEquals("The bean have changed", 1, COUNT_BEAN_CHANGED);
		assertEquals("The authorization have not changed", 0,
				COUNT_AUTHORIZATION_CHANGED);

		UniqueID guid = new UniqueID();
		autorizableBean.addModifier(guid);
		sleep(5);
		autorizableBean.removeModifier(guid);

		autorizableBean.addUser(guid);
		sleep(5);
		autorizableBean.removeUser(guid);
		sleep(5);
		autorizableBean.setPublic(true);
		sleep(5);
		autorizableBean.setPublic(false);
		sleep(5);
		HashSet<UniqueID> users = new HashSet<UniqueID>();
		users.add(new UniqueID());
		autorizableBean.setUsers(users);
		sleep(5);
		autorizableBean.setModifiers(users);
		sleep(5);

		assertEquals("The authorization have not changed", 8,
				COUNT_AUTHORIZATION_CHANGED);
		assertEquals("The bean have changed", 1, COUNT_BEAN_CHANGED);

		CampaignClient.getInstance().removeBean(autorizableBean);
	}
}
