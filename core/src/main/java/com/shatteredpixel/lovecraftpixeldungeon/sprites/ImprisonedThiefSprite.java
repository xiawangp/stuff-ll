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

public class ImprisonedThiefSprite extends MobSprite {

	public ImprisonedThiefSprite() {
		super();
		
		texture( Assets.IMPRTHIEF );
		TextureFilm film = new TextureFilm( texture, 16, 17 );
		
		idle = new Animation( 1, true );
		idle.frames( film, 0, 0, 0, 1, 0, 0, 0, 0, 1 );
		
		run = new Animation( 15, true );
		run.frames( film, 0, 1, 2, 3, 3, 4 );
		
		die = new Animation( 10, false );
		die.frames( film, 8, 9, 8, 9, 8 );
		
		attack = new Animation( 12, false );
		attack.frames( film, 5, 6, 7, 0 );
		
		idle();
	}
}
