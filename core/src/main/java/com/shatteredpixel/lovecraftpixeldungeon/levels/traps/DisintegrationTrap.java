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
package com.shatteredpixel.lovecraftpixeldungeon.levels.traps;

import com.shatteredpixel.lovecraftpixeldungeon.Assets;
import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Beam;
import com.shatteredpixel.lovecraftpixeldungeon.items.Heap;
import com.shatteredpixel.lovecraftpixeldungeon.items.Item;
import com.shatteredpixel.lovecraftpixeldungeon.items.bags.Bag;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class DisintegrationTrap extends Trap {

	{
		color = VIOLET;
		shape = LARGE_DOT;
	}

	@Override
	public void activate() {

		if (Dungeon.visible[ pos ]) {
			LovecraftPixelDungeon.scene().add( new Beam.DeathRay( DungeonTilemap.tileCenterToWorld(pos-1),
					DungeonTilemap.tileCenterToWorld(pos+1)));
			LovecraftPixelDungeon.scene().add(new Beam.DeathRay(DungeonTilemap.tileCenterToWorld(pos - Dungeon.level.width()),
					DungeonTilemap.tileCenterToWorld(pos + Dungeon.level.width())));
			Sample.INSTANCE.play( Assets.SND_RAY );
		}

		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) heap.explode();

		Char ch = Actor.findChar(pos);
		if (ch != null){
			ch.damage( Math.max( ch.HT/5, Random.Int(ch.HP / 2, 2 * ch.HP / 3) ), this );
			if (ch == Dungeon.hero){
				Hero hero = (Hero)ch;
				if (!hero.isAlive()){
					Dungeon.fail( getClass() );
					GLog.n( Messages.get(this, "ondeath") );
				} else {
					Item item = hero.belongings.randomUnequipped();
					Bag bag = hero.belongings.backpack;
					//bags do not protect against this trap
					if (item instanceof Bag){
						bag = (Bag)item;
						item = Random.element(bag.items);
					}
					if (item == null || item.level() > 0 || item.unique) return;
					if (!item.stackable){
						item.detachAll(bag);
						GLog.w( Messages.get(this, "one", item.name()) );
					} else {
						int n = Random.NormalIntRange(1, (item.quantity()+1)/2);
						for(int i = 1; i <= n; i++)
							item.detach(bag);
						GLog.w( Messages.get(this, "some", item.name()) );
					}
				}
			}
		}

	}
}
