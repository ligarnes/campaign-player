package net.alteiar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


import net.alteiar.pcgen.PathfinderCharacter;

public class CharacterIO {

	private static CharacterIO INSTANCE = new CharacterIO();

	private final CharacterProperties characterProperties;


	private CharacterIO() {
		characterProperties = new CharacterProperties();
	}

	private PathfinderCharacter readRealFile(File file)
			throws Exception{
			Serializer serializer = new Persister();
			PathfinderCharacter character= serializer.read(PathfinderCharacter.class, file);
			return character;
	}

	private static void systemCall(String[] cmdarray, File dir,
			boolean returnImmediately) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmdarray, new String[] {}, dir);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private File readPCGenFile(File file) throws IOException {
		File tmpXml = new File("./" + file.getName() + "-"
				+ System.currentTimeMillis() + ".tmp");

		tmpXml.setReadable(true);
		tmpXml.setWritable(true);

		String pcgenPath = characterProperties.getPcgenPath();
		String pcgenStyleXml = characterProperties.getPcgenStyle();
		String pcgenSrc = file.getCanonicalPath();
		String pcgenTargetXml = tmpXml.getCanonicalPath();

		systemCall(new String[] { "java", "-jar", pcgenPath + "pcgen.jar",
				"-E", pcgenPath + pcgenStyleXml, "-c", pcgenSrc, "-o",
				pcgenTargetXml, "-s", pcgenPath }, new File(pcgenPath), false);

		tmpXml.deleteOnExit();
		return tmpXml;
	}

	private void realWriteToFile(File file, PathfinderCharacter character)
			throws Exception {
		
		Serializer serializer = new Persister();
		serializer.write(character, file);
		
	}

	public static PathfinderCharacter readFile(File file)
			throws Exception{

		PathfinderCharacter character = null;
		if (file.getName().endsWith(".pcg")) {
			try {
				file = INSTANCE.readPCGenFile(file);
			} catch (IOException e) {
				ExceptionTool.showError(e);
			}
		}
		character = INSTANCE.readRealFile(file);

		if (character.getCurrentHp() == null) {
			character.setCurrentHp(character.getHp());
		}

		if (character.getHtmlCharacter() == null
				|| character.getHtmlCharacter().isEmpty()) {
			String msg = "<html><b>"
					+ " no html found</b> fail to load the character sheet</html>";
			character.setHtmlCharacter(msg);
		}

		return character;
	}

	public static void writeFile(File file, PathfinderCharacter character)
			throws Exception {
		INSTANCE.realWriteToFile(file, character);
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
