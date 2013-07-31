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
package net.alteiar.utils.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.simpleframework.xml.Element;

/**
 * @author Cody Stoutenburg
 * 
 */
public class SerializableFile implements Serializable {
	private static final long serialVersionUID = 4786344613415239528L;

	private static String convert(byte[] array) {
		StringBuilder builder = new StringBuilder();

		for (byte b : array) {
			int i = b;
			builder.append(String.valueOf(i) + " ");
		}
		builder.deleteCharAt(builder.length() - 1);

		return new String(builder);
	}

	private static byte[] convert(String array) {
		String[] vals = array.split(" ");
		byte[] bytes = new byte[vals.length];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = Integer.valueOf(vals[i]).byteValue();
		}
		return bytes;
	}

	@Element
	private String file;

	public SerializableFile() {
	}

	public SerializableFile(byte[] bytes) {
		this(convert(bytes));
	}

	public SerializableFile(String bytes) {
		this.file = bytes;
	}

	public SerializableFile(File file) throws IOException {
		this.file = convert(SerializableFile.getBytesFromFile(file));
	}

	public byte[] getBytes() {
		return convert(this.file);
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
