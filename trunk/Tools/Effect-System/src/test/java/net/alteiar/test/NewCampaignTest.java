package net.alteiar.test;

import java.awt.Color;
import java.io.File;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;

import org.junit.After;
import org.junit.Before;

public class NewCampaignTest extends BasicTest {

	/**
	 * timeout is used in test when we try to get a bean
	 */
	private Long timeout;

	@Before
	public void beforeTest() {
		System.out.println("Setting up test");
		String address = "127.0.0.1";
		String port = "1099";
		timeout = 300L;

		String localDirectoryPath = getCampaignDirectory();

		deleteRecursive(new File(localDirectoryPath));

		CampaignClient
				.startNewCampaignServer(address, port, localDirectoryPath);
		CampaignClient.getInstance().createPlayer(getPlayerName(), true,
				Color.BLUE);
	}

	@After
	public void afterTest() {
		try {
			// finalyze all actions
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// CampaignClient.getInstance().saveGame();
		CampaignClient.leaveGame();
		System.out.println("tearing down");
	}

	protected Long getTimeout() {
		return timeout;
	}

	protected <E extends BasicBean> E addBean(E bean) {
		CampaignClient.getInstance().addBean(bean);
		return getBeans(bean);
	}

	protected <E extends BasicBean> E getBeans(E bean) {
		return CampaignClient.getInstance().getBean(bean.getId(), getTimeout());
	}
}
