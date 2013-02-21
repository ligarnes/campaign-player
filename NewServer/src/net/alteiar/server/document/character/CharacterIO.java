package net.alteiar.server.document.character;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class CharacterIO {

	private static CharacterIO INSTANCE = new CharacterIO();

	private Unmarshaller unmarshaller;
	private JAXBException ex;

	private CharacterIO() {
		// Create a JAXB context passing in the class of the object we want
		// to marshal/unmarshal
		try {
			JAXBContext context = JAXBContext
					.newInstance(PathfinderCharacter.class);

			// Create the marshaller, this is the nifty little thing that will
			// actually transform the object into XML
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			ex = e;
			unmarshaller = null;
		}
	}

	private PathfinderCharacter readRealFile(File file)
			throws FileNotFoundException, JAXBException {
		if (unmarshaller == null) {
			throw ex;
		}

		return (PathfinderCharacter) unmarshaller
				.unmarshal(new FileReader(file));
	}

	public static PathfinderCharacter readFile(File file)
			throws FileNotFoundException, JAXBException {

		PathfinderCharacter character = null;
		character = INSTANCE.readRealFile(file);

		if (character.getCurrentHp() == null) {
			character.setCurrentHp(character.getHp());
		}

		return character;
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = null;

		IOException ex = null;
		byte[] bytes = null;
		try {
			is = new FileInputStream(file);

			// Get the size of the file
			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				// File is too large
			}

			// Create the byte array to hold the data
			bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				ex = new IOException("Could not completely read file "
						+ file.getName());
			}
		} catch (IOException e) {
			ex = e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					ex = e;
				}
			}
		}

		if (ex != null) {
			throw ex;
		}

		return bytes;
	}
}
