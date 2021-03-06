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
package com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee;

import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

public class Greataxe extends MeleeWeapon {

	{
		image = ItemSpriteSheet.GREATAXE;

		tier = 5;
		weight = 12;
	}

	@Override
	public int max(int lvl) {
		return  5*(tier+3) +    //40 base, up from 30
				lvl*(tier+1);   //scaling unchanged
	}

	@Override
	public int STRReq(int lvl) {
		lvl = Math.max(0, lvl);
		//20 base strength req, up from 18
		return (10 + tier * 2) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
	}

}
