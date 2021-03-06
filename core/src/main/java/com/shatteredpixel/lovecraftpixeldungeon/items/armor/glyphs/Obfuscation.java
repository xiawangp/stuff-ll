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
package com.shatteredpixel.lovecraftpixeldungeon.items.armor.glyphs;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite;

public class Obfuscation extends Armor.Glyph {

	private static ItemSprite.Glowing GREY = new ItemSprite.Glowing( 0x888888 );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see hero.stealth for effect.
		return damage;
	}

	@Override
	public int tierDRAdjust() {
		return -1;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return GREY;
	}

}
