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
package net.alteiar.utils.map.element;

import java.io.Externalizable;

import org.simpleframework.xml.Attribute;

import net.alteiar.utils.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 *         this class represente a size that can be convert from scale
 */
public abstract class MapElementSize implements Externalizable {
	@Attribute
	private static final long serialVersionUID = 7942889766046711297L;
	
	public abstract Double getPixels(Scale scale);
}
