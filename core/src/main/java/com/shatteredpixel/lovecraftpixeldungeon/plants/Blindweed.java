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
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants.LivingPlantBlindWeed;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Blindweed extends Plant {
	
	{
		image = 3;
	}
	
	@Override
	public void activate() {
		if(Randomer.randomBoolean()){
			Char ch = Actor.findChar(pos);

			if (ch != null) {
				int len = Random.Int( 5, 10 );
				Buff.prolong( ch, Blindness.class, len );
				Buff.prolong( ch, Cripple.class, len );
				if (ch instanceof Mob) {
					if (((Mob)ch).state == ((Mob)ch).HUNTING) ((Mob)ch).state = ((Mob)ch).WANDERING;
					((Mob)ch).beckon( Dungeon.level.randomDestination() );
				}
			}

			if (Dungeon.visible[pos]) {
				CellEmitter.get( pos ).burst( Speck.factory( Speck.LIGHT ), 4 );
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
				Mob livingPlantBlindweed;
				livingPlantBlindweed = new LivingPlantBlindWeed();
				livingPlantBlindweed.pos = Random.element( spawnPoints );

				GameScene.add(livingPlantBlindweed);
				Actor.addDelayed( new Pushing( livingPlantBlindweed, pos, livingPlantBlindweed.pos ), -1 );

				if (Dungeon.visible[pos]) {
					CellEmitter.get( pos ).burst( Speck.factory( Speck.LIGHT ), 4 );
				}
				if (Dungeon.visible[livingPlantBlindweed.pos]) {
					CellEmitter.get( livingPlantBlindweed.pos ).burst( Speck.factory( Speck.LIGHT ), 4 );
				}
			}
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_BLINDWEED;

			plantClass = Blindweed.class;
			alchemyClass = PotionOfInvisibility.class;
		}
	}
}
