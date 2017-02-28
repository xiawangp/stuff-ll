/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.lovecraftpixeldungeon.levels.painters;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.MiGoQueen;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.items.Heap;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.lovecraftpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Room;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class LevelBossPainter extends Painter {

	public static void paint(Level level, Room room ) {

		Room.Door entrance = room.entrance();
		entrance.set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.depth));

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);


		int heartX = Random.IntRange(room.left+1, room.right-1);
		int heartY = Random.IntRange(room.top+1, room.bottom-1);

		if (entrance.x == room.left) {
			heartX = room.right - 1;
		} else if (entrance.x == room.right) {
			heartX = room.left + 1;
		} else if (entrance.y == room.top) {
			heartY = room.bottom - 1;
		} else if (entrance.y == room.bottom) {
			heartY = room.top + 1;
		}

		placePlant(level, room.center().x+room.center().y * level.width(), heartX + heartY * level.width(), setBoss(Dungeon.depth));
	}

	//TODO: ADD ALL THE MINIBOSSES TO THE MINIBOSS ROOMS

	private static Mob setBoss(final int depth){
		Mob mob = null;
		switch (depth){
			case 1:
				mob = new MiGoQueen(){
					@Override
					public void die(Object cause) {
						Dungeon.level.drop(new GoldenKey(depth), this.pos);
						super.die(cause);
					}
				};
				break;
		}
		return mob;
	}

	private static void placePlant(Level level, int mobpos, int lootpos, Mob boss){
		boss.pos = mobpos;
		level.mobs.add( boss );
		level.map[mobpos] = Terrain.PEDESTAL;
		level.drop( prize( level ).upgrade(Dungeon.hero.lvl/2), lootpos ).type = Heap.Type.LOCKED_CHEST;


		for(int i : PathFinder.NEIGHBOURS8) {
			if (level.map[mobpos + i] == Terrain.EMPTY){
				set(level, mobpos + i, Terrain.EMBERS);
			}
		}
	}

	public static int spaceNeeded(){
		return Random.IntRange(6, 15);
	}

	private static Item prize(Level level ) {
		return Generator.random( Random.oneOf(
				Generator.Category.WAND,
				Generator.Category.RING,
				Generator.Category.ARTIFACT
		) );
	}
}
