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

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Lightning;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.RainParticle;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.LightningTrap;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Random;

public class Storm extends Blob {
	
	@Override
	protected void evolve() {
		super.evolve();

		int cell;

		for (int i = area.left; i < area.right; i++) {
			for (int j = area.top; j < area.bottom; j++) {
				cell = i + j * Dungeon.level.width();
				if(Actor.findChar(cell) != null){
					Buff.affect(Actor.findChar(cell), Blindness.class, Vertigo.DURATION/10);
					Buff.affect(Actor.findChar(cell), Slow.class, Vertigo.DURATION/10);
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

				Char ch = Actor.findChar(i);

				if (ch != null) {
					Buff.detach(ch, Burning.class);
				}

				if(Random.Int(20) < 3){
					thunderstrike(cell);
				} else {
					if(Random.Int(140) == 5){
						listen();
					}
				}

			}
		}
	}

	public static void viewed() {
		Sample.INSTANCE.play(Assets.SND_BLAST);
		Camera.main.shake(3, 0.3f);
		Dungeon.hero.interrupt();
	}

	public void listen() {
		GLog.i(Messages.get(this, "listen"));
		Sample.INSTANCE.play(Assets.SND_BLAST, 1, 1, Random.Float(1.8f, 2.25f));
		Camera.main.shake(2, 0.2f);
	}

	public static void thunderstrike( int cell ) {

		Char is = Actor.findChar( cell );

		if (is != null) {
			Emitter emitter = CellEmitter.get( cell );

			int power = Random.IntRange(is.HT / 2, is.HT / 3);

			if ( is instanceof Hero) {
				is.damage(is.HP/8, LightningTrap.LIGHTNING);
				viewed();
				Sample.INSTANCE.play(Assets.SND_BLAST);
			} else {
				is.damage(power, LightningTrap.LIGHTNING);
			}

			if( Dungeon.visible[ cell ] ) {

				emitter.parent.add(new Lightning(cell + Dungeon.level.width(), cell - Dungeon.level.width(), null));

				emitter.parent.add(new Lightning(cell - 1, cell + 1, null));

				emitter.burst(Speck.factory(Speck.DISCOVER), Random.Int(4, 6));

			}

			for (Mob mob : Dungeon.level.mobs) {
				if (Dungeon.level.distance( cell, mob.pos ) <= 4 ) {
					mob.beckon(cell);
				}
			}
		}
	}


	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.pour(RainParticle.FACTORY, 1f);
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
