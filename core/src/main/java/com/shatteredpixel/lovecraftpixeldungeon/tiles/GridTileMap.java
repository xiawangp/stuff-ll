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

package com.shatteredpixel.lovecraftpixeldungeon.tiles;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;

public class GridTileMap extends DungeonTilemap {

	public GridTileMap() {
		super("visual_grid.png");

		map( Dungeon.level.map, Dungeon.level.width() );
	}

	private int gridSetting = -1;

	@Override
	public synchronized void updateMap() {
		gridSetting = LovecraftPixelDungeon.visualGrid();
		super.updateMap();
	}

	@Override
	protected int getTileVisual(int pos, int tile, boolean flat) {
		if (gridSetting == -1 || pos % 2 != (pos / mapWidth) % 2){
			return -1;
		} else if (DungeonTileSheet.floorTile(tile) || tile == Terrain.HIGH_GRASS) {
			return gridSetting;
		} else if (DungeonTileSheet.doorTile(tile)){
			if (DungeonTileSheet.wallStitcheable(map[pos - mapWidth])){
				return 12 + gridSetting;
			} else if ( tile == Terrain.OPEN_DOOR){
				return 8 + gridSetting;
			} else {
				return 4 + gridSetting;
			}
		} else {
			return -1;
		}
	}

	@Override
	protected boolean needsRender(int pos) {
		return data[pos] != -1;
	}

}
