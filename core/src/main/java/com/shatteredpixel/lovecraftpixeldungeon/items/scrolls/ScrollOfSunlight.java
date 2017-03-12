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
package com.shatteredpixel.lovecraftpixeldungeon.items.scrolls;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Sunlight;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Flare;
import com.shatteredpixel.lovecraftpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class ScrollOfSunlight extends Scroll {

	{
		initials = 13;

		bones = true;
	}
	
	@Override
	protected void doRead() {

		new Flare( 32, 32 ).color( SpellSprite.COLOUR_HOLY, true ).show( curUser.sprite, 2f );
		SpellSprite.show(curUser, SpellSprite.SCROLL_SUNLIGHT, SpellSprite.COLOUR_HOLY);
		
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob, Vertigo.class, Vertigo.DURATION);
				Buff.affect(mob, Blindness.class, Vertigo.DURATION);
			}
		}

		GameScene.add( Blob.seed( curUser.pos, 1000, Sunlight.class ) );
		
		setKnown();

		curUser.spendAndNext( TIME_TO_READ );
		readAnimation();
	}
	
	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
