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
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.CrabSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class CrabElemental extends Element {

	{
		spriteClass = CrabSprite.class;

		EXP = 1;

		HP = HT = 15+Dungeon.depth;
		defenseSkill = 5+(Dungeon.depth/2);
		baseSpeed = 2f;

		flying = false;
		state = WANDERING;

		loot = new MysteryMeat();
		lootChance = 0.167f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 8 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 12;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 4);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if(enemy == Dungeon.hero){
			Dungeon.hero.decreaseMentalHealth(damage);
		}
		this.aggro(Char.findChar(Dungeon.level.randomDestination()));
		return super.defenseProc(enemy, damage);
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		if (enemy instanceof Mob) {
			((Mob)enemy).aggro( this );
		}
		if(Random.Int(10) < 3){
			if(enemy instanceof Mob){
				enemy.die(null);
			}
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
	
	@Override
	public void add( Buff buff ) {
		if (buff instanceof Corruption) {
			if (HP < HT) {
				HP++;
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			}
		} else {
			super.add( buff );
		}
	}
}
