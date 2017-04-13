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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.GooWarn;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.PurpleGooDrop;
import com.shatteredpixel.lovecraftpixeldungeon.items.RedGooDrop;
import com.shatteredpixel.lovecraftpixeldungeon.items.YellowGooDrop;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.DoubleShoogothSprite;
import com.shatteredpixel.lovecraftpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class DoubleShoggoth extends Mob {

	{
		HP = HT = 30+Dungeon.depth;
		EXP = Random.Int(1, 4);
		defenseSkill = 10+Dungeon.depth;
		spriteClass = DoubleShoogothSprite.class;
	}

	private int pumpedUp = 0;

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 10 );
	}

	@Override
	public int attackSkill( Char target ) {
		int attack = 16;
		if (HP*2 <= HT) attack = 21;
		if (pumpedUp > 0) attack *= 2;
		return attack;
	}

	@Override
	public int defenseSkill(Char enemy) {
		return (int)(super.defenseSkill(enemy) * ((HP*2 <= HT)? 1.5 : 1));
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}

	@Override
	public boolean act() {

		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			if (HP*2 == HT) {
				((DoubleShoogothSprite)sprite).spray(false);
			}
			HP++;
		}

		return super.act();
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		for (int n : PathFinder.NEIGHBOURS4) {
			int cell = this.pos + n;
			if (Level.passable[cell] && Actor.findChar( cell ) == null && Random.Int(4) == 2) {
				if(enemy == Dungeon.hero){
					Dungeon.hero.decreaseMentalHealth(2);
				}
				spawnAt( cell );
			}
		}
		return super.defenseProc(enemy, damage);
	}

	public static NormalShoggoth spawnAt(int pos ) {
		if (Level.passable[pos] && Actor.findChar( pos ) == null) {

			NormalShoggoth shoggoth = new NormalShoggoth();
			shoggoth.pos = pos;
			shoggoth.state = shoggoth.HUNTING;
			GameScene.add( shoggoth, 2f );

			shoggoth.sprite.alpha( 0 );
			shoggoth.sprite.parent.add( new AlphaTweener( shoggoth.sprite, 1, 0.5f ) );

			shoggoth.sprite.emitter().burst(DoubleShoogothSprite.GooParticle.FACTORY, 5 );

			return shoggoth;
		} else {
			return null;
		}
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		return (pumpedUp > 0) ? distance( enemy ) <= 2 : super.canAttack(enemy);
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		for (int n : PathFinder.NEIGHBOURS8) {
			int cell = this.pos + n;
			if (Actor.findChar( cell ) instanceof NormalShoggoth) {
				Char shoggoth = Actor.findChar(cell);
				shoggoth.die(this);
				shoggoth.sprite.killAndErase();
				this.HP = this.HT;
			}
		}
		mentalMinus(3, 4, enemy);
		if (Random.Int( 3 ) == 0) {
			Buff.affect( enemy, Ooze.class );
			enemy.sprite.burst( 0xA3F0A0, 5 );
		}

		if (pumpedUp > 0) {
			Camera.main.shake( 3, 0.2f );
		}

		return damage;
	}

	@Override
	protected boolean doAttack( Char enemy ) {
		if (pumpedUp == 1) {
			((DoubleShoogothSprite)sprite).pumpUp();
			PathFinder.buildDistanceMap( pos, BArray.not( Level.solid, null ), 2 );
			for (int i = 0; i < PathFinder.distance.length; i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE)
					GameScene.add(Blob.seed(i, 2, GooWarn.class));
			}
			pumpedUp++;

			spend( attackDelay() );

			return true;
		} else if (pumpedUp >= 2 || Random.Int( (HP*2 <= HT) ? 2 : 5 ) > 0) {

			boolean visible = Dungeon.visible[pos];

			if (visible) {
				if (pumpedUp >= 2) {
					((DoubleShoogothSprite) sprite).pumpAttack();
				}
				else
					sprite.attack( enemy.pos );
			} else {
				attack( enemy );
			}

			spend( attackDelay() );

			return !visible;

		} else {

			pumpedUp++;

			((DoubleShoogothSprite)sprite).pumpUp();

			for (int i=0; i < PathFinder.NEIGHBOURS9.length; i++) {
				int j = pos + PathFinder.NEIGHBOURS9[i];
				if (!Level.solid[j]) {
					GameScene.add(Blob.seed(j, 2, GooWarn.class));
				}
			}

			if (Dungeon.visible[pos]) {
				sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "!!!") );
			}

			spend( attackDelay() );

			return true;
		}
	}

	@Override
	public boolean attack( Char enemy ) {
		boolean result = super.attack( enemy );
		pumpedUp = 0;
		return result;
	}

	@Override
	protected boolean getCloser( int target ) {
		pumpedUp = 0;
		return super.getCloser( target );
	}

	@Override
	public void die( Object cause ) {
		super.die( cause );
		switch (Random.Int(0, 20)){
			case 0:
			case 1:
				Dungeon.level.drop(new PurpleGooDrop(), this.pos);
				break;
			case 2:
			case 3:
			case 4:
				Dungeon.level.drop(new RedGooDrop(), this.pos);
				break;
			case 5:
			case 6:
			case 7:
			case 8:
				Dungeon.level.drop(new YellowGooDrop(), this.pos);
				break;
			default:
				break;
		}
	}

	private final String PUMPEDUP = "pumpedup";

	@Override
	public void storeInBundle( Bundle bundle ) {

		super.storeInBundle( bundle );

		bundle.put( PUMPEDUP , pumpedUp );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {

		super.restoreFromBundle( bundle );

		pumpedUp = bundle.getInt( PUMPEDUP );

	}
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<>();
	static {
		RESISTANCES.add( ToxicGas.class );
		RESISTANCES.add( Grim.class );
		RESISTANCES.add( ScrollOfPsionicBlast.class );
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
