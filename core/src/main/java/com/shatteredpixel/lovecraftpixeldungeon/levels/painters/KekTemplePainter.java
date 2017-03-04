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
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Yig;
import com.shatteredpixel.lovecraftpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Room;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.watabou.utils.Point;

public class KekTemplePainter extends Painter {

	public static void paint(Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Dungeon.bossLevel( Dungeon.depth + 1 ) ? Terrain.HIGH_GRASS : Terrain.CHASM );

		Point c = room.center();

		fill( level, c.x - 1, c.y - 1, 3, 3, Terrain.CHASM );
		int pos = c.x + c.y * level.width();
		set( level, c, Terrain.PEDESTAL );
		Yig yig = new Yig();
		yig.pos = pos-1;
		yig.SLEEPING.status();
		level.mobs.add( yig );

		level.addItemToSpawn( new PotionOfLevitation() );

		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.LOCKED );
			level.addItemToSpawn(new IronKey(Dungeon.depth));
		}
	}
}