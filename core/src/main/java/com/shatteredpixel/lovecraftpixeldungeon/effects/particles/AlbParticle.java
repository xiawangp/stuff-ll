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

package com.shatteredpixel.lovecraftpixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class AlbParticle extends PixelParticle {

    public static final Emitter.Factory FACTORY = new Emitter.Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((AlbParticle)emitter.recycle(AlbParticle.class )).reset( x, y );
        }
        @Override
        public boolean lightMode() {
            return true;
        };
    };

    public static final Emitter.Factory ATTRACTING = new Emitter.Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((AlbParticle)emitter.recycle(AlbParticle.class )).resetAttract( x, y );
        }
        @Override
        public boolean lightMode() {
            return true;
        };
    };

    public AlbParticle() {
        super();

        color( 0x5FD00B );
        tint( 0x5FD00B );
        hardlight( 0x5FD00B );
        lifespan = 0.5f;

        speed.set( Random.Float( -10, +10 ), Random.Float( -10, +10 ) );
    }

    public void reset( float x, float y ) {
        revive();

        this.x = x;
        this.y = y;

        left = lifespan;
    }

    public void resetAttract( float x, float y) {
        revive();

        //size = 8;
        left = lifespan;

        speed.polar( Random.Float( PointF.PI2 ), Random.Float( 16, 32 ) );
        this.x = x - speed.x * lifespan;
        this.y = y - speed.y * lifespan;
    }

    @Override
    public void update() {
        super.update();
        // alpha: 1 -> 0; size: 1 -> 4
        size( 4 - (am = left / lifespan) * 3 );
    }
}