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

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.seals;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.gods.Gods;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Seals {

	public static String randomSeal(){
		String name = "sigyll of "+ Gods.getYourGodsEnemyName();
		switch(Randomer.randomInteger(9)) {
			case 0:
				name = "the elder sign";
				break;
			case 1:
				name = "the black seal";
				break;
			case 2:
				name = "yellow sign";
				break;
			case 3:
				name = "the r'lyeh seal";
				break;
			case 4:
				name = "zkauba's seal";
				break;
			case 5:
				name = "of the silver key gates";
				break;
			case 6:
				name = "the dreamlands seal";
				break;
			case 7:
				name = "the seven geases";
				break;
		}
		return name;
	}
}
