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
package com.shatteredpixel.lovecraftpixeldungeon.levels;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Kek;
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
		width = 20;
		height = 20;
	}
	
	private int stairs = 0;

	private Kek kek;
	
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

		entrance = 1+18*20;
		exit = 8+0*20;

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
	
	@Override
	public Group addVisuals() {
		super.addVisuals();
		SewerLevel.addSewerVisuals(this, visuals);
		return visuals;
	}

	@Override
	public void press( int cell, Char ch ) {
		super.press(cell, ch);
	}

	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {
		if(!RegularLevel.pepespanwed){
			kek = new Kek();
			kek.properties().add(Char.Property.IMMOVABLE);
			kek.pos = 17+16*20;
			mobs.add(kek);
			RegularLevel.pepespanwed = true;
		}
	}
	
	@Override
	protected void createItems() {
		for(int i = 20; i > 0; i--){
			drop(new IronKey(Dungeon.depth), entrance);
		}
	}

	private static final String STAIRS	= "stairs";
	private static final String KEK		= "kek";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( STAIRS, stairs );
		bundle.put( KEK, kek );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		stairs = bundle.getInt( STAIRS );
		kek = (Kek)bundle.get(KEK);
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
	private static final int e = Terrain.EMPTY;
	private static final int w = Terrain.WATER;
	private static final int C = Terrain.CHASM;
	private static final int c = Terrain.EMPTY_SP;

	private static final int E = Terrain.ENTRANCE;
	private static final int X = Terrain.LOCKED_EXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int m = Terrain.EMPTY_DECO;

	private static final int[] MAP_START =
			{		//  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19
				/*0*/	W, M, W, M, W, M, W, M, X, W, W, W, W, M, W, W, W, M, W, W,
				/*1*/	W, w, m, w, e, w, e, w, e, C, C, C, w, w, e, C, e, w, e, W,
				/*2*/	W, w, e, w, m, w, m, w, m, C, C, C, e, w, e, c, w, w, e, W,
				/*3*/	W, w, m, w, m, w, e, w, w, C, C, C, e, w, w, C, w, w, e, W,
				/*4*/	W, w, e, w, w, w, w, w, w, c, c, c, C, c, C, C, C, c, C, W,
				/*5*/	W, w, w, w, w, e, w, w, w, C, C, C, C, c, C, C, C, c, C, W,
				/*6*/	W, C, C, C, C, C, C, c, C, C, C, C, C, c, C, C, C, c, C, W,
				/*7*/	W, C, C, C, C, C, C, c, C, C, C, C, C, c, C, C, C, c, C, W,
				/*8*/	W, C, C, C, C, C, C, c, C, C, C, C, w, e, e, w, w, w, C, W,
				/*9*/	W, C, w, w, m, C, e, w, e, C, C, C, w, w, w, w, e, w, C, W,
				/*10*/	W, C, m, w, e, c, e, w, w, c, c, c, e, e, w, w, e, e, C, W,
				/*11*/	W, C, e, m, e, C, w, w, e, C, C, C, c, C, C, C, C, c, C, W,
				/*12*/	W, C, C, c, C, C, C, C, C, C, C, C, c, C, C, C, C, c, C, W,
				/*13*/	W, C, C, c, C, C, C, C, C, C, C, C, c, C, C, C, C, c, C, W,
				/*14*/	W, C, C, c, C, C, C, C, C, C, C, C, c, C, C, C, C, c, C, W,
				/*15*/	W, C, C, c, C, C, C, C, C, C, C, C, w, w, e, C, w, w, e, W,
				/*16*/	W, w, m, e, m, C, m, w, e, C, C, C, e, w, w, c, e, w, e, W,
				/*17*/	W, w, w, e, w, C, w, w, w, c, c, c, e, w, e, C, w, w, e, W,
				/*18*/	W, E, w, m, w, C, e, m, m, C, C, C, C, C, C, C, C, C, C, W,
				/*19*/	W, W, M, W, M, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			};
}
