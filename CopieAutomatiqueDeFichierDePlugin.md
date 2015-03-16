# Copie automatique du fichier de plugin #

Pour une copie plus simple et automatique je suit les étapes suivantes:

  * maven install du projet Pathfinder-System (ou autre projet de plugin)
  * exécution de la classe install (dans le projet pathfinder-System ou autre plugin)


```
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

		// FileUtils.copyFile(sourceFile, targetFile);

		File newTargetFile = new File(
				"../CampaignPlayer/ressources/plugin/pathfinder/Pathfinder-system-1.0-SNAPSHOT.jar");

		FileUtils.copyFile(sourceFile, newTargetFile);
	}

}
```

# Details #

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages