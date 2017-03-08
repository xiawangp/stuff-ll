/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.lovecraftpixeldungeon.sprites;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Alb;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Beam;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.AlbParticle;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;

public class AlbSprite extends MobSprite {

	private int zapPos;

	private Animation charging;
	private Emitter chargeParticles;

	public AlbSprite() {
		super();
		
		texture( Assets.ALB );
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );

		idle = new Animation( 5, true );
		idle.frames(frames, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7);

		charging = new Animation( 12, true);
		charging.frames( frames, 3, 4 );

		chargeParticles = centerEmitter();
		chargeParticles.autoKill = false;
		chargeParticles.pour(AlbParticle.ATTRACTING, 0.05f);
		chargeParticles.on = false;

		run = new Animation( 10, true );
		run.frames( frames, 0, 1, 2, 3 );

		attack = new Animation( 15, false );
		attack.frames( frames, 8, 9, 10, 11, 12 );
		zap = attack.clone();

		die = new Animation( 10, false );
		die.frames( frames, 16, 17, 18, 19, 20, 21 );
		
		play( idle );
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		if (((Alb)ch).beamCharged) play(charging);
	}

	@Override
	public void update() {
		super.update();
		chargeParticles.pos(center());
		chargeParticles.visible = visible;
	}

	public void charge( int pos ){
		turnTo(ch.pos, pos);
		play(charging);
	}

	@Override
	public void play(Animation anim) {
		chargeParticles.on = anim == charging;
		super.play(anim);
	}

	@Override
	public void zap( int pos ) {
		zapPos = pos;
		super.zap( pos );
	}
	
	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );
		
		if (anim == zap) {
			if (Actor.findChar(zapPos) != null){
				parent.add(new Beam.ScottRay(center(), Actor.findChar(zapPos).sprite.center()));
			} else {
				parent.add(new Beam.ScottRay(center(), DungeonTilemap.raisedTileCenterToWorld(zapPos)));
			}
			((Alb)ch).deathGaze();
			ch.next();
		} else if (anim == die){
			chargeParticles.killAndErase();
		}
	}
}
