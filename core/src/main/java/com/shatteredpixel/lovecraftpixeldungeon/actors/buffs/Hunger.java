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
package com.shatteredpixel.lovecraftpixeldungeon.actors.buffs;

import com.shatteredpixel.lovecraftpixeldungeon.Badges;
import com.shatteredpixel.lovecraftpixeldungeon.Challenges;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.lovecraftpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class Hunger extends Buff implements Hero.Doom {

	private static float STEP = 10f;

	public static final float HUNGRY	= 300f;
	public static final float STARVING	= 400f;

	private float level;
	private float partialDamage;

	private static final String LEVEL			= "level";
	private static final String PARTIALDAMAGE 	= "partialDamage";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( LEVEL, level );
		bundle.put( PARTIALDAMAGE, partialDamage );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		level = bundle.getFloat( LEVEL );
		partialDamage = bundle.getFloat(PARTIALDAMAGE);
	}

	@Override
	public boolean act() {

		float stepf;
		try{
			stepf = (float) Dungeon.hero.beloningsweight/Dungeon.hero.STR;
		} catch (NullPointerException e){
			stepf = 0f;
		}

		if(stepf < 0f){
			stepf = 1f;
		}

		STEP = stepf;

		if (Dungeon.level.locked){
			spend(STEP);
			return true;
		}

		if (target.isAlive()) {

			Hero hero = (Hero)target;

			if (isStarving()) {

				partialDamage += target.HT/100f;

				if (partialDamage > 1){
					target.damage( (int)partialDamage, this);
					partialDamage -= (int)partialDamage;
				}

			} else {

				float newLevel = level + STEP;
				boolean statusUpdated = false;
				if (newLevel >= STARVING) {

					GLog.n( Messages.get(this, "onstarving") );
					hero.resting = false;
					hero.damage( 1, this );
					statusUpdated = true;

					hero.interrupt();

				} else if (newLevel >= HUNGRY && level < HUNGRY) {

					GLog.w( Messages.get(this, "onhungry") );
					statusUpdated = true;

				} else if (level < 0) {
					if(((Hero) target).resting && target.MH > target.MMH && target.HP > target.HT){
						if(Randomer.randomBoolean()){
							target.MH++;
						} else {
							target.HP++;
						}
					}
					if(Randomer.randomInteger(10) == 1){
						GLog.w( Messages.get(this, "onoverfed") );
					}
					statusUpdated = true;
				}
				level = newLevel;

				if (statusUpdated) {
					BuffIndicator.refreshHero();
				}

			}

			float step = ((Hero)target).heroClass == HeroClass.ROGUE ? STEP * 1.2f : STEP;
			spend( target.buff( Shadows.class ) == null ? step : step * 1.5f );

		} else {

			diactivate();

		}

		return true;
	}

	public void satisfy( float energy ) {

		Artifact.ArtifactBuff buff = target.buff( HornOfPlenty.hornRecharge.class );
		if (buff != null && buff.isCursed()){
			energy *= 0.67f;
			GLog.n( Messages.get(this, "cursedhorn") );
		}

		if (!Dungeon.isChallenged(Challenges.NO_FOOD))
			reduceHunger( energy );
	}

	//directly interacts with hunger, no checks.
	public void reduceHunger( float energy ) {

		level -= energy;
		if (level < -25) {
			level = -25;
		} else if (level > STARVING) {
			level = STARVING;
		}

		BuffIndicator.refreshHero();
	}

	public boolean isStarving() {
		return level >= STARVING;
	}

	public boolean isHungry() {
		return level >= HUNGRY;
	}

	public int hunger() {
		return (int)Math.ceil(level);
	}

	@Override
	public int icon() {
		if (level < HUNGRY && level >= 0) {
			return BuffIndicator.NONE;
		} else if (level < STARVING && level >= 0) {
			return BuffIndicator.HUNGER;
		} else if(level < 0){
			return BuffIndicator.OVERFED;
		} else {
			return BuffIndicator.STARVATION;
		}
	}

	@Override
	public String toString() {
		if (level < HUNGRY && level >= 0) {
			return "";
		} else if (level < STARVING && level >= 0) {
			return Messages.get(this, "hungry");
		} else if(level < 0){
			return Messages.get(this, "overfed");
		} else {
			return Messages.get(this, "starving");
		}
	}

	@Override
	public String desc() {
		String result;
		if (level < HUNGRY && level >= 0) {
			return "";
		} else if (level < STARVING && level >= 0) {
			result = Messages.get(this, "desc_intro_hungry");
		} else if(level < 0){
			return Messages.get(this, "desc_intro_overfed");
		} else {
			result = Messages.get(this, "desc_intro_starving");
		}

		result += Messages.get(this, "desc");

		return result;
	}

	@Override
	public void onDeath() {

		Badges.validateDeathFromHunger();

		Dungeon.fail( getClass() );
		GLog.n( Messages.get(this, "ondeath") );
	}
}
