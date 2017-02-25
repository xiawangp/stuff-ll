/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.shatteredpixel.lovecraftpixeldungeon.levels.features;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.lovecraftpixeldungeon.levels.DeadEndLevel;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Languages;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.shatteredpixel.lovecraftpixeldungeon.windows.WndMessage;
import com.watabou.noosa.audio.Sample;

public class Sign {

	private static final String[] teaser_texts = new String[]{
		"G<@8R9BER4RA8JRC8EFC86G<I8 _0_",
		"G;8RJ4??FR4E8RF;<9G<A: _5_",
		"G;8R7HA:8BARJ<??R58RF;4GG8E87 _0_"
	};
	
	public static void read( int pos ) {
		
		if (Dungeon.level instanceof DeadEndLevel) {
			
			GameScene.show( new WndMessage( Messages.get(Sign.class, "dead_end") ) );
			
		} else {

			if (Dungeon.depth <= 21) {
				GameScene.show( new WndMessage( Messages.get(Sign.class, "tip_"+Dungeon.depth) ) );
			} else {

				//if we are at depths 22-24 and in english
				if (Dungeon.depth-21 <= 3 && Messages.lang() == Languages.ENGLISH){
					GameScene.show( new WndMessage(teaser_texts[Dungeon.depth-22]));
				}

				Dungeon.level.destroy( pos );
				GameScene.updateMap( pos );
				GameScene.discoverTile( pos, Terrain.SIGN );

				GLog.w( Messages.get(Sign.class, "burn") );

				CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
				Sample.INSTANCE.play( Assets.SND_BURNING );
			}

		}
	}
}
