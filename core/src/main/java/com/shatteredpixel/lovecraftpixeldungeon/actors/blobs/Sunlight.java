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
package com.shatteredpixel.lovecraftpixeldungeon.actors.blobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.SunLightParticle;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Plant;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class Sunlight extends Blob {
	
	@Override
	protected void evolve() {
		super.evolve();

		int cell;

		for (int i = area.left; i < area.right; i++) {
			for (int j = area.top; j < area.bottom; j++) {
				cell = i + j * Dungeon.level.width();
				if(Dungeon.level.map[cell] == Terrain.EMBERS){
					Dungeon.level.map[cell] = Terrain.GRASS;
				} else if(Dungeon.level.map[cell] == Terrain.GRASS){
					Dungeon.level.map[cell] = Terrain.HIGH_GRASS;
				} else if(Dungeon.level.map[cell] == Terrain.HIGH_GRASS){
					GLog.p("spawn!");
					int rand = Random.Int(0, 2);
					if(rand == 0){
						Dungeon.level.plant((Plant.Seed)Generator.random(Generator.Category.SEED), cell);
						Dungeon.level.map[cell] = Terrain.EMPTY_DECO;
					} else if(rand == 1){
						Dungeon.level.drop(Generator.random(Generator.Category.SHROOMS), cell);
					} else {
						Dungeon.level.drop(Generator.random(Generator.Category.SEED), cell);
					}
				}
			}
		}
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.pour(SunLightParticle.FACTORY, 1f);
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
