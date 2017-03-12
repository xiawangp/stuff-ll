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

public class LivingSorrowmossPlantSprite extends MobSprite {

	public LivingSorrowmossPlantSprite() {
		super();

		texture(Assets.LPLANTS);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(5, true);
		idle.frames(frames, 0+(2*9), 1+(2*9));
		run = new Animation( 15, true);
		run.frames( frames, 0+(2*9), 1+(2*9), 2+(2*9), 3+(2*9), 4+(2*9), 5+(2*9));

		attack = new Animation( 12, false );
		attack.frames( frames, 0+(2*9), 2+(2*9), 3+(2*9), 4+(2*9), 0+(2*9));

		die = new Animation( 5, false);
		die.frames( frames, 6+(2*9), 7+(2*9), 8+(2*9));

		play(idle);
	}
}
