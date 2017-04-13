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
package com.shatteredpixel.lovecraftpixeldungeon.sprites;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.ShadowParticle;
import com.watabou.noosa.TextureFilm;

public class SpartacusSprite extends MobSprite {

	public SpartacusSprite() {
		super();

		texture( Assets.GUARDS );

		int rowlenght = 21;
		int row = 1;
		int newrow = row*rowlenght;

		TextureFilm frames = new TextureFilm( texture, 12, 16 );

		idle = new Animation( 2, true );
		idle.frames( frames, 0+newrow, 0+newrow, 0+newrow, 1+newrow, 0+newrow, 0+newrow, 1+newrow, 1+newrow );

		run = new Animation( 15, true );
		run.frames( frames, 2+newrow, 3+newrow, 4+newrow, 5+newrow, 6+newrow, 7+newrow );

		attack = new Animation( 12, false );
		attack.frames( frames, 8+newrow, 9+newrow, 10+newrow );

		die = new Animation( 8, false );
		die.frames( frames, 11+newrow, 12+newrow, 13+newrow, 14+newrow );

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