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
package com.shatteredpixel.lovecraftpixeldungeon.items.potions;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class PotionOfLiquidFlame extends Potion {

	{
		initials = 5;
	}

	@Override
	public void shatter( int cell ) {

		if (Dungeon.visible[cell]) {
			setKnown();

			splash( cell );
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}

		for (int offset : PathFinder.NEIGHBOURS9){
			if (Level.flamable[cell+offset]
					|| Actor.findChar(cell+offset) != null
					|| Dungeon.level.heaps.get(cell+offset) != null) {

				GameScene.add(Blob.seed(cell + offset, 2, Fire.class));

			} else {

				CellEmitter.get(cell+offset).burst(FlameParticle.FACTORY, 2);

			}
		}
	}
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
