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
import com.shatteredpixel.lovecraftpixeldungeon.items.armorpieces.MiGoPiece;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MiGoSprite;
import com.watabou.utils.Random;

public class MiGo extends Mob {

	{
		spriteClass = MiGoSprite.class;
		
		HP = HT = 10+ Dungeon.depth;
		defenseSkill = 3+(Dungeon.depth/2);
		EXP = 1;
		loot = MiGoPiece.class;
		lootChance = 0.1f;
		maxLvl = 5;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		mentalMinus(1, 10, enemy);
		return super.attackProc(enemy, damage);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 5 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 9;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 1);
	}
}
