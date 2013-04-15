package net.alteiar.test.saveable;

import java.io.File;

import net.alteiar.test.NewCampaignTest;

public class TestSaveAndLoad extends NewCampaignTest {

	@Override
	public String getCampaignName() {
		return "test-save-campaign";
	}

	public int countObjectFile(Class<?> classe) {
		String campaignPath = getCampaignDirectory();
		File objectDir = new File(campaignPath, classe.getCanonicalName());

		String[] files = objectDir.list();
		return files != null ? files.length : 0;
	}
}
