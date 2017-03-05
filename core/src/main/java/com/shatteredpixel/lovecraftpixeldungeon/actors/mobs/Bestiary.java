/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
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
package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs;

import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.watabou.utils.Random;

public class Bestiary {

	public static Mob mob( int depth ) {
		@SuppressWarnings("unchecked")
		Class<? extends Mob> cl = (Class<? extends Mob>)mobClass( depth );
		try {
			return cl.newInstance();
		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}
	
	public static Mob mutable( int depth ) {
		@SuppressWarnings("unchecked")
		Class<? extends Mob> cl = (Class<? extends Mob>)mobClass( depth );
		
		if (Random.Int( 30 ) == 0) {
			if (cl == MiGo.class) {
				cl = MiGoQueen.MiGoLarva.class;
			} else if (cl == Thief.class) {
				cl = Bandit.class;
			}
		}
		
		try {
			return cl.newInstance();
		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}
	
	private static Class<?> mobClass( int depth ) {
		
		float[] chances;
		Class<?>[] classes;

		//TODO: BESTIARY
		switch (depth) {
		case 1:
			chances = new float[]{ 2.5f, 1.1f, 0.01f };
			classes = new Class<?>[]{ MiGo.class, YoungMiGo.class, MiGoNurse.class };
			break;
		case 2:
			chances = new float[]{ 2, 1, 1 };
			classes = new Class<?>[]{ MiGo.class, YoungMiGo.class, NormalShoggoth.class };
			break;
		case 3:
			chances = new float[]{ 2, 4, 1, 1 };
			classes = new Class<?>[]{  };
			break;
		case 4:
			chances = new float[]{ 1, 2, 3, 1,   0.01f, 0.01f };
			classes = new Class<?>[]{  };
			break;
			
		case 5:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ Goo.class };
			break;
			
		case 6:
			chances = new float[]{ 3, 1, 1,     0.2f };
			classes = new Class<?>[]{  };
			break;
		case 7:
			chances = new float[]{ 3, 1, 1, 1 };
			classes = new Class<?>[]{  };
			break;
		case 8:
			chances = new float[]{ 3, 2, 2, 1,   0.02f };
			classes = new Class<?>[]{  };
			break;
		case 9:
			chances = new float[]{ 3, 3, 2, 1,   0.02f, 0.01f };
			classes = new Class<?>[]{  };
			break;
			
		case 10:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ Tengu.class };
			break;
			
		case 11:
			chances = new float[]{ 1,   0.2f };
			classes = new Class<?>[]{  };
			break;
		case 12:
			chances = new float[]{ 1, 1,   0.2f };
			classes = new Class<?>[]{  };
			break;
		case 13:
			chances = new float[]{ 1, 3, 1, 1,   0.02f };
			classes = new Class<?>[]{  };
			break;
		case 14:
			chances = new float[]{ 1, 3, 1, 4,    0.02f, 0.01f };
			classes = new Class<?>[]{  };
			break;
			
		case 15:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ DM300.class };
			break;
			
		case 16:
			chances = new float[]{ 1, 1,   0.2f };
			classes = new Class<?>[]{  };
			break;
		case 17:
			chances = new float[]{ 1, 1, 1 };
			classes = new Class<?>[]{  };
			break;
		case 18:
			chances = new float[]{ 1, 2, 1, 1 };
			classes = new Class<?>[]{  };
			break;
		case 19:
			chances = new float[]{ 1, 2, 3, 1,    0.02f };
			classes = new Class<?>[]{  };
			break;
			
		case 20:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ King.class };
			break;
			
		case 22:
			chances = new float[]{ 1, 1 };
			classes = new Class<?>[]{  };
			break;
		case 23:
			chances = new float[]{ 1, 2, 1 };
			classes = new Class<?>[]{  };
			break;
		case 24:
			chances = new float[]{ 1, 2, 3 };
			classes = new Class<?>[]{  };
			break;
			
		case 25:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ Yog.class };
			break;
			
		default:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{  };
		}
		
		return classes[ Random.chances( chances )];
	}
}
