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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.elements;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.AirElementSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class AirElementEuros extends Element {
	
	{
		spriteClass = AirElementSprite.class;

		EXP = 1;
		
		flying = true;
		state = WANDERING;
	}
	
	@Override
	public int attackSkill( Char target ) {
		return defenseSkill;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( HT / 10, HT / 4 );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		if (enemy instanceof Mob) {
			((Mob)enemy).aggro( this );
		}
		if(Random.Int(10) > 7){
			int opposite = enemy.pos + (enemy.pos - this.pos);
			Ballistica trajectory = new Ballistica(enemy.pos, opposite, Ballistica.MAGIC_BOLT);
			WandOfBlastWave.throwChar(enemy, trajectory, 10+Dungeon.depth);
		}
		if(Random.Int(10) < 3){
			Buff.affect(enemy, Paralysis.class, 2f);
		}
		return damage;
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( Poison.class );
		IMMUNITIES.add( Amok.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}