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
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MiGoSwordSprite;
import com.watabou.utils.Random;

public class MiGoSword extends Mob {

	{
		spriteClass = MiGoSwordSprite.class;
		
		HP = HT = 10+ Dungeon.depth;
		defenseSkill = 0;

		EXP = 2;
		
		maxLvl = 5;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		mentalMinus(2, 8, enemy);
		return super.attackProc(enemy, damage)*3;
	}

	@Override
	public int defenseSkill(Char enemy) {
		return 0;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 4, 10 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 20;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 1);
	}
}
