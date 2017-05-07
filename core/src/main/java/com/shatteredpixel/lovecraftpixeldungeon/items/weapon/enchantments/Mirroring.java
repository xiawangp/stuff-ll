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
package com.shatteredpixel.lovecraftpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Kagebunshin;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Mirroring extends Weapon.Enchantment {

	private static Glowing DARKPURPLE = new Glowing( 0x6D376D );
	
	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		int NIMAGES	= weapon.visiblyUpgraded();

		ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = attacker.pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p]) && !Level.pit[p]) {
				respawnPoints.add( p );
			}
		}

		int nImages = NIMAGES;
		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index( respawnPoints );

			if(attacker == Dungeon.hero){
				MirrorImage mob = new MirrorImage();
				mob.duplicate( Dungeon.hero );
				GameScene.add( mob );
				ScrollOfTeleportation.appear( mob, respawnPoints.get( index ) );

				respawnPoints.remove( index );
				nImages--;
			} else {
				Kagebunshin kagebunshin = new Kagebunshin();
				GameScene.add( kagebunshin );
				ScrollOfTeleportation.appear( kagebunshin, respawnPoints.get( index ) );

				respawnPoints.remove( index );
				nImages--;
			}
		}

		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();

		return damage;

	}
	
	@Override
	public Glowing glowing() {
		return DARKPURPLE;
	}
}
