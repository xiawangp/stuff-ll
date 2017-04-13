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
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Kek;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.items.StatueOfPepe;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Room;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class KekTemplePainter extends Painter {

	public static void paint(Level level, Room room ) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.WATER);

		int mobpos = room.center().x+room.center().y * level.width();

		level.map[mobpos] = Terrain.PEDESTAL;


		for(int i : PathFinder.NEIGHBOURS8) {
			if (level.map[mobpos + i] == Terrain.WATER){
				set(level, mobpos + i, Terrain.CHASM);
			}
		}

		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.BARRICADE );
			level.addItemToSpawn(new PotionOfLiquidFlame());
		}

		placeBoss(level, mobpos);
	}

	private static void placeBoss( Level level, int mobpos ) {

		int pos;
		do {
			pos = mobpos;
		} while (level.heaps.get( pos ) != null);

		Mob boss1= new Kek(){
			@Override
			public void die(Object cause) {
				super.die(cause);
				Dungeon.level.drop(new StatueOfPepe(), this.pos);
			}
		};
		boss1.pos = pos;
		level.mobs.add( boss1 );
	}

	public static int spaceNeeded(){
		return Random.IntRange(20, 25);
	}

}