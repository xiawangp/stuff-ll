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
package com.shatteredpixel.lovecraftpixeldungeon.levels.features;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class Door {

	public static void enter( int pos ) {
		Level.set( pos, Terrain.OPEN_DOOR );
		GameScene.updateMap( pos );

		if (Dungeon.visible[pos]) {
			Dungeon.observe();
			Sample.INSTANCE.play( Assets.SND_OPEN );
		}
	}

	public static void leave( int pos ) {
		if (Dungeon.level.heaps.get( pos ) == null) {
			Level.set( pos, Terrain.DOOR );
			GameScene.updateMap( pos );
			if (Dungeon.visible[pos])
				Dungeon.observe();
		}
	}
}
