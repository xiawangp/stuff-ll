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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.Journal;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.PoorThief;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.items.IronBar;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ImprisonedThiefSprite;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndQuest;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ImprisonedThieves extends NPC {
	
	{
		spriteClass = ImprisonedThiefSprite.class;

		properties.add(Property.IMMOVABLE);
	}

	@Override
	protected boolean act() {
		if(Dungeon.visible[pos]){
			Journal.add(Journal.Feature.CAGE);
		}
		return super.act();
	}

	private final String[] PLEAS = {
			Messages.get(this, "plea0"),
			Messages.get(this, "plea1"),
			Messages.get(this, "plea2"),
			Messages.get(this, "plea3"),
			Messages.get(this, "plea4"),
			Messages.get(this, "plea5"),
			Messages.get(this, "plea6"),
			Messages.get(this, "plea7"),
			Messages.get(this, "plea8"),
			Messages.get(this, "plea9"),
			Messages.get(this, "plea10"),
			Messages.get(this, "plea11"),
			Messages.get(this, "plea12"),
			Messages.get(this, "plea13"),
			Messages.get(this, "plea14"),
	};
	
	@Override
	public boolean interact() {
		
		sprite.turnTo( pos, Dungeon.hero.pos );

		tell(Random.element(PLEAS));

		Journal.remove(Journal.Feature.CAGE);

		ArrayList<Integer> spawnPoints = new ArrayList<>();
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p]) && !Level.pit[p]) {
				spawnPoints.add( p );
			}
		}
		if (spawnPoints.size() > 0) {
			PoorThief poor = new PoorThief();
			poor.pos = Random.element( spawnPoints );
			poor.state = poor.WANDERING;
			GameScene.add( poor );
			Buff.affect(poor, Corruption.class);
			Actor.addDelayed( new Pushing( poor, pos, poor.pos ), -1 );
		} else {
			PoorThief poor = new PoorThief();
			poor.pos = pos;
			poor.state = poor.WANDERING;
			GameScene.add( poor );
			Buff.affect(poor, Corruption.class);
			Actor.addDelayed( new Pushing( poor, pos, poor.pos ), -1 );
		}

		this.die(null);
		this.sprite.die();

		Dungeon.level.drop(new IronBar(), pos);

		return false;
	}
	
	private void tell( String text ) {
		GameScene.show( new WndQuest( this, text ) );
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
}
