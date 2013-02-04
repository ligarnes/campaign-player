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
package net.alteiar.server.shared.campaign.character;

import java.io.Serializable;

/**
 * @author Cody Stoutenburg
 * 
 */
public class SizeType implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum SizeCategorie {
		I, MIN, TP, P, M, G, TG, GIG, C
	};

	public enum SizeStyle {
		BIPEDE, QUADRUPEDE
	};

	private static Double getFactorQuadrupede(SizeCategorie categorie) {
		Double factor = 1.0;
		switch (categorie) {
		case I:
			factor = 0.25;
			break;
		case MIN:
			factor = 0.5;
			break;
		case TP:
			factor = 0.75;
			break;
		case P:
			factor = 1.0;
			break;
		case M:
			factor = 1.5;
			break;
		case G:
			factor = 3.0;
			break;
		case TG:
			factor = 6.0;
			break;
		case GIG:
			factor = 12.0;
			break;
		case C:
			factor = 24.0;
			break;
		}
		return factor;
	}

	private static Double getFactorBipede(SizeCategorie categorie) {
		Double factor = 1.0;
		switch (categorie) {
		case I:
			factor = 0.125;
			break;
		case MIN:
			factor = 0.25;
			break;
		case TP:
			factor = 0.5;
			break;
		case P:
			factor = 0.75;
			break;
		case M:
			factor = 1.0;
			break;
		case G:
			factor = 2.0;
			break;
		case TG:
			factor = 4.0;
			break;
		case GIG:
			factor = 8.0;
			break;
		case C:
			factor = 16.0;
			break;
		}
		return factor;
	}

	public static Double getSizeWeightFactor(SizeType size) {
		Double factor = 1.0;
		switch (size.getStyle()) {
		case BIPEDE:
			factor = getFactorBipede(size.getCategorie());
			break;
		case QUADRUPEDE:
			factor = getFactorQuadrupede(size.getCategorie());
			break;
		}
		return factor;
	}

	public static Integer getModTaille(SizeCategorie categorie) {
		Integer modTaille = 0;

		switch (categorie) {
		case I:
			modTaille = +8;
			break;
		case MIN:
			modTaille = +4;
			break;
		case TP:
			modTaille = +2;
			break;
		case P:
			modTaille = +1;
			break;
		case M:
			modTaille = 0;
			break;
		case G:
			modTaille = -1;
			break;
		case TG:
			modTaille = -2;
			break;
		case GIG:
			modTaille = -4;
			break;
		case C:
			modTaille = -8;
			break;
		}

		return modTaille;
	}

	private final SizeCategorie categorie;
	private final SizeStyle style;

	/**
	 * @param categorie
	 * @param style
	 */
	public SizeType(SizeCategorie categorie, SizeStyle style) {
		super();
		this.categorie = categorie;
		this.style = style;
	}

	public SizeCategorie getCategorie() {
		return categorie;
	}

	public SizeStyle getStyle() {
		return style;
	}

	public Integer getModTaille() {
		return getModTaille(categorie);
	}
}
