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
package net.alteiar.logger;

import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * @author Cody Stoutenburg
 * 
 */
public class LoggerConfig {
	public static final Logger SERVER_LOGGER = Logger.getLogger("server");
	public static final Logger CLIENT_LOGGER = Logger.getLogger("client");

	static {
		for (Handler handler : SERVER_LOGGER.getHandlers()) {
			SERVER_LOGGER.removeHandler(handler);
		}

		// Do not need to add console handler because it is a default
		// SERVER_LOGGER.addHandler(new ConsoleHandler());
		// CLIENT_LOGGER.addHandler(new ConsoleHandler());
	}

	public static void showStat() {
		Runtime runtime = Runtime.getRuntime();
		Integer byteToMega = 1048576;
		System.out.print("used : "
				+ ((runtime.totalMemory() - runtime.freeMemory()) / byteToMega)
				+ "mb ");
		System.out.print("  committed : "
				+ (runtime.totalMemory() / byteToMega) + "mb ");
		System.out.println("  max : " + (runtime.maxMemory() / byteToMega)
				+ "mb ");
	}
}
