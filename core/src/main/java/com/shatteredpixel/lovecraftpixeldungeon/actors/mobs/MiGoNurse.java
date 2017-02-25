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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MiGoBirthHelperSprite;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MiGoNurse extends Mob {

	{
		spriteClass = MiGoBirthHelperSprite.class;
		
		HP = HT = 6+ Dungeon.depth;
		defenseSkill = 4+(Dungeon.depth/2);

		EXP = 2;
		
		maxLvl = 5;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		mentalMinus(2, 8, enemy);
		return super.attackProc(enemy, damage);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 4 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 8;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 1);
	}

	@Override
	protected boolean act() {
		if(Randomer.randomBoolean()){
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar( p ) instanceof MiGoQueen) {
					Actor.findChar( p ).HP++;
				}
			}
		} else {
			if(HP > HT/2){
				HP = HP/2;
				ArrayList<Integer> spawnPoints = new ArrayList<>();

				for (int i=0; i < PathFinder.NEIGHBOURS8.length; i++) {
					int p = pos + PathFinder.NEIGHBOURS8[i];
					if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
						spawnPoints.add( p );
					}
				}

				if (spawnPoints.size() > 0) {
					MiGoQueen.MiGoLarva miGo = new MiGoQueen.MiGoLarva();
					miGo.pos = Random.element( spawnPoints );

					GameScene.add( miGo );
					Actor.addDelayed( new Pushing( miGo, pos, miGo.pos ), -1 );
				}
			}
		}
		return super.act();
	}
}
