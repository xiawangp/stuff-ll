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
package com.shatteredpixel.lovecraftpixeldungeon.actors.buffs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LarvaInfectedSprite;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Infected extends FlavourBuff {

	{
		type = buffType.NEGATIVE;
	}

	@Override
	public void detach() {
		super.detach();
		Dungeon.observe();
	}
	
	@Override
	public int icon() {
		return BuffIndicator.INFECTED;
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {
			if (Dungeon.depth > 4)
				target.damage( Dungeon.depth/5, this );
			else if (Random.Int(2) == 0)
				target.damage( 1, this );
			if (!target.isAlive() && target == Dungeon.hero) {
				Dungeon.fail( getClass() );
			}
			if(Random.Int(10) > 8){
				if(target != Dungeon.hero){
					Larva larva = new Larva();
					Buff.affect(larva, Corruption.class);
					do {
						larva.pos = target.pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
					} while (!Level.passable[larva.pos] && Actor.findChar( larva.pos ) == null);
					GameScene.add( larva );
					Actor.addDelayed( new Pushing( larva, target.pos, larva.pos ), -1 );
				} else {
					Larva larva = new Larva();
					do {
						larva.pos = target.pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
					} while (!Level.passable[larva.pos] && Actor.findChar( larva.pos ) == null);
					GameScene.add( larva );
					Actor.addDelayed( new Pushing( larva, target.pos, larva.pos ), -1 );
				}
			}
			spend( TICK );
		}
		if (Level.water[target.pos]) {
			detach();
		}
		return true;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}

	public static class Larva extends Mob {

		{
			spriteClass = LarvaInfectedSprite.class;

			HP = HT = 10+(Dungeon.hero.lvl);
			defenseSkill = 10+(Dungeon.hero.lvl/2);

			EXP = 1;

			state = HUNTING;

			properties.add(Property.DEMONIC);
		}

		@Override
		public int attackProc(Char enemy, int damage ) {

			if (Randomer.randomInteger(6) == 0 && enemy == Dungeon.hero) {
				Dungeon.hero.decreaseMentalHealth(1);
			}

			return damage;
		}

		@Override
		public int defenseProc(Char enemy, int damage) {
			return super.defenseProc(enemy, damage);
		}

		@Override
		public int attackSkill( Char target ) {
			return 30;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 3, 4 );
		}

		@Override
		public int drRoll() {
			return Random.NormalIntRange(0, 4);
		}

	}
}