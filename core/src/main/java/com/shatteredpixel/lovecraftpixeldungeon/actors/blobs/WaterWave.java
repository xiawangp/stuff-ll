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
package com.shatteredpixel.lovecraftpixeldungeon.actors.blobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.lovecraftpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.WaterWaveParticle;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.watabou.utils.Random;

public class WaterWave extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		Char ch;
		int cell;

		for (int i = area.left; i < area.right; i++){
			for (int j = area.top; j < area.bottom; j++){
				cell = i + j* Dungeon.level.width();

				if (cur[cell] > 0 && (ch = Actor.findChar( cell )) != null) {
					if (!ch.immunities().contains(this.getClass()))
						Buff.affect(ch, Slow.class, Slow.duration(ch));
						Buff.affect(ch, Cripple.class, Slow.duration(ch));
					if(ch == Dungeon.hero){
						Burning.dispel();
					}
				}

				if (Random.Int(20) < 11
						&& (Dungeon.level.map[cell] == Terrain.GRASS
						|| Dungeon.level.map[cell] == Terrain.HIGH_GRASS
						|| Dungeon.level.map[cell] == Terrain.EMPTY
						|| Dungeon.level.map[cell] == Terrain.EMPTY_DECO
						|| Dungeon.level.map[cell] == Terrain.EMBERS)) {

					int oldTile = Dungeon.level.map[cell];
					Dungeon.level.set(cell, Terrain.WATER);

					if (Dungeon.visible[cell]) {
						GameScene.updateMap( cell );
						GameScene.discoverTile( cell, oldTile );
					}
				}
			}
		}
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );

		emitter.pour(WaterWaveParticle.FACTORY, 0.4f );
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
