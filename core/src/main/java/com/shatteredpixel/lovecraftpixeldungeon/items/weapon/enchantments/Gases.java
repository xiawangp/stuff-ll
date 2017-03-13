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
package com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ConfusionGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.IceWind;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Regrowth;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Smoke;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.StenchGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Storm;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Sunlight;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.TeleportGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.VenomGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.WaterWave;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.GasesImmunity;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite.Glowing;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.Random;

public class Gases extends Weapon.Enchantment {

	private static Glowing GREEN = new Glowing( 0x25F145 );
	
	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		int level = Math.max( 0, weapon.level() );
		
		if (Random.Int( level + 3 ) >= 2) {
			
			if (Random.Int( 2 ) == 0) {
				Buff.prolong(attacker, GasesImmunity.class, 30f);
				switch(Randomer.randomInteger(13)){
					case 0:
						GameScene.add( Blob.seed( defender.pos, 1000, ParalyticGas.class ) );
						break;
					case 1:
						GameScene.add( Blob.seed( defender.pos, 1000, WaterWave.class ) );
						break;
					case 2:
						GameScene.add( Blob.seed( defender.pos, 1000, StenchGas.class ) );
						break;
					case 3:
						GameScene.add( Blob.seed( defender.pos, 1000, ConfusionGas.class ) );
						break;
					case 4:
						GameScene.add( Blob.seed( defender.pos, 1000, VenomGas.class ) );
						break;
					case 5:
						GameScene.add( Blob.seed( defender.pos, 1000, ToxicGas.class ) );
						break;
					case 6:
						GameScene.add( Blob.seed( defender.pos, 1000, Regrowth.class ) );
						break;
					case 7:
						GameScene.add( Blob.seed( defender.pos, 1000, Fire.class ) );
						break;
					case 8:
						GameScene.add( Blob.seed( defender.pos, 1000, IceWind.class ) );
						break;
					case 9:
						GameScene.add( Blob.seed( defender.pos, 1000, TeleportGas.class ) );
						break;
					case 10:
						GameScene.add( Blob.seed( defender.pos, 1000, Storm.class ) );
						break;
					case 11:
						GameScene.add( Blob.seed( defender.pos, 1000, Sunlight.class ) );
						break;
					case 12:
						GameScene.add( Blob.seed( defender.pos, 1000, Smoke.class ) );
						break;
				}
			}
			defender.damage( Random.Int( 1, level + 2 ), this );

		}

		return damage;

	}
	
	@Override
	public Glowing glowing() {
		return GREEN;
	}
}
