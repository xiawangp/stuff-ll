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
package com.shatteredpixel.lovecraftpixeldungeon.actors.buffs;

import android.opengl.GLES20;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Halo;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Game;
import com.watabou.utils.PointF;

import javax.microedition.khronos.opengles.GL10;

public class MagicShield extends FlavourBuff {

	public static final float DURATION	= 40f;

	private Shield shield;

	{
		type = buffType.POSITIVE;
	}

	@Override
	public boolean attachTo( Char target ) {
		if (super.attachTo( target )) {
			target.sprite.parent.add(shield = new Shield());
			if(target instanceof Hero){
				target.magicshield = true;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		super.detach();
		shield.putOut();
		target.SHLD = 0;
		target.magicshield = false;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.SHIELD;
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}

	public class Shield extends Halo {

		private float phase;

		public Shield() {

			super( 9, 0xBBAACC, 1f );

			am = -0.33f;
			aa = +0.33f;

			phase = 1;
		}

		@Override
		public void update() {
			super.update();

			if (phase < 1) {
				if ((phase -= Game.elapsed) <= 0) {
					killAndErase();
				} else {
					scale.set( (2 - phase) * radius / RADIUS );
					am = phase * (-1);
					aa = phase * (+1);
				}
			}

			if (visible = target.sprite.visible) {
				PointF p = target.sprite.center();
				point(p.x, p.y );
			}
		}

		@Override
		public void draw() {
			GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
			super.draw();
			GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
		}

		public void putOut() {
			phase = 0.999f;
		}
	}
}
