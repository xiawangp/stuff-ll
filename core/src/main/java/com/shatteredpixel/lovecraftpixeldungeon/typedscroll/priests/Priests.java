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

package com.shatteredpixel.lovecraftpixeldungeon.typedscroll.priests;

import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;

public class Priests {

	public static String randomPriest(){
		String name = "Monk Lobon The First";
		switch(Randomer.randomInteger(10)){
			case 0:
				name = "Monk Lobon II";
				break;
			case 1:
				name = "Monk Lobon XVI";
				break;
			case 2:
				name = "Priest Tamash The Elder";
				break;
			case 3:
				name = "Priest Tamash The Younger";
				break;
			case 4:
				name = "Highpriest Oukranos";
				break;
			case 5:
				name = "Cultist Ryonis";
				break;
			case 6:
				name = "Juntz";
				break;
			case 7:
				name = "Godpriest Vorvadoss";
				break;
			case 8:
				name = "God Ulthar The Wise";
				break;
			case 9:
				name = "Forgotten One Savty'ya";
				break;
		}
		return name;
	}
}
