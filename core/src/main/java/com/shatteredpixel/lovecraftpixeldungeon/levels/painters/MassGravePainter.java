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

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.MiGo;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.items.Gold;
import com.shatteredpixel.lovecraftpixeldungeon.items.Heap;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.lovecraftpixeldungeon.items.quest.CorpseDust;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Room;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.ui.CustomTileVisual;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MassGravePainter extends Painter {

	public static void paint( Level level, Room room){

		Room.Door entrance = room.entrance();
		entrance.set(Room.Door.Type.BARRICADE);
		level.addItemToSpawn(new PotionOfLiquidFlame());

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_SP);

		level.customTiles.addAll(Bones.CustomTilesForRoom(room, Bones.class));

		//50% 1 skeleton, 50% 2 skeletons
		for (int i = 0; i <= Random.Int(2); i++){
			//TODO: SKELTONS
			MiGo skele = new MiGo();

			int pos;
			do {
				pos = level.pointToCell(room.random());
			} while (level.map[pos] != Terrain.EMPTY_SP || level.findMob(pos) != null);
			skele.pos = pos;
			level.mobs.add( skele );
		}

		ArrayList<Item> items = new ArrayList<>();
		//100% corpse dust, 2x100% 1 coin, 2x30% coins, 1x60% random item, 1x30% armor
		items.add(new CorpseDust());
		items.add(new Gold(1));
		items.add(new Gold(1));
		if (Random.Float() <= 0.3f) items.add(new Gold());
		if (Random.Float() <= 0.3f) items.add(new Gold());
		if (Random.Float() <= 0.6f) items.add(Generator.random());
		if (Random.Float() <= 0.3f) items.add(Generator.randomArmor());

		for (Item item : items){
			int pos;
			do {
				pos = level.pointToCell(room.random());
			} while (level.map[pos] != Terrain.EMPTY_SP || level.heaps.get(pos) != null);
			Heap h = level.drop(item, pos);
			h.type = Heap.Type.SKELETON;
		}
	}

	public static class Bones extends CustomTileVisual {
		{
			name = Messages.get(this, "name");

			tx = Assets.PRISON_QUEST;
			txX = 3;
			txY = 0;
		}

		@Override
		public String desc() {
			if (ofsX == 1 && ofsY == 1) {
				return Messages.get(this, "desc");
			} else {
				return null;
			}
		}
	}
}
