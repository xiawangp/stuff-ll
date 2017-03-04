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

public class KekTemplePainter extends Painter {

	public static void paint(Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Dungeon.bossLevel( Dungeon.depth + 1 ) ? Terrain.HIGH_GRASS : Terrain.CHASM );

		Point c = room.center();
		Room.Door door = room.entrance();
		door.set(Room.Door.Type.EMPTY);

		fill( level, c.x - 1, c.y - 1, 3, 3, Terrain.EMBERS );
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
		Yig yig = new Yig();
		yig.pos = pos-1;
		yig.SLEEPING.status();
		level.mobs.add( yig );

		level.addItemToSpawn( new PotionOfLevitation() );



		door.set( Room.Door.Type.EMPTY );
	}
}