package net.alteiar.test.saveable;

import static org.junit.Assert.assertEquals;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.test.LoadCampaignTest;

import org.junit.Test;

public class TestLoad extends LoadCampaignTest {

	@Override
	public String getCampaignName() {
		return "test-save-campaign";
	}

	@Test
	public void testLoad() {
		assertEquals(CampaignClient.getInstance().getPlayers().get(0),
				CampaignClient.getInstance().getCurrentPlayer());
	}
}
