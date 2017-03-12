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
package com.shatteredpixel.lovecraftpixeldungeon.items.scrolls;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Alb;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.AlbParticle;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.PathFinder;

public class ScrollOfSummoning extends Scroll {

	{
		initials = 14;

		bones = true;
	}
	
	@Override
	protected void doRead() {
		
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();

		for (int n : PathFinder.NEIGHBOURS4) {
			int cell = curUser.pos + n;
			if (Level.passable[cell] && Actor.findChar( cell ) == null) {
				curUser.decreaseMentalHealth(2);
				spawnAt( cell );
			}
		}
		
		setKnown();
		readAnimation();
	}

	public static Alb spawnAt(int pos ) {
		if (Level.passable[pos] && Actor.findChar( pos ) == null) {

			Alb alb = new Alb();
			if(!Randomer.randomBoolean()){
				Buff.affect(alb, Corruption.class);
			}
			alb.pos = pos;
			alb.state = alb.HUNTING;
			GameScene.add( alb, 2f );

			alb.sprite.alpha( 0 );
			alb.sprite.parent.add( new AlphaTweener( alb.sprite, 1, 0.5f ) );

			alb.sprite.emitter().burst(AlbParticle.FACTORY, 5 );

			return alb;
		} else {
			return null;
		}
	}
	
	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
