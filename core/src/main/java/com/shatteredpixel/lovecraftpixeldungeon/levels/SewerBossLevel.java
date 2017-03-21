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
package com.shatteredpixel.lovecraftpixeldungeon.levels;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.SpearTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;

public class SewerBossLevel extends Level {

	{
		color1 = 0x48763c;
		color2 = 0x59994a;
		width = 32;
		height = 32;
	}
	
	private int stairs = 0;
	
	@Override
	public String tilesTex() {
		return Assets.TILES_SEWERS;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_SEWERS;
	}

	@Override
	protected boolean build() {

		map = MAP_START.clone();
		decorate();

		buildFlagMaps();
		cleanWalls();

		resetTraps();

		entrance = 3+3*32;
		exit = 30+1*32;

		return true;
	}

	private void resetTraps(){
		traps.clear();

		for (int i = 0; i < length(); i++){
			if (map[i] == Terrain.INACTIVE_TRAP) {
				Trap t = new SpearTrap().reveal();
				t.active = false;
				setTrap(t, i);
				map[i] = Terrain.INACTIVE_TRAP;
			}
			if (map[i] == Terrain.SECRET_TRAP) {
				Trap t = new SpearTrap().reveal();
				t.active = false;
				setTrap(t, i);
				map[i] = Terrain.SECRET_TRAP;
			}
		}
	}
		
	protected boolean[] water() {
		return Patch.generate( this, 0.5f, 5 );
	}
	
	protected boolean[] grass() {
		return Patch.generate( this, 0.40f, 4 );
	}
	
	@Override
	public Group addVisuals() {
		super.addVisuals();
		SewerLevel.addSewerVisuals(this, visuals);
		return visuals;
	}

	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {
		//do nothing
	}
	
	@Override
	protected void createItems() {
		for(int i = 20; i > 0; i--){
			drop(new IronKey(Dungeon.depth), entrance);
		}
	}
	
	private static final String STAIRS	= "stairs";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( STAIRS, stairs );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		stairs = bundle.getInt( STAIRS );
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(SewerLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(SewerLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(SewerLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	private static final int W = Terrain.WALL;
	private static final int D = Terrain.DOOR;
	private static final int H = Terrain.SECRET_DOOR;
	private static final int L = Terrain.LOCKED_DOOR;
	private static final int e = Terrain.EMPTY;
	private static final int S = Terrain.SIGN;
	private static final int B = Terrain.BARRICADE;
	private static final int b = Terrain.BOOKSHELF;
	private static final int s = Terrain.STATUE;
	private static final int w = Terrain.WATER;
	private static final int C = Terrain.CHASM;
	private static final int c = Terrain.EMPTY_SP;

	private static final int T = Terrain.INACTIVE_TRAP;
	private static final int A = Terrain.SECRET_TRAP;

	private static final int E = Terrain.ENTRANCE;
	private static final int X = Terrain.EXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;

	private static final int[] MAP_START =
			{       W, W, M, W, M, M, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
					W, s, w, s, w, s, W, e, e, e, W, e, e, e, e, e, e, e, W, e, D, e, e, e, W, e, e, e, W, W, X, W,
					W, e, w, w, w, e, W, e, s, e, L, e, b, e, b, e, b, e, W, e, W, e, P, e, W, e, P, e, W, e, e, W,
					W, e, w, E, w, e, W, e, e, e, W, e, b, e, b, e, b, e, W, e, W, e, e, e, L, e, e, e, W, e, W, W,
					W, w, w, w, w, w, W, W, L, W, W, e, b, e, b, e, b, e, W, e, W, W, W, W, W, W, L, W, W, e, e, W,
					W, e, w, e, w, e, W, e, e, e, W, e, b, e, b, e, b, e, W, e, W, e, e, e, W, e, e, e, W, W, e, W,
					W, e, e, e, e, e, W, e, s, e, W, e, b, e, b, e, b, e, W, e, W, e, P, e, L, e, P, e, W, e, e, W,
					W, e, e, e, e, e, W, e, e, e, W, e, b, e, b, e, b, e, W, e, W, e, e, e, W, e, e, e, W, e, W, W,
					W, B, B, e, B, B, W, W, L, W, W, e, b, e, b, e, b, e, W, e, W, W, L, W, W, W, W, W, W, e, e, W,
					W, e, e, e, e, e, W, e, e, e, W, e, e, e, e, e, e, e, W, e, W, e, e, e, W, e, e, e, W, W, e, W,
					W, s, e, S, e, s, W, e, s, e, W, e, s, e, s, e, s, e, W, e, W, e, P, e, L, e, P, e, W, e, e, W,
					W, W, D, W, D, W, W, e, e, e, W, D, W, D, W, D, W, D, W, e, W, e, e, e, W, e, e, e, W, e, W, W,
					W, e, e, e, e, e, W, W, L, W, W, e, W, e, W, e, W, e, W, e, W, W, W, W, W, W, L, W, W, e, e, W,
					W, e, e, e, e, e, W, e, e, e, W, e, W, e, W, e, W, e, W, e, W, e, e, e, W, e, e, e, W, W, e, W,
					W, e, B, B, B, B, W, e, s, e, W, e, e, e, W, e, e, e, W, e, W, e, P, e, L, e, P, e, W, e, e, W,
					W, e, e, e, e, e, W, e, e, e, W, W, e, W, W, W, e, W, W, e, W, e, e, e, W, e, e, e, W, e, W, W,
					W, e, e, e, e, e, W, W, L, W, W, W, D, W, W, W, D, W, W, e, W, M, L, M, W, M, M, W, M, L, M, W,
					W, B, B, B, B, e, W, e, e, e, W, W, e, e, e, e, e, e, e, e, W, w, e, w, s, w, w, s, w, e, w, W,
					W, e, e, e, e, e, W, e, s, e, W, W, M, W, H, W, M, W, W, H, W, w, e, w, e, w, w, e, w, e, w, W,
					W, e, e, e, e, e, W, e, e, e, W, e, w, e, e, e, w, e, W, e, W, w, w, w, w, w, w, w, w, w, w, W,
					W, e, B, B, B, B, W, W, L, W, W, e, e, e, e, e, e, e, W, e, W, w, w, w, w, w, w, w, w, w, w, W,
					W, e, e, e, e, e, W, e, e, e, W, e, e, e, e, e, e, e, W, e, W, C, C, A, A, C, C, A, A, C, C, W,
					W, e, e, e, e, e, W, e, s, e, W, e, e, e, e, e, e, e, W, e, W, C, C, A, A, C, C, A, A, C, C, W,
					W, B, M, W, W, D, W, e, e, e, W, e, e, e, e, e, e, e, W, e, W, e, e, e, e, e, e, e, e, e, e, W,
					W, e, H, e, H, e, W, W, L, W, W, e, e, e, e, e, e, e, W, e, W, e, e, e, e, e, e, e, e, e, e, W,
					W, B, W, M, W, D, W, e, e, e, W, e, e, e, e, e, e, e, W, e, W, e, e, C, C, C, C, C, C, e, e, W,
					W, e, e, w, e, e, L, e, s, e, W, W, W, W, e, W, W, W, W, e, W, e, e, C, C, C, C, C, C, e, e, W,
					W, e, w, w, e, e, W, e, e, e, W, T, T, T, e, T, T, T, W, e, W, e, e, c, c, e, e, c, c, e, e, W,
					W, e, w, e, e, e, W, W, W, W, W, s, e, s, c, s, e, s, W, e, W, e, e, C, C, e, e, C, C, e, e, W,
					W, w, w, e, e, e, W, e, P, e, W, C, C, C, c, C, C, C, W, e, W, w, w, C, C, C, C, C, C, w, w, W,
					W, w, e, e, e, e, W, e, e, e, e, C, C, C, c, C, C, C, e, e, W, w, w, C, C, C, C, C, C, w, w, W,
					W, W, W, W, W, W, W, W, W, W, W, C, C, C, C, C, C, C, W, W, W, C, C, C, C, C, C, C, C, C, C, W};
}
