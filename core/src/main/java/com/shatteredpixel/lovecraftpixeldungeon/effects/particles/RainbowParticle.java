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
package com.shatteredpixel.lovecraftpixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class RainbowParticle extends PixelParticle {

	public static final Emitter.Factory BURST = new Emitter.Factory() {
		@Override
		public void emit( Emitter emitter, int index, float x, float y ) {
			((RainbowParticle)emitter.recycle( RainbowParticle.class )).resetBurst( x, y );
		}
		@Override
		public boolean lightMode() {
			return true;
		}
	};


	public RainbowParticle() {
		super();
		color( Random.Int( 0x1000000 ) );
		lifespan = 0.5f;
	}


	public void reset( float x, float y ) {
		revive();

		this.x = x;
		this.y = y;

		speed.set( Random.Float(-5, +5), Random.Float( -5, +5 ) );

		left = lifespan;
	}

	public void resetBurst( float x, float y ) {
		revive();

		this.x = x;
		this.y = y;

		speed.polar( Random.Float( PointF.PI2 ), Random.Float( 16, 32 ) );

		left = lifespan;
	}

	@Override
	public void update() {
		super.update();
		// alpha: 1 -> 0; size: 1 -> 5
		size( 5 - (am = left / lifespan) * 4 );
	}
}
