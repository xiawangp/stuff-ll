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
import com.watabou.noosa.TextureFilm;

public class EarthElementSprite extends MobSprite {

	public EarthElementSprite() {
		super();
		
		texture( Assets.EARTHELEM );
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 12, true );
		idle.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7, 8, 5, 4, 3, 2, 1, 0);
		
		run = new Animation( 15, true );
		run.frames( frames, 5, 6, 7, 8 );
		
		attack = new Animation( 20, false );
		attack.frames( frames, 5, 4, 3, 2, 9, 10, 1, 0 );
		
		die = new Animation( 20, false );
		die.frames( frames, 0, 11, 12, 13 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xFFF200;
	}
}