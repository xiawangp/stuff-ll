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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.Wand;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.LightningTrap;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MiGoShieldSprite;
import com.watabou.utils.Random;

public class MiGoShield extends Mob {

	{
		spriteClass = MiGoShieldSprite.class;
		
		HP = HT = 6+ Dungeon.depth;
		defenseSkill = 4+(Dungeon.depth/2)*2;

		EXP = 2;
		
		maxLvl = 5;
	}

	@Override
	public void damage( int dmg, Object src ){
		//crab blocks all attacks originating from the hero or enemy characters or traps if it is alerted.
		//All direct damage from these sources is negated, no exceptions. blob/debuff effects go through as normal.
		if ((enemySeen && state != SLEEPING && paralysed == 0) && (src instanceof Wand || src instanceof LightningTrap.Electricity || src instanceof Char)){
			super.damage( 0, src );
		} else {
			super.damage( dmg, src );
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return false;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 4 );
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 1);
	}
}
