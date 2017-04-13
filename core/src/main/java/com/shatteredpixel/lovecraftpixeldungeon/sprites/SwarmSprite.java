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
import com.watabou.noosa.TextureFilm;

public class SwarmSprite extends MobSprite {
	
	public SwarmSprite() {
		super();
		
		texture( Assets.TOOTHFAE );
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 15, true );
		idle.frames( frames, 0, 1, 2, 3 );
		
		run = new Animation( 10, true );
		run.frames( frames, 0, 1, 2, 3);
		
		attack = new Animation( 20, false );
		attack.frames( frames, 4, 0, 5, 1, 6, 2, 3, 7 );
		
		die = new Animation( 15, false );
		die.frames( frames, 8, 9, 10 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xEC7190;
	}
}
