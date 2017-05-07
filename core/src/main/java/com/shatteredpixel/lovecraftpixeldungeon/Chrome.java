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
package com.shatteredpixel.lovecraftpixeldungeon;

import com.watabou.noosa.NinePatch;

public class Chrome {

	public static int assetid;
	public static String assets;

	public enum  Type {
		TOAST,
		TOAST_TR,
		WINDOW,
		BUTTON,
		TAG,
		GEM,
		SCROLL,
		TAB_SET,
		TAB_SELECTED,
		TAB_UNSELECTED,
		SMOOTHWINDOW
	};
	
	public static NinePatch get( Type type ) {
		assetid = LovecraftPixelDungeon.frameColor();
		switch (assetid){
			case 0:
				assets = Assets.CHROME1;
				break;
			case 1:
				assets = Assets.CHROME2;
				break;
			case 2:
				assets = Assets.CHROME3;
				break;
			case 3:
				assets = Assets.CHROME4;
				break;
		}
		switch (type) {
		case WINDOW:
			return new NinePatch( assets, 0, 0, 20, 20, 6 );
		case TOAST:
			return new NinePatch( assets, 22, 0, 18, 18, 5 );
		case TOAST_TR:
			return new NinePatch( assets, 40, 0, 18, 18, 5 );
		case BUTTON:
			return new NinePatch( assets, 58, 0, 6, 6, 2 );
		case TAG:
			return new NinePatch( assets, 22, 18, 16, 14, 3 );
		case GEM:
			return new NinePatch( assets, 0, 32, 32, 32, 13 );
		case SCROLL:
			return new NinePatch( assets, 32, 32, 32, 32, 5, 11, 5, 11 );
		case TAB_SET:
			return new NinePatch( assets, 64, 0, 20, 20, 6 );
		case TAB_SELECTED:
			return new NinePatch( assets, 65, 22, 8, 13, 3, 7, 3, 5 );
		case TAB_UNSELECTED:
			return new NinePatch( assets, 75, 22, 8, 13, 3, 7, 3, 5 );
			case SMOOTHWINDOW:
				return new NinePatch( assets, 88, 25, 19, 19, 5 );
		default:
			return null;
		}
	}

	public static NinePatch get( Type type, String setasset ) {
		switch (type) {
			case WINDOW:
				return new NinePatch( setasset, 0, 0, 20, 20, 6 );
			case TOAST:
				return new NinePatch( setasset, 22, 0, 18, 18, 5 );
			case TOAST_TR:
				return new NinePatch( setasset, 40, 0, 18, 18, 5 );
			case BUTTON:
				return new NinePatch( setasset, 58, 0, 6, 6, 2 );
			case TAG:
				return new NinePatch( setasset, 22, 18, 16, 14, 3 );
			case GEM:
				return new NinePatch( setasset, 0, 32, 32, 32, 13 );
			case SCROLL:
				return new NinePatch( setasset, 32, 32, 32, 32, 5, 11, 5, 11 );
			case TAB_SET:
				return new NinePatch( setasset, 64, 0, 20, 20, 6 );
			case TAB_SELECTED:
				return new NinePatch( setasset, 65, 22, 8, 13, 3, 7, 3, 5 );
			case TAB_UNSELECTED:
				return new NinePatch( setasset, 75, 22, 8, 13, 3, 7, 3, 5 );
			case SMOOTHWINDOW:
				return new NinePatch( setasset, 88, 25, 19, 19, 5 );
			default:
				return null;
		}
	}
}
