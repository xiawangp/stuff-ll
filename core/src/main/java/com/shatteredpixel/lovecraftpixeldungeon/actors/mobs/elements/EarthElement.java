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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.elements;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.items.MongolianEgg;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.EarthElementSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class EarthElement extends Element {
	
	{
		spriteClass = EarthElementSprite.class;

		EXP = 1;
		
		flying = false;
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
	protected boolean act() {
		if (Level.water[pos]) {
			die(null);
			sprite.killAndErase();
			return true;
		}
		for(int i : PathFinder.NEIGHBOURS8){
			if(findChar(i) != null && findChar(i) instanceof EarthElement){
				die(null);
				sprite.killAndErase();
				Dungeon.level.drop(new MongolianEgg(), this.pos);
			}
		}
		return super.act();
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		if (enemy instanceof Mob) {
			((Mob)enemy).aggro( this );
		}
		if(Random.Int(10) >= 8){
			for(int i : PathFinder.NEIGHBOURS8){
				if(Level.water[i]){
					int oldTile = Dungeon.level.map[i];
					Dungeon.level.set(i, Terrain.EMPTY_DECO);

					if (Dungeon.visible[i]) {
						GameScene.updateMap( i );
						GameScene.discoverTile( i, oldTile );
					}
				}
			}
		}
		if(Random.Int(10) <= 5){
			Buff.affect(enemy, Roots.class, 3f);
		}
		return damage;
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( Poison.class );
		IMMUNITIES.add( Amok.class );
		IMMUNITIES.add(Levitation.class);
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}