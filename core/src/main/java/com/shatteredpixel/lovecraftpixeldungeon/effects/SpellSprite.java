/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
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
package com.shatteredpixel.lovecraftpixeldungeon.effects;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;

import java.util.HashMap;

public class SpellSprite extends Image {

	public static final int FOOD		= 0;
	public static final int MAP			= 1;
	public static final int CHARGE		= 2;
	public static final int MASTERY		= 3;

	public static final int SCROLL_IDENTIFY	= 12;
	public static final int SCROLL_TRANSMUT = 13;
	public static final int SCROLL_FARSIGHT = 14;
	public static final int SCROLL_EXORCISM	= 15;
	public static final int SCROLL_SUNLIGHT	= 16;
	public static final int SCROLL_DARKNESS	= 17;
	public static final int SCROLL_CHALLENGE= 18;
	public static final int SCROLL_TELEPORT	= 19;
	public static final int SCROLL_MASSHARM = 20;
	public static final int SCROLL_RAISEDEAD= 21;
	public static final int SCROLL_UPGRADE	= 22;
	public static final int SCROLL_ENCHANT	= 23;

	public static final int SCROLL_IDENTIFY_SMALL	= 24;
    public static final int SCROLL_TRANSMUT_SMALL   = 25;
    public static final int SCROLL_FARSIGHT_SMALL   = 26;
    public static final int SCROLL_EXORCISM_SMALL	= 27;
    public static final int SCROLL_SUNLIGHT_SMALL	= 28;
    public static final int SCROLL_DARKNESS_SMALL	= 29;
    public static final int SCROLL_CHALLENGE_SMALL  = 30;
    public static final int SCROLL_TELEPORT_SMALL	= 31;
    public static final int SCROLL_MASSHARM_SMALL   = 32;
    public static final int SCROLL_RAISEDEAD_SMALL  = 33;
    public static final int SCROLL_UPGRADE_SMALL	= 34;
    public static final int SCROLL_ENCHANT_SMALL	= 35;

	public static final int COLOUR_HOLY	= 0xFFF799;
	public static final int COLOUR_RUNE	= 0x3499FF;
	public static final int COLOUR_DARK	= 0xFF0000;
	public static final int COLOUR_WILD	= 0xFF00FF;
	
	private static final int SIZE	= 16;
	
	private enum Phase {
		FADE_IN, STATIC, FADE_OUT
	};
	
	private static final float FADE_IN_TIME		= 0.2f;
	private static final float STATIC_TIME		= 0.8f;
	private static final float FADE_OUT_TIME	= 0.4f;
	
	private static TextureFilm film;
	
	private Char target;
	
	private Phase phase;
	private float duration;
	private float passed;
	
	private static HashMap<Char,SpellSprite> all = new HashMap<Char, SpellSprite>();
	
	public SpellSprite() {
		super( Assets.SPELL_ICONS );

		if (film == null) {
			film = new TextureFilm( texture, SIZE, SIZE );
		}
	}
	
	public void reset( int index ) {
		frame( film.get( index ) );
		origin.set( width / 2, height / 2 );
		
		phase = Phase.FADE_IN;
		
		duration = FADE_IN_TIME;
		passed = 0;
	}
	
	@Override
	public void update() {
		super.update();
		
		x = target.sprite.center().x - SIZE / 2;
		y = target.sprite.y - SIZE;
		
		switch (phase) {
		case FADE_IN:
			alpha( passed / duration );
			scale.set( passed / duration );
			break;
		case STATIC:
			break;
		case FADE_OUT:
			alpha( 1 - passed / duration );
			break;
		}
		
		if ((passed += Game.elapsed) > duration) {
			switch (phase) {
			case FADE_IN:
				phase = Phase.STATIC;
				duration = STATIC_TIME;
				break;
			case STATIC:
				phase = Phase.FADE_OUT;
				duration = FADE_OUT_TIME;
				break;
			case FADE_OUT:
				kill();
				break;
			}
			
			passed = 0;
		}
	}
	
	@Override
	public void kill() {
		super.kill();
		all.remove( target );
	}
	
	public static void show( Char ch, int index, int color ) {
		
		if (!ch.sprite.visible) {
			return;
		}
		
		SpellSprite old = all.get( ch );
		if (old != null) {
			old.kill();
		}

		SpellSprite sprite = GameScene.spellSprite();
		sprite.revive();
		sprite.reset( index );
		sprite.target = ch;
		sprite.hardlight( color );
		all.put(ch, sprite);
	}

	public static void show( Char ch, int index ) {

		show( ch, index, 0xFFFFFF );

	}
}
