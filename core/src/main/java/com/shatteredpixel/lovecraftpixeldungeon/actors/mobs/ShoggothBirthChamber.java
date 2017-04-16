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
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.lovecraftpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ShoggothBirthChamberSprite;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.TwoShoggothBossSprite;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class ShoggothBirthChamber extends Mob {

	{
		HP = HT = 200+Dungeon.depth;
		EXP = 20;
		defenseSkill = 15+Dungeon.depth;
		spriteClass = ShoggothBirthChamberSprite.class;
		properties.add(Property.BOSS);
		properties.add(Property.IMMOVABLE);
	}

	@Override
	public int damageRoll() {
		int min = 1;
		int max = 9;
		return Random.NormalIntRange( min, max );
	}

	@Override
	public int attackSkill( Char target ) {
		int attack = 10;
		return attack;
	}

	@Override
	public int defenseSkill(Char enemy) {
		return super.defenseSkill(enemy) * 2;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		for (int n : PathFinder.NEIGHBOURS4) {
			int cell = this.pos + n;
			if (Level.passable[cell] && Actor.findChar( cell ) == null) {
				if(enemy == Dungeon.hero){
					Dungeon.hero.decreaseMentalHealth(2);
				}
				if(Randomer.randomBoolean()){
					spawnNormalAt(cell);
				} else {
					if(Randomer.randomBoolean()){
						spawnTwoShoggoth(cell);
					} else {
						spawnDoubleAt(cell);
					}
				}
			}
		}
		return super.defenseProc(enemy, damage);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		if(Dungeon.depth == 5){
			Dungeon.level.drop(new SkeletonKey(Dungeon.depth), this.pos);
		}
	}

	public static NormalShoggoth spawnNormalAt(int pos ) {
		if (Level.passable[pos] && Actor.findChar( pos ) == null) {

			NormalShoggoth shoggoth = new NormalShoggoth();
			shoggoth.pos = pos;
			shoggoth.state = shoggoth.HUNTING;
			GameScene.add( shoggoth, 2f );

			shoggoth.sprite.alpha( 0 );
			shoggoth.sprite.parent.add( new AlphaTweener( shoggoth.sprite, 1, 0.5f ) );

			shoggoth.sprite.emitter().burst(TwoShoggothBossSprite.GooParticle.FACTORY, 5 );

			return shoggoth;
		} else {
			return null;
		}
	}

	public static DoubleShoggoth spawnDoubleAt(int pos ) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {

			DoubleShoggoth shoggoth = new DoubleShoggoth();
			shoggoth.pos = pos;
			shoggoth.state = shoggoth.HUNTING;
			GameScene.add(shoggoth, 2f);

			shoggoth.sprite.alpha(0);
			shoggoth.sprite.parent.add(new AlphaTweener(shoggoth.sprite, 1, 0.5f));

			shoggoth.sprite.emitter().burst(TwoShoggothBossSprite.GooParticle.FACTORY, 5);

			return shoggoth;
		} else {
			return null;
		}
	}

	public static TwoShoggoth spawnTwoShoggoth(int pos ) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {

			TwoShoggoth shoggoth = new TwoShoggoth();
			shoggoth.pos = pos;
			shoggoth.state = shoggoth.HUNTING;
			GameScene.add(shoggoth, 2f);

			shoggoth.sprite.alpha(0);
			shoggoth.sprite.parent.add(new AlphaTweener(shoggoth.sprite, 1, 0.5f));

			shoggoth.sprite.emitter().burst(TwoShoggothBossSprite.GooParticle.FACTORY, 5);

			return shoggoth;
		} else {
			return null;
		}
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
