import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class install {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File sourceFile = new File(
				"./target/Pathfinder-system-1.0-SNAPSHOT.jar");

		File targetFile = new File(
				"../CampaignPlayer/ressources/plugin/Pathfinder-system-1.0-SNAPSHOT.jar");

		FileUtils.copyFile(sourceFile, targetFile);
	}

}
