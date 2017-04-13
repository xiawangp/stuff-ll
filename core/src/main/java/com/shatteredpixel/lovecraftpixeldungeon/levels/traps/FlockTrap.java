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
package com.shatteredpixel.lovecraftpixeldungeon.levels.traps;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class FlockTrap extends Trap {

	{
		color = WHITE;
		shape = WAVES;
	}


	@Override
	public void activate() {
		//use an actor as we want to put this on a slight delay so all chars get a chance to act this turn first.
		Actor.add(new Actor() {

			{ actPriority = 3; }

			protected boolean act() {
				PathFinder.buildDistanceMap( pos, BArray.not( Level.solid, null ), 2 );
				for (int i = 0; i < PathFinder.distance.length; i++) {
					if (PathFinder.distance[i] < Integer.MAX_VALUE)
						if (Dungeon.level.insideMap(i) && Actor.findChar(i) == null && !(Level.pit[i])) {
						Sheep sheep = new Sheep();
						sheep.lifespan = 2 + Random.Int(Dungeon.depth + 10);
						sheep.pos = i;
						GameScene.add(sheep);
						CellEmitter.get(i).burst(Speck.factory(Speck.WOOL), 4);
					}
				}
				Sample.INSTANCE.play(Assets.SND_PUFF);
				Actor.remove(this);
				return true;
			}
		});

	}

}
