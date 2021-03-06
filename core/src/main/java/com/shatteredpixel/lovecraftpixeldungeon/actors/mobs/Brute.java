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
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.lovecraftpixeldungeon.items.Gold;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.BruteSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Brute extends Mob {
	
	{
		spriteClass = BruteSprite.class;
		
		HP = HT = 40;
		defenseSkill = 15;
		
		EXP = 8;
		maxLvl = 15;
		
		loot = Gold.class;
		lootChance = 0.5f;
	}
	
	private boolean enraged = false;
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		enraged = HP < HT / 4;
	}
	
	@Override
	public int damageRoll() {
		return enraged ?
			Random.NormalIntRange( 15, 45 ) :
			Random.NormalIntRange( 6, 26 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 20;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 8);
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		super.damage( dmg, src );
		
		if (isAlive() && !enraged && HP < HT / 4) {
			enraged = true;
			spend( TICK );
			if (Dungeon.visible[pos]) {
				GLog.w( Messages.get(this, "enraged_text") );
				sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "enraged") );
			}
		}
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( Terror.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
