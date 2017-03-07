/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
import com.watabou.noosa.TextureFilm;

public class AirElementSprite extends MobSprite {

	public AirElementSprite() {
		super();
		
		texture( Assets.AIRELEMEN );
		
		TextureFilm frames = new TextureFilm( texture, 12, 16 );
		
		idle = new Animation( 12, true );
		idle.frames( frames, 0, 1, 2, 1, 3);
		
		run = new Animation( 15, true );
		run.frames( frames, 1, 3 );
		
		attack = new Animation( 20, false );
		attack.frames( frames, 1, 4, 5, 6, 2 );
		
		die = new Animation( 20, false );
		die.frames( frames, 7, 8, 9, 10, 11, 12 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0x98F9F6;
	}
}
