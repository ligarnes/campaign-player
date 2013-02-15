package net.alteiar.client.shared.campaign.battle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.alteiar.client.shared.campaign.map.Map2DSave;

public class BattleIO {

	private static BattleIO INSTANCE = new BattleIO();

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private JAXBException ex;

	private BattleIO() {
		// Create a JAXB context passing in the class of the object we want
		// to marshal/unmarshal
		try {
			JAXBContext context = JAXBContext.newInstance(BattleSave.class,
					Map2DSave.class);
			// Create the marshaller, this is the nifty little thing that will
			// actually transform the object into XML
			marshaller = context.createMarshaller();
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			ex = e;
			marshaller = null;
			unmarshaller = null;
		}
	}

	private BattleSave readRealFile(File file) throws FileNotFoundException,
			JAXBException {
		if (unmarshaller == null) {
			throw ex;
		}

		return (BattleSave) unmarshaller.unmarshal(new FileReader(file));
	}

	private void realWriteToFile(File file, BattleSave character)
			throws JAXBException, IOException {
		if (marshaller == null) {
			throw ex;
		}

		FileWriter writer = new FileWriter(file);
		marshaller.marshal(character, writer);
	}

	public static BattleSave readFile(File file) throws FileNotFoundException,
			JAXBException {

		BattleSave battle = null;
		battle = INSTANCE.readRealFile(file);
		return battle;
	}

	public static void writeFile(File file, BattleSave battle)
			throws JAXBException, IOException {
		INSTANCE.realWriteToFile(file, battle);
	}
}
