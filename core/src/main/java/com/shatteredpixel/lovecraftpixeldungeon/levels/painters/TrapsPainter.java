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
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.items.Heap;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Room;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.BlazingTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.ConfusionTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.DisintegrationTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.FlockTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.ParalyticTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.SpearTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.TeleportationTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.ToxicTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.VenomTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.WarpingTrap;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class TrapsPainter extends Painter {

	public static void paint( Level level, Room room ) {
		 
		fill( level, room, Terrain.WALL );

		Class<? extends Trap> trapClass;
		switch (Random.Int(5)){
			case 0: default:
				trapClass = SpearTrap.class;
				break;
			case 1:
				trapClass = !Dungeon.bossLevel(Dungeon.depth + 1)? null : SummoningTrap.class;
				break;
			case 2: case 3: case 4:
				trapClass = Random.oneOf(levelTraps[Dungeon.depth/5]);
				break;
		}

		if (trapClass == null){
			fill(level, room, 1, Terrain.CHASM);
		} else {
			fill(level, room, 1, Terrain.TRAP);
		}
		
		Room.Door door = room.entrance();
		door.set( Room.Door.Type.REGULAR );
		
		int lastRow = level.map[room.left + 1 + (room.top + 1) * level.width()] == Terrain.CHASM ? Terrain.CHASM : Terrain.EMPTY;

		int x = -1;
		int y = -1;
		if (door.x == room.left) {
			x = room.right - 1;
			y = room.top + room.height() / 2;
			fill( level, x, room.top + 1, 1, room.height() - 1 , lastRow );
		} else if (door.x == room.right) {
			x = room.left + 1;
			y = room.top + room.height() / 2;
			fill( level, x, room.top + 1, 1, room.height() - 1 , lastRow );
		} else if (door.y == room.top) {
			x = room.left + room.width() / 2;
			y = room.bottom - 1;
			fill( level, room.left + 1, y, room.width() - 1, 1 , lastRow );
		} else if (door.y == room.bottom) {
			x = room.left + room.width() / 2;
			y = room.top + 1;
			fill( level, room.left + 1, y, room.width() - 1, 1 , lastRow );
		}

		for(Point p : room.getPoints()) {
			int cell = level.pointToCell(p);
			if (level.map[cell] == Terrain.TRAP){
				try {
					level.setTrap(((Trap) trapClass.newInstance()).reveal(), cell);
				} catch (Exception e) {
					LovecraftPixelDungeon.reportException(e);
				}
			}
		}
		
		int pos = x + y * level.width();
		if (Random.Int( 3 ) == 0) {
			if (lastRow == Terrain.CHASM) {
				set( level, pos, Terrain.EMPTY );
			}
			level.drop( prize( level ), pos ).type = Heap.Type.CHEST;
		} else {
			set( level, pos, Terrain.PEDESTAL );
			level.drop( prize( level ), pos );
		}
		
		level.addItemToSpawn( new PotionOfLevitation() );
	}
	
	private static Item prize( Level level ) {

		Item prize;

		if (Random.Int(3) != 0){
			prize = level.findPrizeItem();
			if (prize != null)
				return prize;
		}
		
		//1 floor set higher in probability, never cursed
		do {
			if (Random.Int(2) == 0) {
				prize = Generator.randomWeapon((Dungeon.depth / 5) + 1);
			} else {
				prize = Generator.randomArmor((Dungeon.depth / 5) + 1);
			}
		} while (prize.cursed);

		//33% chance for an extra update.
		if (!(prize instanceof MissileWeapon) && Random.Int(3) == 0){
			prize.upgrade();
		}
		
		return prize;
	}

	@SuppressWarnings("unchecked")
	private static Class<?extends Trap>[][] levelTraps = new Class[][]{
			//sewers
			{ToxicTrap.class, TeleportationTrap.class, FlockTrap.class},
			//prison
			{ConfusionTrap.class, ExplosiveTrap.class, ParalyticTrap.class},
			//caves
			{BlazingTrap.class, VenomTrap.class, ExplosiveTrap.class},
			//city
			{WarpingTrap.class, VenomTrap.class, DisintegrationTrap.class},
			//halls, muahahahaha
			{GrimTrap.class}
	};
}
