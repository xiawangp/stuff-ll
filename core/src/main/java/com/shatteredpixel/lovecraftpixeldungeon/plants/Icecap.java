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
package com.shatteredpixel.lovecraftpixeldungeon.plants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants.LivingPlantIceCap;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.IceWindParticle;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Icecap extends Plant {
	
	{
		image = 1;
	}
	
	@Override
	public void activate() {

		if(Randomer.randomBoolean()){
			PathFinder.buildDistanceMap( pos, BArray.not( Level.losBlocking, null ), 1 );
		
			Fire fire = (Fire)Dungeon.level.blobs.get( Fire.class );
		
			for (int i=0; i < PathFinder.distance.length; i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				Freezing.affect( i, fire );
				}
			}
		} else {
			ArrayList<Integer> spawnPoints = new ArrayList<>();

			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add( p );
				}
			}

			if (spawnPoints.size() > 0) {
				Mob livingIcecapPlant;
				livingIcecapPlant = new LivingPlantIceCap();
				livingIcecapPlant.pos = Random.element( spawnPoints );

				GameScene.add(livingIcecapPlant);
				Actor.addDelayed( new Pushing( livingIcecapPlant, pos, livingIcecapPlant.pos ), -1 );

				if (Dungeon.visible[pos]) {
					CellEmitter.get( pos ).burst( IceWindParticle.FACTORY, 2 );
				}
				if (Dungeon.visible[livingIcecapPlant.pos]) {
					CellEmitter.get( livingIcecapPlant.pos ).burst( IceWindParticle.FACTORY, 2 );
				}
			}
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_ICECAP;

			plantClass = Icecap.class;
			alchemyClass = PotionOfFrost.class;
		}
	}
}
