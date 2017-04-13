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
package com.shatteredpixel.lovecraftpixeldungeon.levels.painters;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.MiGoKing;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.MiGoQueen;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.TwoShoggoth;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.WalkingShoggoth;
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

	public static void paint(final Level level, Room room ) {

		Room.Door entrance = room.entrance();

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

		int mobpos = room.center().x+room.center().y * level.width();

		level.map[mobpos] = Terrain.PEDESTAL;
		level.drop( prize().upgrade(Dungeon.hero.lvl/2), heartX + heartY * level.width() ).type = Heap.Type.LOCKED_CHEST;

		for(int i : PathFinder.NEIGHBOURS8) {
			if (level.map[mobpos + i] == Terrain.EMPTY){
				set(level, mobpos + i, Terrain.EMBERS);
			}
		}

		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.LOCKED );
			level.addItemToSpawn(new IronKey(Dungeon.depth));
		}

		placeBoss(level, mobpos);
	}

	private static void placeBoss( Level level, int mobpos ) {

		//TODO: MORE LEVELBOSSES
		int pos;
		do {
			pos = mobpos;
		} while (level.heaps.get( pos ) != null);

		if(Dungeon.depth == 1){
			Mob boss1= new MiGoQueen(){
				@Override
				public void die(Object cause) {
					super.die(cause);
					Dungeon.level.drop(new GoldenKey(Dungeon.depth), this.pos);
				}
			};
			boss1.pos = pos;
			level.mobs.add( boss1 );
		} else if(Dungeon.depth == 2){
			Mob boss2= new MiGoKing(){
				@Override
				public void die(Object cause) {
					super.die(cause);
					Dungeon.level.drop(new GoldenKey(Dungeon.depth), this.pos);
				}
			};
			boss2.pos = pos;
			level.mobs.add( boss2 );
		} else if(Dungeon.depth == 3){
			Mob boss3= new TwoShoggoth(){
				@Override
				public void die(Object cause) {
					super.die(cause);
					Dungeon.level.drop(new GoldenKey(Dungeon.depth), this.pos);
				}
			};
			boss3.pos = pos;
			level.mobs.add( boss3 );
		} else if(Dungeon.depth == 4){
			Mob boss4= new WalkingShoggoth(){
				@Override
				public void die(Object cause) {
					super.die(cause);
					Dungeon.level.drop(new GoldenKey(Dungeon.depth), this.pos);
				}
			};
			boss4.pos = pos;
			level.mobs.add( boss4 );
		}
	}

	public static int spaceNeeded(){
		return Random.IntRange(20, 25);
	}

	private static Item prize() {
		return Generator.random( Random.oneOf(
				Generator.Category.WAND,
				Generator.Category.RING,
				Generator.Category.ARTIFACT
		) );
	}
}
