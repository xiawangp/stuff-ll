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

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Build;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.GolemSprite;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.Random;

import java.util.HashSet;

public class GolemElement extends Element {
	
	{
		spriteClass = GolemSprite.class;

		EXP = 1;
		
		flying = false;
		state = WANDERING;
	}

	@TargetApi(Build.VERSION_CODES.N)
	private static int getAttackFromTime(){
		int damage;
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int min = Calendar.getInstance().get(Calendar.MINUTE);
		int sec = Calendar.getInstance().get(Calendar.SECOND);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		damage = hour+min+sec/(year/100);
		return damage;
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
		try{
			return Random.Int(getAttackFromTime());
		} catch (Exception e){
			return 100;
		}
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if(Randomer.randomInteger(20) == 17){
			this.damage(100, enemy);
		}
		return 0;
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