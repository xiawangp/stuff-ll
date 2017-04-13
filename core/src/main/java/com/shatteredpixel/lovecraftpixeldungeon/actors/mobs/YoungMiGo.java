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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.YoungMiGoSprite;
import com.watabou.utils.Random;

public class YoungMiGo extends MiGo {

	{
		spriteClass = YoungMiGoSprite.class;
		
		HP = HT = 15+ Dungeon.depth;
		defenseSkill = 4+(Dungeon.depth/2);;
		EXP = Random.Int(1,2);
		maxLvl = 5;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 6 );
	}

	@Override
	public int attackSkill(Char target) {
		return 10;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int( 5 ) == 0) {
			Buff.affect( enemy, Bleeding.class ).set( damage );
		}
		mentalMinus(1, 8, enemy);
		return damage;
	}
}
