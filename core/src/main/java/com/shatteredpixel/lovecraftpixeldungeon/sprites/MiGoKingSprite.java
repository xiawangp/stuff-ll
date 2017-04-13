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
import com.shatteredpixel.lovecraftpixeldungeon.effects.Splash;
import com.watabou.noosa.TextureFilm;

public class MiGoKingSprite extends MobSprite {

	public MiGoKingSprite() {
		super();
		
		texture( Assets.MIGOKING );
		
		TextureFilm frames = new TextureFilm( texture, 20, 20 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 5, 1, 3, 4, 0, 3, 6, 2, 3, 1 );
		
		run = new Animation( 12, true );
		run.frames( frames, 3 );
		
		attack = new Animation( 12, false );
		attack.frames( frames, 0, 1, 2, 3 );
		
		die = new Animation( 10, false );
		die.frames( frames, 3, 7, 8, 9 );
		
		play( idle );
	}
	
	@Override
	public void die() {
		super.die();
		
		Splash.at( center(), 0x7EFD75, 12 );
	}
}
