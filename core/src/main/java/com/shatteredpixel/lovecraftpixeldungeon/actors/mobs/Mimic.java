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

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.Generator;
import com.shatteredpixel.lovecraftpixeldungeon.items.Gold;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.MimicSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Mimic extends Mob {
	
	private int level;
	
	{
		spriteClass = MimicSprite.class;

		properties.add(Property.DEMONIC);
	}
	
	public ArrayList<Item> items;
	
	private static final String LEVEL	= "level";
	private static final String ITEMS	= "items";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( ITEMS, items );
		bundle.put( LEVEL, level );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		items = new ArrayList<>( (Collection<Item>) ((Collection<?>) bundle.getCollection( ITEMS ) ));
		adjustStats( bundle.getInt( LEVEL ) );
		super.restoreFromBundle(bundle);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( HT / 10, HT / 4 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 9 + level;
	}
	
	public void adjustStats( int level ) {
		this.level = level;
		
		HP = HT = (1 + level) * 6;
		EXP = 2 + 2 * (level - 1) / 5;
		defenseSkill = attackSkill( null ) / 2;
		
		enemySeen = true;
	}
	
	@Override
	public void die( Object cause ) {

		super.die( cause );
		
		if (items != null) {
			for (Item item : items) {
				Dungeon.level.drop( item, pos ).sprite.drop();
			}
		}
	}
	
	@Override
	public boolean reset() {
		state = WANDERING;
		return true;
	}

	public static Mimic spawnAt( int pos, List<Item> items ) {
		Char ch = Actor.findChar( pos );
		if (ch != null) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : PathFinder.NEIGHBOURS8) {
				int cell = pos + n;
				if ((Level.passable[cell] || Level.avoid[cell]) && Actor.findChar( cell ) == null) {
					candidates.add( cell );
				}
			}
			if (candidates.size() > 0) {
				int newPos = Random.element( candidates );
				Actor.addDelayed( new Pushing( ch, ch.pos, newPos ), -1 );
				
				ch.pos = newPos;
				// FIXME
				if (ch instanceof Mob) {
					Dungeon.level.mobPress( (Mob)ch );
				} else {
					Dungeon.level.press( newPos, ch );
				}
			} else {
				return null;
			}
		}
		
		Mimic m = new Mimic();
		m.items = new ArrayList<>( items );
		m.adjustStats( Dungeon.depth );
		m.pos = pos;
		m.state = m.HUNTING;
		GameScene.add( m, 1 );
		
		m.sprite.turnTo( pos, Dungeon.hero.pos );
		
		if (Dungeon.visible[m.pos]) {
			CellEmitter.get( pos ).burst( Speck.factory( Speck.STAR ), 10 );
			Sample.INSTANCE.play( Assets.SND_MIMIC );
		}

		//generate an extra reward for killing the mimic
		switch(Random.Int(5)){
			case 0: case 1:
				m.items.add(new Gold().random()); break;
			case 2:
				m.items.add(Generator.randomArmor().identify()); break;
			case 3:
				m.items.add(Generator.randomWeapon().identify()); break;
			case 4:
				m.items.add(Generator.random(Generator.Category.RING).identify()); break;
		}
		
		return m;
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( ScrollOfPsionicBlast.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
