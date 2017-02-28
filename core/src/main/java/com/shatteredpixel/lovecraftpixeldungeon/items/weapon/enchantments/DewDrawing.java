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
package com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Splash;
import com.shatteredpixel.lovecraftpixeldungeon.items.Dewdrop;
import com.shatteredpixel.lovecraftpixeldungeon.items.GreenDewdrop;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite.Glowing;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.watabou.utils.Random;

public class DewDrawing extends Weapon.Enchantment {

	private static Glowing GREEN = new Glowing( 0x00FF00 );
	
	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
		// lvl 0 - 20%
		// lvl 1 - 33%
		// lvl 2 - 43%
		int level = Math.max( 0, weapon.level() );

		if (Random.Int( level + 5 ) >= 4) {
			switch (Randomer.randomInteger(6)){
				case 0:
				case 2:
				case 4:
					Dungeon.level.drop(new GreenDewdrop(), defender.pos);
					Buff.prolong( attacker, Vertigo.class, 4f );
					if(attacker == Dungeon.hero){
						Dungeon.hero.decreaseMentalHealth(1);
					}
					break;
				default:
					Dungeon.level.drop(new Dewdrop(), defender.pos);
					Buff.prolong( attacker, Vertigo.class, 2f );
					if(attacker == Dungeon.hero){
						Dungeon.hero.decreaseMentalHealth(1);
					}
					break;
			}
			Splash.at( defender.sprite.center(), 0x00FF00, 5);

		}

		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return GREEN;
	}

}
