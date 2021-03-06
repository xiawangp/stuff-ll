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
package com.shatteredpixel.lovecraftpixeldungeon.levels.traps;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.items.Heap;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;

public class PitfallTrap extends Trap {

	{
		color = RED;
		shape = DIAMOND;
	}

	@Override
	public void activate() {
		Heap heap = Dungeon.level.heaps.get( pos );

		if (heap != null){
			for (Item item : heap.items){
				Dungeon.dropToChasm(item);
			}
			heap.sprite.kill();
			GameScene.discard(heap);
			Dungeon.level.heaps.remove( pos );
		}

		Char ch = Actor.findChar( pos );

		if (ch == Dungeon.hero){
			Chasm.heroFall( pos );
		} else if (ch != null){
			Chasm.mobFall((Mob)ch);
		}
	}

	@Override
	protected void disarm() {
		super.disarm();

		//if making a pit here wouldn't block any paths, make a pit tile instead of a disarmed trap tile.
		if (!(Dungeon.level.solid[pos - Dungeon.level.width()] && Dungeon.level.solid[pos + Dungeon.level.width()])
				&& !(Dungeon.level.solid[pos - 1]&& Dungeon.level.solid[pos + 1])){

			Level.set(pos, Terrain.CHASM);
			GameScene.updateMap( pos );
		}
	}
}
