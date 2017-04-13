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
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.lovecraftpixeldungeon.items.MongolianEgg;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MongolianDeathWormSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class MongolianDeathWorm extends Mob {

	{
		spriteClass = MongolianDeathWormSprite.class;

		HP = HT = 60;
		defenseSkill = 20;

		EXP = 6;
		maxLvl = 14;

		loot = MongolianEgg.class;
		lootChance = 0.1f;

		properties.add(Property.DEMONIC);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(4, 12);
	}

	@Override
	protected boolean act() {
		Dungeon.level.updateFieldOfView( this, Level.fieldOfView );

		if (Level.water[pos]) {
			die(null);
			sprite.killAndErase();
			return true;
		}
		for(int i : PathFinder.NEIGHBOURS8){
			int position = pos + i;
			Char mob;
			if(findChar(position) != null && findChar(position) instanceof MongolianDeathWorm){
				mob = findChar(position);
				int deadpos = mob.pos;
				mob.sprite.killAndErase();
				mob.die(null);
				Dungeon.level.drop(new MongolianEgg(), deadpos);
			}
		}

		return super.act();
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (enemy instanceof Mob) {
			((Mob)enemy).aggro( this );
		}
		if(Random.Int(10) >= 8){
			for(int i : PathFinder.NEIGHBOURS8){
				int position = pos+i;
				if(Level.water[position]){
					int oldTile = Dungeon.level.map[position];
					Dungeon.level.set(position, Terrain.EMPTY_DECO);

					if (Dungeon.visible[position]) {
						GameScene.updateMap( position );
						GameScene.discoverTile( position, oldTile );
					}
				}
			}
		}
		if(Random.Int(6) <= 5){
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

	@Override
	public int attackSkill( Char target ) {
		return 20;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 8);
	}
}
