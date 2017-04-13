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
package com.shatteredpixel.lovecraftpixeldungeon.windows;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTerrainTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.ui.CustomTileVisual;
import com.shatteredpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.lovecraftpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

public class WndInfoCell extends Window {
	
	private static final float GAP	= 2;
	
	private static final int WIDTH = 120;
	
	public WndInfoCell( int cell ) {
		
		super();
		
		int tile = Dungeon.level.map[cell];
		if (Level.water[cell]) {
			tile = Terrain.WATER;
		} else if (Level.pit[cell]) {
			tile = Terrain.CHASM;
		}

		CustomTileVisual vis = null;
		int x = cell % Dungeon.level.width();
		int y = cell / Dungeon.level.width();
		for (CustomTileVisual i : Dungeon.level.customTiles){
			if ((x >= i.tileX && x < i.tileX+i.tileW) &&
					(y >= i.tileY && y < i.tileY+i.tileH)){
				if (i.desc() != null) {
					vis = i;
					break;
				}
			}
		}


		String desc = "";

		IconTitle titlebar = new IconTitle();
		if (vis != null){
			titlebar.icon(new Image(vis));
			titlebar.label(vis.name);
			desc += vis.desc();
		} else {

			if (tile == Terrain.WATER) {
				Image water = new Image(Dungeon.level.waterTex());
				water.frame(0, 0, DungeonTilemap.SIZE, DungeonTilemap.SIZE);
				titlebar.icon(water);
			} else {
				titlebar.icon(DungeonTerrainTilemap.tile( cell, tile ));
			}
			titlebar.label(Dungeon.level.tileName(tile));
			desc += Dungeon.level.tileDesc(tile);

		}
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline info = PixelScene.renderMultiline(6);
		add(info);

		for (Blob blob:Dungeon.level.blobs.values()) {
			if (blob.volume > 0 && blob.cur[cell] > 0 && blob.tileDesc() != null) {
				if (desc.length() > 0) {
					desc += "\n\n";
				}
				desc += blob.tileDesc();
			}
		}
		
		info.text( desc );
		info.maxWidth(WIDTH);
		info.setPos(titlebar.left(), titlebar.bottom() + GAP);
		
		resize( WIDTH, (int)(info.top() + info.height()) );
	}
}
