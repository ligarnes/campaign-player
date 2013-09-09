/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.server.document.DocumentIO;
import net.alteiar.shared.UniqueID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Element;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @author Cody Stoutenburg
 * 
 */
public class SerializableFile implements KryoSerializable {

	@Element
	private UniqueID id;

	public SerializableFile() {
	}

	public SerializableFile(File file) throws IOException {
		id = new UniqueID();
		FileUtils.copyFile(file, getLocalFile());
	}

	protected File getLocalFile() {
		String campaignDir = CampaignClient.getInstance()
				.getCampaignDirectory();
		System.out.println("file at: " + campaignDir + "files/"
				+ DocumentIO.validateFilename(id.toString()));
		return new File(campaignDir, "files/"
				+ DocumentIO.validateFilename(id.toString()));
	}

	@Override
	public void read(Kryo kryo, Input arg1) {
		id = kryo.readObject(arg1, UniqueID.class);

		byte[] data = kryo.readObject(arg1, byte[].class);
		try {
			writeByteToFile(data, getLocalFile());
		} catch (IOException e) {
			Logger.getLogger(getClass()).warn(
					"Impossible d'enregister le fichier", e);
			e.printStackTrace();
		}
	}

	@Override
	public void write(Kryo kryo, Output arg1) {
		byte[] data;
		try {
			kryo.writeObject(arg1, id);

			data = getBytesFromFile(getLocalFile());
			kryo.writeObject(arg1, data);
		} catch (IOException e) {
			Logger.getLogger(getClass()).warn(
					"Impossible d'envoyer le fichier", e);
			e.printStackTrace();
		}
	}

	private static void writeByteToFile(byte[] file, File dest)
			throws IOException {
		OutputStream os = null;

		IOException ex = null;

		try {
			os = new FileOutputStream(dest);
			os.write(file);
			os.flush();
		} catch (IOException e) {
			ex = e;
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				ex = e;
			}
		}

		if (ex != null) {
			throw ex;
		}
	}

	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = null;

		IOException ex = null;
		byte[] bytes = null;
		try {
			is = new FileInputStream(file);

			// Get the size of the file
			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				// File is too large
				Logger.getLogger(SerializableFile.class).error(
						"Impossible de lire le fichier, fichier trop gros");
				return null;
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
