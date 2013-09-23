import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class installShadowrunPlugin {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File sourceFile = new File("./target/Shadowrun-system-1.0-SNAPSHOT.jar");

		File newTargetFile = new File(
				"../../CampaignPlayer/ressources/plugin/shadowrun/Shadowrun-system-1.0-SNAPSHOT.jar");

		FileUtils.copyFile(sourceFile, newTargetFile);
	}

}
