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
package com.shatteredpixel.lovecraftpixeldungeon.levels.traps;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Splash;
import com.shatteredpixel.lovecraftpixeldungeon.items.Heap;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class FrostTrap extends Trap {

	{
		color = WHITE;
		shape = STARS;
	}

	@Override
	public void activate() {

		if (Dungeon.visible[ pos ]){
			Splash.at( pos, 0xFFB2D6FF, 10);
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null) heap.freeze();

		Char ch = Actor.findChar(pos);
		if (ch != null){
			ch.damage(Random.NormalIntRange(1 , Dungeon.depth), this);
			Chill.prolong(ch, Frost.class, 10f + Random.Int(Dungeon.depth));
			if (!ch.isAlive() && ch == Dungeon.hero){
				Dungeon.fail( getClass() );
				GLog.n( Messages.get(this, "ondeath") );
			}
		}
	}
}
