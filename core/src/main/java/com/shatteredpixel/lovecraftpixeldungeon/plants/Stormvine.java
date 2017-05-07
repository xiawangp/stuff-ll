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
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants.LivingPlantStormVine;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Stormvine extends Plant {

	{
		image = 9;
	}

	@Override
	public void activate() {
		if(Randomer.randomBoolean()){
			Char ch = Actor.findChar(pos);

			if (ch != null) {
				Buff.affect(ch, Vertigo.class, Vertigo.duration( ch ) );
			}
			if (Dungeon.visible[pos]) {
				CellEmitter.get( pos ).burst( Speck.factory( Speck.STEAM ), 4 );
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
				Mob livingPlantStormvine;
				livingPlantStormvine = new LivingPlantStormVine();
				livingPlantStormvine.pos = Random.element( spawnPoints );

				GameScene.add(livingPlantStormvine);
				Actor.addDelayed( new Pushing( livingPlantStormvine, pos, livingPlantStormvine.pos ), -1 );

				if (Dungeon.visible[pos]) {
					CellEmitter.get( pos ).burst( Speck.factory( Speck.STEAM ), 4 );
				}
				if (Dungeon.visible[livingPlantStormvine.pos]) {
					CellEmitter.get( livingPlantStormvine.pos ).burst( Speck.factory( Speck.STEAM ), 4 );
				}
			}
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_STORMVINE;

			plantClass = Stormvine.class;
			alchemyClass = PotionOfLevitation.class;
		}
	}
}
