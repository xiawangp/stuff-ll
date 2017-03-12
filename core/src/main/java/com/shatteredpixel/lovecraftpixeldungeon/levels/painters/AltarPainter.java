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
package com.shatteredpixel.lovecraftpixeldungeon.levels.painters;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Yig;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Room;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class AltarPainter extends Painter {

	public static void paint(Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Dungeon.bossLevel( Dungeon.depth + 1 ) ? Terrain.HIGH_GRASS : Terrain.CHASM );

		Point c = room.center();

		fill( level, c.x - 1, c.y - 1, 3, 3, Terrain.WATER );
		int pos = c.x + c.y * level.width();
		if(Randomer.randomBoolean() == true){
			level.drop(new PotionOfLevitation(), pos);
			Item item = Generator.random(Generator.Category.WEP_T5);
			if(item instanceof Weapon){
				((Weapon) item).enchant().upgrade(Random.IntRange(Dungeon.depth/2, Dungeon.depth)).identify();
			}
			level.drop(item, pos );
			set( level, c, Terrain.PEDESTAL );

		} else {
			set( level, c, Terrain.ENCHANTING);
			level.drop(new PotionOfLevitation(), pos+1);
		}

		level.addItemToSpawn( new PotionOfLevitation() );

		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.HIDDEN );
		}

		placeBoss(level, pos-1);
	}

	private static void placeBoss( Level level, int mobpos ) {

		//TODO: MORE MINIBOSSES
		int pos;
		do {
			pos = mobpos;
		} while (level.heaps.get( pos ) != null);

		Mob boss1= new Yig();
		boss1.pos = pos;
		level.mobs.add( boss1 );
	}
}