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
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Random;

public class OffspringOfVthyarilopsSprite extends MobSprite {
	
	public OffspringOfVthyarilopsSprite() {
		super();

		renderShadow = false;
		perspectiveRaise = 0.2f;

		int rand = Random.Int(0, 2);
		if(rand == 0){
			texture( Assets.PIRANHA1 );
		} else if(rand == 1){
			texture( Assets.PIRANHA2 );
		} else {
			texture( Assets.PIRANHA3 );
		}
		
		TextureFilm frames = new TextureFilm( texture, 12, 16 );
		
		idle = new Animation( 8, true );
		idle.frames( frames, 0, 1, 2, 1 );
		
		run = new Animation( 20, true );
		run.frames( frames, 0, 1, 2, 1 );
		
		attack = new Animation( 20, false );
		attack.frames( frames, 3, 4, 5, 6, 7, 8, 9, 10, 11 );
		
		die = new Animation( 4, false );
		die.frames( frames, 12, 13, 14 );
		
		play( idle );
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		renderShadow = false;
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );
		
		if (anim == attack) {
			GameScene.ripple( ch.pos );
		}
	}
}
