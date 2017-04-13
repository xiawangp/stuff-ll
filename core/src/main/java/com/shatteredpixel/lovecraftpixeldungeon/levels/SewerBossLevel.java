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
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.DoubleShoggoth;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Kek;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.TenguGuardSpartacus;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.TenguGuardVercingetorix;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.npcs.ImprisonedThieves;
import com.shatteredpixel.lovecraftpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.SpearTrap;
import com.shatteredpixel.lovecraftpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
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

	private Kek kek;
	private DoubleShoggoth shogg1;
	private DoubleShoggoth shogg2;
	private DoubleShoggoth shogg3;
	private TenguGuardVercingetorix vercingetorix;
	private TenguGuardSpartacus spartacus;
	private ImprisonedThieves imprisonedThieve1;
	private ImprisonedThieves imprisonedThieve2;
	private ImprisonedThieves imprisonedThieve3;
	private ImprisonedThieves imprisonedThieve4;
	private ImprisonedThieves imprisonedThieve5;
	private ImprisonedThieves imprisonedThieve6;
	
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
	
	@Override
	public Group addVisuals() {
		super.addVisuals();
		SewerLevel.addSewerVisuals(this, visuals);
		return visuals;
	}

	@Override
	public void press( int cell, Char ch ) {

		super.press(cell, ch);

		if (ch == Dungeon.hero){
			if (((Room)new Room().set(0, 11, 6, 23)).inside(cellToPoint(cell))){
				if(shogg1.isAlive() || shogg2.isAlive() || shogg3.isAlive()){
					seal();
					set(2+11*32, Terrain.WALL);
					set(4+11*32, Terrain.WALL);
					set(5+23*32, Terrain.WALL);
					set(1+23*32, Terrain.WALL);
					GameScene.updateMap(5+23*32);
					GameScene.updateMap(4+11*32);
					GameScene.updateMap(2+11*32);
					GameScene.updateMap(1+23*32);
				} else {
					unseal();
					set(2+11*32, Terrain.DOOR);
					set(4+11*32, Terrain.DOOR);
					set(5+23*32, Terrain.DOOR);
					set(1+23*32, Terrain.BARRICADE);
					GameScene.updateMap(5+23*32);
					GameScene.updateMap(4+11*32);
					GameScene.updateMap(2+11*32);
					GameScene.updateMap(1+23*32);
				}
			} else if(((Room)new Room().set(0, 25, 6, 31)).inside(cellToPoint(cell))){
				if(vercingetorix.isAlive() ){
					seal();
					set(1+25*32, Terrain.WALL);
					set(5+25*32, Terrain.WALL);
					set(6+26*32, Terrain.WALL);
					GameScene.updateMap(1+25*32);
					GameScene.updateMap(5+25*32);
					GameScene.updateMap(6+26*32);
				} else {
					unseal();
					set(1+25*32, Terrain.BARRICADE);
					set(5+25*32, Terrain.DOOR);
					set(6+26*32, Terrain.DOOR);
					GameScene.updateMap(1+25*32);
					GameScene.updateMap(5+25*32);
					GameScene.updateMap(6+26*32);
				}
			}
		}
	}

	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {
		kek = new Kek();
		kek.properties().add(Char.Property.IMMOVABLE);
		kek.pos = 8+29*32;
		mobs.add(kek);

		shogg1 = new DoubleShoggoth();
		shogg1.pos = 2+15*32;
		mobs.add(shogg1);

		shogg2 = new DoubleShoggoth();
		shogg2.pos = 4+18*32;
		mobs.add(shogg2);

		shogg3 = new DoubleShoggoth();
		shogg3.pos = 2+21*32;
		mobs.add(shogg3);

		vercingetorix = new TenguGuardVercingetorix();
		vercingetorix.pos = 3+28*32;
		mobs.add(vercingetorix);

		spartacus = new TenguGuardSpartacus();
		spartacus.pos = 8+25*32;
		mobs.add(spartacus);

		imprisonedThieve1 = new ImprisonedThieves();
		imprisonedThieve1.pos = 11+20*32;
		mobs.add(imprisonedThieve1);

		imprisonedThieve2 = new ImprisonedThieves();
		imprisonedThieve2.pos = 11+22*32;
		mobs.add(imprisonedThieve2);

		imprisonedThieve3 = new ImprisonedThieves();
		imprisonedThieve3.pos = 11+24*32;
		mobs.add(imprisonedThieve3);

		imprisonedThieve4 = new ImprisonedThieves();
		imprisonedThieve4.pos = 17+20*32;
		mobs.add(imprisonedThieve4);

		imprisonedThieve5 = new ImprisonedThieves();
		imprisonedThieve5.pos = 17+22*32;
		mobs.add(imprisonedThieve5);

		imprisonedThieve6 = new ImprisonedThieves();
		imprisonedThieve6.pos = 17+24*32;
		mobs.add(imprisonedThieve6);
	}
	
	@Override
	protected void createItems() {
		for(int i = 20; i > 0; i--){
			drop(new IronKey(Dungeon.depth), entrance);
		}
	}

	private static final String STAIRS	= "stairs";
	private static final String KEK		= "kek";
	private static final String SHOGG1	= "shogg1";
	private static final String SHOGG2	= "shogg2";
	private static final String SHOGG3	= "shogg3";
	private static final String VERC	= "verc";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( STAIRS, stairs );
		bundle.put( KEK, kek );
		bundle.put( SHOGG1, shogg1 );
		bundle.put( SHOGG2, shogg2 );
		bundle.put( SHOGG3, shogg3 );
		bundle.put( VERC, vercingetorix );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		stairs = bundle.getInt( STAIRS );
		kek = (Kek)bundle.get(KEK);
		shogg1 = (DoubleShoggoth) bundle.get(SHOGG1);
		shogg2 = (DoubleShoggoth)bundle.get(SHOGG2);
		shogg3 = (DoubleShoggoth)bundle.get(SHOGG3);
		vercingetorix = (TenguGuardVercingetorix)bundle.get(VERC);
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
	private static final int X = Terrain.LOCKED_EXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;
	private static final int m = Terrain.EMPTY_DECO;

	private static final int[] MAP_START =
			{       W, W, M, W, M, M, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
					W, s, w, s, w, s, W, e, m, e, W, e, e, e, e, e, e, e, W, e, D, e, e, e, W, e, e, e, W, W, X, W,
					W, e, w, w, w, m, W, m, s, e, L, e, b, e, b, e, b, e, W, m, W, m, P, e, W, m, P, e, W, e, e, W,
					W, e, w, E, w, e, W, m, e, e, W, e, b, e, b, e, b, e, W, m, W, e, m, e, L, m, e, e, W, e, W, W,
					W, w, w, w, w, w, W, W, L, W, W, e, b, e, b, e, b, e, W, e, W, W, W, W, W, W, L, W, W, e, e, W,
					W, e, w, e, w, e, W, e, e, e, W, e, b, e, b, e, b, e, W, m, W, e, e, e, W, m, e, e, W, W, e, W,
					W, m, e, e, e, e, W, m, s, e, W, e, b, e, b, e, b, e, W, m, W, e, P, m, L, m, P, e, W, e, e, W,
					W, e, m, m, e, e, W, e, m, m, W, e, b, e, b, e, b, e, W, e, W, e, m, e, W, e, e, e, W, e, W, W,
					W, B, B, e, B, B, W, W, L, W, W, e, b, e, b, e, b, e, W, e, W, W, L, W, W, W, W, W, W, e, e, W,
					W, e, e, e, e, e, W, e, e, e, W, e, e, e, e, e, e, e, W, e, W, e, m, e, W, m, m, e, W, W, e, W,
					W, s, e, S, m, s, W, e, s, m, W, e, s, e, s, e, s, e, W, m, W, e, P, m, L, m, P, e, W, e, e, W,
					W, W, D, W, D, W, W, e, m, e, W, D, W, D, W, D, W, D, W, m, W, e, e, e, W, e, e, e, W, e, W, W,
					W, e, e, e, e, e, W, W, L, W, W, m, W, e, W, m, W, e, W, m, W, W, W, W, W, W, L, W, W, e, e, W,
					W, e, m, m, e, e, W, m, e, e, W, e, W, e, W, m, W, m, W, e, W, e, e, e, W, e, m, e, W, W, e, W,
					W, e, B, B, B, B, W, m, s, e, W, m, m, e, W, e, m, e, W, m, W, m, P, e, L, e, P, e, W, e, e, W,
					W, e, e, m, e, e, W, e, e, e, W, W, m, W, W, W, e, W, W, e, W, e, m, m, W, m, m, e, W, e, W, W,
					W, e, e, e, m, e, W, W, L, W, W, W, D, W, W, W, D, W, W, e, W, M, L, M, W, M, M, W, M, L, M, W,
					W, B, B, B, B, e, W, m, e, e, W, W, e, m, m, e, m, m, e, e, W, w, m, w, s, w, w, s, w, e, w, W,
					W, e, e, e, m, e, W, e, s, m, W, W, M, W, H, W, M, W, W, H, W, w, m, w, e, w, w, e, w, e, w, W,
					W, e, m, m, e, e, W, e, e, e, W, e, w, m, e, e, w, e, W, m, W, w, w, w, w, w, w, w, w, w, w, W,
					W, e, B, B, B, B, W, W, L, W, W, e, e, m, e, e, e, e, W, e, W, w, w, w, w, w, w, w, w, w, w, W,
					W, e, e, e, m, e, W, e, e, e, W, m, m, e, e, e, e, e, W, e, W, C, C, A, A, C, C, A, A, C, C, W,
					W, e, m, m, e, e, W, e, s, m, W, m, m, e, e, e, m, e, W, m, W, C, C, A, A, C, C, A, A, C, C, W,
					W, B, M, W, W, D, W, e, m, e, W, e, e, e, m, m, e, e, W, m, W, e, m, e, m, m, e, m, m, e, e, W,
					W, e, H, e, H, e, W, W, L, W, W, e, e, e, e, m, e, e, W, e, W, e, e, m, e, e, e, e, m, e, e, W,
					W, B, W, M, W, D, W, e, e, e, W, e, e, e, e, e, e, e, W, m, W, e, e, C, C, C, C, C, C, e, e, W,
					W, e, e, w, m, e, L, m, s, e, W, W, W, W, m, W, W, W, W, m, W, e, e, C, C, C, C, C, C, m, e, W,
					W, e, w, w, m, e, W, e, m, e, W, T, T, T, m, T, T, T, W, m, W, e, e, c, c, e, e, c, c, m, m, W,
					W, e, w, e, e, m, W, W, W, W, W, s, e, s, c, s, m, s, W, e, W, m, m, C, C, e, m, C, C, e, m, W,
					W, w, w, m, e, e, W, s, P, s, W, C, C, C, c, C, C, C, W, e, W, w, w, C, C, C, C, C, C, w, w, W,
					W, w, m, m, e, e, W, e, e, e, e, C, C, C, c, C, C, C, e, m, W, w, w, C, C, C, C, C, C, w, w, W,
					W, W, W, W, W, W, W, W, W, W, W, C, C, C, C, C, C, C, W, W, W, C, C, C, C, C, C, C, C, C, C, W};
}
