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