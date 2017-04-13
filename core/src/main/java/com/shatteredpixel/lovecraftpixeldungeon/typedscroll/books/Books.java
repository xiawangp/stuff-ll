/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * Lovecraft Pixel Dungeon
 * Copyright (C) 2016-2017 Leon Horn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This Program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without eben the implied warranty of
 * GNU General Public License for more details.
 *
 * You should have have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.books;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Books {

	public static String randomBook(){
		String name = "Mr. Green";
		switch(Randomer.randomInteger(20)){
			case 0:
				name = "Voormish Tablets";
				break;
			case 1:
				name = "Necronomicon";
				break;
			case 2:
				name = "Book of Eibon";
				break;
			case 3:
				name = "Pnakotic Manuscripts";
				break;
			case 4:
				name = "Revelations of Gla'aki";
				break;
			case 5:
				name = "Book of Azathoth";
				break;
			case 6:
				name = "Book of Iod";
				break;
			case 7:
				name = "Celaeno Fragments";
				break;
			case 8:
				name = "Cthäat Aquadingen";
				break;
			case 9:
				name = "Cultes des Goules";
				break;
			case 10:
				name = "De Vermis Mysteriis";
				break;
			case 11:
				name = "Dhol Chants";
				break;
			case 12:
				name = "Eltdown Shards";
				break;
			case 13:
				name = "G'harne Fragments";
				break;
			case 14:
				name = "Liber Ivonis";
				break;
			case 15:
				name = "Sending Out of the Soul";
				break;
			case 16:
				name = "Parchments of Pnom";
				break;
			case 17:
				name = "Poakotic Fragments";
				break;
			case 18:
				name = "Las Reglas de Ruina";
				break;
			case 19:
				name = "Tarsioid Psalms";
				break;
			case 20:
				name = "Testament of Carnamagos";
				break;
			case 21:
				name = "Unaussprechliche Kulte";
				break;
			case 22:
				name = "Zanthu Tablets";
				break;
			case 23:
				name = "Zhou Texts";
				break;
			case 24:
				name = "Ars Magna et Ultima";
				break;
			case 25:
				name = "Black Rites";
				break;
			case 26:
				name = "Book of Dzyan";
				break;
			case 27:
				name = "Book of Hidden Things";
				break;
			case 28:
				name = "Book of Thoth";
				break;
			case 29:
				name = "Clavis Alchimiae";
				break;
			case 30:
				name = "Commentaries on Witchcraft";
				break;
			case 31:
				name = "Cryptomenysis Patefacta";
				break;
			case 32:
				name = "Daemonolatreia";
				break;
			case 33:
				name = "De Furtivis Literarum Notis";
				break;
			case 34:
				name = "De Masticatione Mortuorum in Tumulis";
				break;
			case 35:
				name = "Image du Monde";
				break;
			case 36:
				name = "Liber-Damnatus";
				break;
			case 37:
				name = "Liber Investigationis";
				break;
			case 38:
				name = "Magyar Folklore";
				break;
			case 39:
				name = "Seventh Book of Moses";
				break;
		}
		return name;
	}
}
