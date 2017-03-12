/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * Lovercaft Pixel Dungeon
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

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.people;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class People {

	public static String randomPerson(){
		String name = "Abdul Alhazred";
		switch(Randomer.randomInteger(18)) {
			case 0:
				name = "Abdul Alhazred";
				break;
			case 1:
				name = "Bran Mak Morn";
				break;
			case 2:
				name = "Remigius";
				break;
			case 3:
				name = "Gauthier de Metz";
				break;
			case 4:
				name = "Heiriarchus";
				break;
			case 5:
				name = "Otto Dostman";
				break;
			case 6:
				name = "Dr. Margaret Alice Murray";
				break;
			case 7:
				name = "Friedrich von Junzt";
				break;
			case 9:
				name = "Barzai the Wise";
				break;
			case 10:
				name = "Geber";
				break;
			case 11:
				name = "Enoch Bowen";
				break;
			case 12:
				name = "Atal";
				break;
			case 13:
				name = "Titus Crow";
				break;
			case 14:
				name = "Gespard Du Nord";
				break;
			case 15:
				name = "E-poh";
				break;
			case 16:
				name = "Eldin the Wanderer";
				break;
			case 17:
				name = "Edward Elric";
				break;
		}
		return name;
	}
}
