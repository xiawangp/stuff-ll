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
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.watabou.noosa.TextureFilm;

public class FireElementalSprite extends MobSprite {
	
	public FireElementalSprite() {
		super();
		
		texture( Assets.FIREELEME );
		
		TextureFilm frames = new TextureFilm( texture, 12, 14 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0, 1, 2 );
		
		run = new Animation( 12, true );
		run.frames( frames, 0, 1, 3 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 4, 5, 6 );
		
		die = new Animation( 15, false );
		die.frames( frames, 7, 8, 9, 10, 11, 12, 13, 12 );
		
		play( idle );
	}
	
	@Override
	public void link( Char ch ) {
		super.link( ch );
		add( State.BURNING );
	}
	
	@Override
	public void die() {
		super.die();
		remove( State.BURNING );
	}
	
	@Override
	public int blood() {
		return 0xFFFF7D13;
	}
}
