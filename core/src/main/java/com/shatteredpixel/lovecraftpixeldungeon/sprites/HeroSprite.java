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

import android.graphics.RectF;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.MiGo;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.NormalShoggoth;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.ToothFaierie;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Splash;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class HeroSprite extends CharSprite {
	
	private static final int FRAME_WIDTH	= 12;
	private static final int FRAME_HEIGHT	= 15;
	
	private static final int RUN_FRAMERATE	= 20;
	
	private static TextureFilm tiers;
	
	private Animation fly;
	private Animation read;

	public HeroSprite() {
		super();
		
		link( Dungeon.hero );
		
		texture( Dungeon.hero.heroClass.spritesheet() );
		updateArmor();

		if (ch.isAlive())
			idle();
		else
			die();
	}

	public void changeSkin(Mob mob) {

		if(mob instanceof MiGo){

			texture(Assets.MIGO);

			TextureFilm film = new TextureFilm( texture, 16, 16 );

			idle = new Animation( 1, true );
			idle.frames( film, 0, 1, 0, 2 );

			run = new Animation( RUN_FRAMERATE, true );
			run.frames( film, 3, 4, 5, 6 );

			die = new Animation( 20, false );
			die.frames( film, 10, 11, 12, 13 );

			attack = new Animation( 15, false );
			attack.frames( film, 7, 8, 9  );

			zap = attack.clone();

			operate = new Animation( 8, false );
			operate.frames( film, 0, 1, 0, 2  );

			fly = new Animation( 1, true );
			fly.frames( film, 1 );

			read = new Animation( 20, false );
			read.frames( film, 7, 8, 9 );

		} else if(mob instanceof NormalShoggoth){

			texture(Assets.NSHOOGOTH);

			TextureFilm film = new TextureFilm( texture, 20, 14 );

			idle = new Animation( 10, true );
			idle.frames( film, 2, 3, 1 );

			run = new Animation( 15, true );
			run.frames( film, 2, 8, 4, 3 );

			die = new Animation( 20, false );
			die.frames( film, 5, 6, 7 );

			attack = new Animation( 10, false );
			attack.frames( film, 2, 8, 9, 10  );

			zap = attack.clone();

			operate = new Animation( 25, false );
			operate.frames( film, 2, 1, 0, 1, 2, 3, 4, 3, 2, 3, 4, 5  );

			fly = new Animation( 25, true );
			fly.frames( film, 2, 1, 0, 1, 2, 3, 4, 3, 2, 3, 4, 5 );

			read = new Animation( 20, false );
			read.frames( film, 2, 3, 4, 5, 4, 5, 4, 5, 8, 2 );

		} else if(mob instanceof ToothFaierie){

			texture(Assets.TOOTHFAE);

			TextureFilm frames = new TextureFilm( texture, 16, 16 );

			idle = new Animation( 15, true );
			idle.frames( frames, 0, 1, 2, 3 );

			run = new Animation( 10, true );
			run.frames( frames, 0, 1, 2, 3);

			attack = new Animation( 20, false );
			attack.frames( frames, 4, 0, 5, 1, 6, 2, 3, 7 );

			die = new Animation( 15, false );
			die.frames( frames, 8, 9, 10 );

			zap = attack.clone();

			operate = run.clone();

			fly = idle.clone();

			read = attack.clone();

		} else {

		}
	}
	
	public void updateArmor() {

		texture( Dungeon.hero.heroClass.spritesheet() );

		TextureFilm film = new TextureFilm( tiers(), ((Hero)ch).tier(), FRAME_WIDTH, FRAME_HEIGHT );
		
		idle = new Animation( 1, true );
		idle.frames( film, 0, 0, 0, 1, 0, 0, 1, 1 );
		
		run = new Animation( RUN_FRAMERATE, true );
		run.frames( film, 2, 3, 4, 5, 6, 7 );
		
		die = new Animation( 20, false );
		die.frames( film, 8, 9, 10, 11, 12, 11 );
		
		attack = new Animation( 15, false );
		attack.frames( film, 13, 14, 15, 0 );
		
		zap = attack.clone();
		
		operate = new Animation( 8, false );
		operate.frames( film, 16, 17, 16, 17 );
		
		fly = new Animation( 1, true );
		fly.frames( film, 18 );

		read = new Animation( 20, false );
		read.frames( film, 19, 20, 20, 20, 20, 20, 20, 20, 20, 19 );
	}
	
	@Override
	public void place( int p ) {
		super.place( p );
		Camera.main.target = this;
	}

	@Override
	public void move( int from, int to ) {
		super.move( from, to );
		if (ch.flying) {
			play( fly );
		}
		Camera.main.target = this;
	}

	@Override
	public void jump( int from, int to, Callback callback ) {
		super.jump( from, to, callback );
		play( fly );
	}

	public void read() {
		animCallback = new Callback() {
			@Override
			public void call() {
				idle();
				ch.onOperateComplete();
			}
		};
		play( read );
	}

	@Override
	public void bloodBurstA(PointF from, int damage) {
		if (visible) {
			PointF c = center();
			Splash.at( c, PointF.angle( from, c ), 3.1415926f / 2, blood(), Random.NormalIntRange(10, 100));
		}
	}

	@Override
	public void update() {
		sleeping = ch.isAlive() && ((Hero)ch).resting;
		
		super.update();
	}
	
	public boolean sprint( boolean on ) {
		run.delay = on ? 0.667f / RUN_FRAMERATE : 1f / RUN_FRAMERATE;
		return on;
	}
	
	public static TextureFilm tiers() {
		if (tiers == null) {
			SmartTexture texture = TextureCache.get( Assets.ROGUE );
			tiers = new TextureFilm( texture, texture.width, FRAME_HEIGHT );
		}
		
		return tiers;
	}
	
	public static Image avatar( HeroClass cl, int armorTier ) {
		
		RectF patch = tiers().get( armorTier );
		Image avatar = new Image( cl.spritesheet() );
		RectF frame = avatar.texture.uvRect( 1, 0, FRAME_WIDTH, FRAME_HEIGHT );
		frame.offset( patch.left, patch.top );
		avatar.frame( frame );
		
		return avatar;
	}
}
