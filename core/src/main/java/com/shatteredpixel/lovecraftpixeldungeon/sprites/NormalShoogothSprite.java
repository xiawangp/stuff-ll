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
package com.shatteredpixel.lovecraftpixeldungeon.sprites;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.Emitter.Factory;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class NormalShoogothSprite extends MobSprite {

	private Animation pump;
	private Animation pumpAttack;

	private Emitter spray;

	public NormalShoogothSprite() {
		super();

		texture( Assets.NSHOOGOTH );

		TextureFilm frames = new TextureFilm( texture, 20, 14 );

		idle = new Animation( 10, true );
		idle.frames( frames, 2, 3, 1 );

		run = new Animation( 15, true );
		run.frames( frames, 2, 8, 4, 3 );

		pump = new Animation( 20, true );
		pump.frames( frames, 2, 1, 0, 1, 2, 3, 4, 3, 2, 3, 4, 5 );

		pumpAttack = new Animation ( 20, false );
		pumpAttack.frames( frames, 2, 3, 4, 5, 4, 5, 4, 5, 8, 2);

		attack = new Animation( 10, false );
		attack.frames( frames, 2, 8, 9, 10 );

		die = new Animation( 10, false );
		die.frames( frames, 5, 6, 7 );

		play(idle);

		spray = centerEmitter();
		spray.autoKill = false;
		spray.pour( GooParticle.FACTORY, 0.04f );
		spray.on = false;
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		if (ch.HP*2 <= ch.HT)
			spray(true);
	}

	public void pumpUp() {
		play( pump );
	}

	public void pumpAttack() { play(pumpAttack); }

	@Override
	public int blood() {
		return 0xF0A0C8;
	}

	public void spray(boolean on){
		spray.on = on;
	}

	@Override
	public void update() {
		super.update();
		spray.pos(center());
		spray.visible = visible;
	}

	public static class GooParticle extends PixelParticle.Shrinking {

		public static final Factory FACTORY = new Factory() {
			@Override
			public void emit( Emitter emitter, int index, float x, float y ) {
				((GooParticle)emitter.recycle( GooParticle.class )).reset( x, y );
			}
		};

		public GooParticle() {
			super();

			color( 0xF0A0C8 );
			lifespan = 0.3f;

			acc.set( 0, +50 );
		}

		public void reset( float x, float y ) {
			revive();

			this.x = x;
			this.y = y;

			left = lifespan;

			size = 4;
			speed.polar( -Random.Float( PointF.PI ), Random.Float( 32, 48 ) );
		}

		@Override
		public void update() {
			super.update();
			float p = left / lifespan;
			am = p > 0.5f ? (1 - p) * 2f : 1;
		}
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete(anim);

		if (anim == pumpAttack) {

			idle();
			ch.onAttackComplete();
		} else if (anim == die) {
			spray.killAndErase();
		}
	}
}
