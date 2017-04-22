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
package com.shatteredpixel.lovecraftpixeldungeon.items.weapon.melee;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Terrain;
import com.shatteredpixel.lovecraftpixeldungeon.levels.features.HighGrass;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Scythe extends MeleeWeapon {

	private static final String AC_HARVEST = "HARVEST";

	{
		image = ItemSpriteSheet.SCYTHE;

		tier = 3;
		weight = 3;
		RCH = 3;
		DLY = 0.2f;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_HARVEST );
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_HARVEST)) {

			curUser = hero;

			for(int i : PathFinder.NEIGHBOURS9){
				int posi = curUser.pos + i;
				Dungeon.level.getPlant(posi).activate();
				if(Dungeon.level.map[posi] == Terrain.HIGH_GRASS){
					HighGrass.trample(Dungeon.level, posi, curUser );
				}
			}

		}
	}

	@Override
	public int max(int lvl) {
		return  4*(tier+1) +    //20 base, down from 25
				lvl*(tier+1);   //scaling unchanged
	}

	@Override
	public int damageRoll(Hero hero) {
		Char enemy = hero.enemy();
		if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
			//deals avg damage to max on surprise, instead of min to max.
			int damage = imbue.damageFactor(Random.NormalIntRange((min() + max()) / 2, max()));
			int exStr = hero.STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
			return damage;
		} else
			return super.damageRoll(hero);
	}

}