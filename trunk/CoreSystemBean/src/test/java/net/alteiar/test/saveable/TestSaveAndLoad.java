package net.alteiar.test.saveable;

import java.io.File;

import net.alteiar.test.AllTests;
import net.alteiar.test.BasicTest;

public class TestSaveAndLoad extends BasicTest {
	public static String getPlayerName() {
		return "player-name";
	}

	public static String getCampaignName() {
		return "test-save-campaign";
	}

	public static String getCampaignDirectory() {
		return "./test/ressources/campaign/" + getCampaignName();
	}

	public int countObjectFile(Class<?> classe) {
		String campaignPath = getCampaignDirectory();
		File objectDir = new File(campaignPath, classe.getCanonicalName());

		String[] files = objectDir.list();
		return files != null ? files.length : 0;
	}

	public static void clearSaveCampaign() {
		// First remove old save directory
		File campaignDirectory = new File(getCampaignDirectory());
		AllTests.deleteRecursive(campaignDirectory);
	}

}
