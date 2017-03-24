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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.lovecraftpixeldungeon.effects.MiGoTounge;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.lovecraftpixeldungeon.items.MongolianEgg;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.LightningTrap;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MongolianDeathWormSprite;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class MongolianDeathWorm extends Mob {

	{
		spriteClass = MongolianDeathWormSprite.class;

		HP = HT = 60;
		defenseSkill = 20;

		EXP = 6;
		maxLvl = 14;

		loot = MongolianEgg.class;
		lootChance = 0.1f;

		properties.add(Property.DEMONIC);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(4, 12);
	}

	@Override
	protected boolean act() {
		Dungeon.level.updateFieldOfView( this, Level.fieldOfView );

		if (Level.water[pos]) {
			die(null);
			sprite.killAndErase();
			return true;
		}
		for(int i : PathFinder.NEIGHBOURS8){
			if(findChar(i) != null && findChar(i) instanceof MongolianDeathWorm){
				die(null);
				sprite.killAndErase();
				Dungeon.level.drop(new MongolianEgg(), this.pos);
			}
		}

		if (state == HUNTING &&
				paralysed <= 0 &&
				enemy != null &&
				enemy.invisible == 0 &&
				Level.fieldOfView[enemy.pos] &&
				Dungeon.level.distance( pos, enemy.pos ) < 5 &&
				!Dungeon.level.adjacent( pos, enemy.pos ) &&
				Random.Int(3) == 0 &&

				chain(enemy.pos)) {

			return false;

		} else {
			return super.act();
		}
	}

	@Override
	protected boolean doAttack( Char enemy ) {

		if (Dungeon.level.distance( pos, enemy.pos ) <= 1 && Randomer.randomBoolean()) {

			return super.doAttack( enemy );

		} else {

			boolean visible = Level.fieldOfView[pos] || Level.fieldOfView[enemy.pos];
			if (visible) {
				sprite.zap( enemy.pos );
			}

			spend( 1f );

			if (hit( this, enemy, true )) {
				int dmg = Random.NormalIntRange(3, 10);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage( dmg, LightningTrap.LIGHTNING );

				enemy.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake( 2, 0.3f );

					if (!enemy.isAlive()) {
						Dungeon.fail( getClass() );
						GLog.n( Messages.get(this, "zap_kill") );
					}
				}
			} else {
				enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
			}

			return !visible;
		}
	}

	private boolean chain(int target){
		if (enemy.properties().contains(Property.IMMOVABLE))
			return false;

		Ballistica chain = new Ballistica(pos, target, Ballistica.PROJECTILE);

		if (chain.collisionPos != enemy.pos || chain.path.size() < 1 || Level.pit[chain.path.get(1)])
			return false;
		else {
			int newPos = -1;
			for (int i : chain.subPath(1, chain.dist)){
				if (!Level.solid[i] && Actor.findChar(i) == null){
					newPos = i;
					break;
				}
			}

			if (newPos == -1){
				return false;
			} else {
				final int newPosFinal = newPos;
				sprite.parent.add(new MiGoTounge(sprite.center(), enemy.sprite.center(), new Callback() {
					public void call() {
						Actor.addDelayed(new Pushing(enemy, enemy.pos, newPosFinal, new Callback(){
							public void call() {
								enemy.pos = newPosFinal;
								Dungeon.level.press(newPosFinal, enemy);
								Cripple.prolong(enemy, Cripple.class, 4f);
								if (enemy == Dungeon.hero) {
									Dungeon.hero.interrupt();
									Dungeon.observe();
									GameScene.updateFog();
								}
							}
						}), -1);
						next();
					}
				}));
			}
		}
		return true;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (enemy instanceof Mob) {
			((Mob)enemy).aggro( this );
		}
		if(Random.Int(10) >= 8){
			for(int i : PathFinder.NEIGHBOURS8){
				if(Level.water[i]){
					int oldTile = Dungeon.level.map[i];
					Dungeon.level.set(i, Terrain.EMPTY_DECO);

					if (Dungeon.visible[i]) {
						GameScene.updateMap( i );
						GameScene.discoverTile( i, oldTile );
					}
				}
			}
		}
		if(Random.Int(10) <= 5){
			Buff.affect(enemy, Roots.class, 3f);
		}
		return damage;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( Poison.class );
		IMMUNITIES.add( Amok.class );
		IMMUNITIES.add(Levitation.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	@Override
	public int attackSkill( Char target ) {
		return 20;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 8);
	}
}
