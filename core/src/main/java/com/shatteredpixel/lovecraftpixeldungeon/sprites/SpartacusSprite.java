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
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.ShadowParticle;
import com.watabou.noosa.TextureFilm;

public class SpartacusSprite extends MobSprite {

	public SpartacusSprite() {
		super();

		texture( Assets.GUARDS );

		TextureFilm frames = new TextureFilm( texture, 12, 16 );

		idle = new Animation( 2, true );
		idle.frames( frames, 0+(1*15), 0+(1*15), 0+(1*15), 1+(1*15), 0+(1*15), 0+(1*15), 1+(1*15), 1+(1*15) );

		run = new Animation( 15, true );
		run.frames( frames, 2+(1*15), 3+(1*15), 4+(1*15), 5+(1*15), 6+(1*15), 7+(1*15) );

		attack = new Animation( 12, false );
		attack.frames( frames, 8+(1*15), 9+(1*15), 10+(1*15) );

		die = new Animation( 8, false );
		die.frames( frames, 11+(1*15), 12+(1*15), 13+(1*15), 14+(1*15) );

		play( idle );
	}

	@Override
	public void play( Animation anim ) {
		if (anim == die) {
			emitter().burst( ShadowParticle.UP, 4 );
		}
		super.play( anim );
	}
}