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

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Dagger extends MeleeWeapon {
	
	{
		image = ItemSpriteSheet.DAGGER;

		tier = 1;
		weight = 1;
	}

	@Override
	public int max(int lvl) {
		return  4*(tier+1) +    //8 base, down from 10
				lvl*(tier+1);   //scaling unchanged
	}

	@Override
	public int damageRoll(Hero hero) {
		Char enemy = hero.enemy();
		if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
			//deals avg damage to max on surprise, instead of min to max.
			int damage = imbue.damageFactor(Random.NormalIntRange((min() + max()) / 2, max()));
			int exStr = hero.STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
			return damage;
		} else
			return super.damageRoll(hero);
	}
}
