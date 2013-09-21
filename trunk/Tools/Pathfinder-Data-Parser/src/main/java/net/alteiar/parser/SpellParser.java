package net.alteiar.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import net.alteiar.newversion.server.document.DocumentIO;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import pathfinder.bean.spell.Reference;
import pathfinder.bean.spell.Spell;
import pathfinder.bean.spell.UnitValue;

public class SpellParser {

	/**
	 * @param args
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void main(String[] args) throws DocumentException {
		File coreRuleBook = new File("pfrpg.xml");
		File ultimateMagic = new File("um.xml");
		File advancePlayerGuide = new File("um.xml");

		parseSpells("Core rule book", coreRuleBook);
		parseSpells("Ultimate magic", ultimateMagic);
		parseSpells("Advanced player guide", advancePlayerGuide);
	}

	public static void parseSpells(String name, File book)
			throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(book);

		Element root = document.getRootElement();

		parseSpell("Ultimate magic", root.element("spells"));
	}

	public static void parseSpell(String source, Element allSpell) {
		for (Iterator<Element> i = allSpell.elementIterator(); i.hasNext();) {
			Element element = i.next();

			String name = element.attributeValue("title");
			String school = element.attributeValue("school");

			Element elementComp = element.element("components");
			String composant = null;
			if (elementComp != null) {
				composant = elementComp.attributeValue("kinds");
			}
			if (composant == null) {
				composant = "";
			}

			UnitValue range = parseUnitValues(element.element("range"));

			UnitValue castingTime = parseUnitValues(element
					.element("castingTime"));

			String description = getTextValue(element.element("summary"));
			if (description == null) {
				description = "";
			}

			HashMap<String, Integer> parseLevels = parseLevels(element
					.element("levels"));

			Reference ref = parseReference(element.element("source"));

			System.out.println(name + " - " + school + " - " + composant
					+ " - " + range + " - " + castingTime + " - " + description
					+ " - " + parseLevels);

			try {
				Spell spell = new Spell(name, school, range, castingTime,
						description, composant, source, ref, parseLevels);

				DocumentIO.saveDocument(spell, "./tmp/");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Reference parseReference(Element source) {
		Element ref = source.element("references").element("reference");
		return new Reference(ref.attributeValue("title"),
				ref.attributeValue("url"));
	}

	public static HashMap<String, Integer> parseLevels(Element element) {
		HashMap<String, Integer> map = new HashMap<>();

		for (Iterator<Element> itt = element.elementIterator(); itt.hasNext();) {
			Element classLevel = itt.next();
			String className = classLevel.attributeValue("list");
			Integer level = Integer.valueOf(classLevel.attributeValue("level"));

			if (className.equalsIgnoreCase("druid")) {
				className = "druide";
			} else if (className.equalsIgnoreCase("sorcerer-wizard")) {
				className = "magicien";
				map.put("ensorceleur", level);
			} else if (className.equalsIgnoreCase("witch")) {
				className = "sorcière";
			} else if (className.equalsIgnoreCase("summoner")) {
				className = "conjurateur";
			} else if (className.equalsIgnoreCase("cleric")) {
				className = "prêtre";
			} else if (className.equalsIgnoreCase("inquisitor")) {
				className = "inquisiteur";
			} else if (className.equalsIgnoreCase("alchemist")) {
				className = "alchimiste";
			} else if (className.equalsIgnoreCase("bard")) {
				className = "barde";
			} else if (className.equalsIgnoreCase("antipaladin")) {
				className = "antipaladin";
			} else if (className.equalsIgnoreCase("ranger")) {
				className = "rôdeur";
			} else if (className.equalsIgnoreCase("paladin")) {
			} else if (className.equalsIgnoreCase("oracle")) {
			} else if (className.equalsIgnoreCase("magus")) {
			}
			map.put(className, level);
		}

		return map;
	}

	public static String getTextValue(Element e) {
		String res = null;
		if (e != null) {
			res = e.getText();
		}
		return res;
	}

	public static UnitValue parseUnitValues(Element element) {
		if (element == null) {
			return new UnitValue("", "");
		}

		String unitType = null;
		if (element.attributeCount() > 0) {
			unitType = element.attributeValue("unit");
		}
		String value = element.getTextTrim();
		if (value.isEmpty()) {
			value = element.attributeValue("value");
		}

		return new UnitValue(unitType, value);
	}
}
