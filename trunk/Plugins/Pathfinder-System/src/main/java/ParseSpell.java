
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ParseSpell {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File spells = new File("sorts-pathfinder.csv");

		BufferedReader reader = new BufferedReader(new FileReader(spells));

		String titles = reader.readLine();

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] spellValue = line.split(";");
			String name = spellValue[1];
			String school = spellValue[2];

			String source = spellValue[15];
			String url = spellValue[17];

			HashMap<String, Integer> classesLevel = new HashMap<String, Integer>();
			addToMap(classesLevel, "magicien", valueOf(spellValue[3]));
			addToMap(classesLevel, "prêtre", valueOf(spellValue[4]));
			addToMap(classesLevel, "druide", valueOf(spellValue[5]));
			addToMap(classesLevel, "rodeur", valueOf(spellValue[6]));
			addToMap(classesLevel, "barde", valueOf(spellValue[7]));
			addToMap(classesLevel, "paladin", valueOf(spellValue[8]));
			addToMap(classesLevel, "alchimiste", valueOf(spellValue[9]));
			addToMap(classesLevel, "conjurateur", valueOf(spellValue[10]));
			addToMap(classesLevel, "sorcier", valueOf(spellValue[11]));
			addToMap(classesLevel, "inquisiteur", valueOf(spellValue[12]));
			addToMap(classesLevel, "oracle", valueOf(spellValue[13]));
			addToMap(classesLevel, "anti paladin", valueOf(spellValue[14]));

			if (source.isEmpty()) {
				source = "inconnu";
			}
			if (url.isEmpty()) {
				url = "inconnu";
			}

			/*
			 * Spell spell = new Spell(name, school, source, url, classesLevel);
			 * // System.out.println(spell.toString());
			 * 
			 * try { String filename = DocumentIO.validateFilename(spell.getId()
			 * .toString()); DocumentIO.saveDocument(spell, "./tmp/", filename +
			 * ".xml"); } catch (Exception e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 */
		}

		reader.close();
	}

	private static void addToMap(HashMap<String, Integer> map, String key,
			Integer value) {
		if (value >= 0) {
			map.put(key, value);
		}
	}

	private static int valueOf(String str) {
		if (str.isEmpty()) {
			return -1;
		}

		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException ex) {

		}

		return -1;
	}
}
