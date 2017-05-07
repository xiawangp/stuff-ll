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
package com.shatteredpixel.lovecraftpixeldungeon.plants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants.LivingPlantSorrowMoss;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Sorrowmoss extends Plant {

	{
		image = 2;
	}
	
	@Override
	public void activate() {
		if(Randomer.randomBoolean()){
			Char ch = Actor.findChar(pos);

			if (ch != null) {
				Buff.affect( ch, Poison.class ).set( Poison.durationFactor( ch ) * (4 + Dungeon.depth / 2) );
			}

			if (Dungeon.visible[pos]) {
				CellEmitter.center( pos ).burst( PoisonParticle.SPLASH, 3 );
			}
		} else {
			ArrayList<Integer> spawnPoints = new ArrayList<>();

			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p]) && !Level.pit[p]) {
					spawnPoints.add( p );
				}
			}

			if (spawnPoints.size() > 0) {
				Mob livingPlantDreamfoil;
				livingPlantDreamfoil = new LivingPlantSorrowMoss();
				livingPlantDreamfoil.pos = Random.element( spawnPoints );

				GameScene.add(livingPlantDreamfoil);
				Actor.addDelayed( new Pushing( livingPlantDreamfoil, pos, livingPlantDreamfoil.pos ), -1 );

				if (Dungeon.visible[pos]) {
					CellEmitter.center( pos ).burst( PoisonParticle.SPLASH, 3 );
				}
				if (Dungeon.visible[livingPlantDreamfoil.pos]) {
					CellEmitter.center( livingPlantDreamfoil.pos ).burst( PoisonParticle.SPLASH, 3 );
				}
			}
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_SORROWMOSS;

			plantClass = Sorrowmoss.class;
			alchemyClass = PotionOfToxicGas.class;
		}
	}
}
