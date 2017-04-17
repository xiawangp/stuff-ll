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

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ToxicElementalSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class ToxicElemental extends Element {

	{
		spriteClass = ToxicElementalSprite.class;

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
	public int defenseProc(Char enemy, int damage) {
		GameScene.add(Blob.seed(enemy.pos, 10, ToxicGas.class));
		return super.defenseProc(enemy, damage);
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		if (enemy instanceof Mob) {
			((Mob)enemy).aggro( this );
		}
		if(Random.Int(10) < 3){
			Buff.affect(enemy, Poison.class);
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
		if (buff instanceof Poison) {
			if (HP < HT) {
				HP++;
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			}
		} else {
			super.add( buff );
		}
	}
}
