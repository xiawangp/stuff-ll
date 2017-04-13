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

package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.MoonFury;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.MoonPowers;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BlueWhiteShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_BLUEWHITE;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if(action.equals(AC_EAT)){

            int time = Random.Int(4, 8);

            furycry(hero, time);

            hero.decreaseMentalHealth(time*2);

            GLog.h(Messages.get(this, "moonfury"));
        }
    }

    private void furycry(Hero hero, int time){
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        ArrayList<MoonPowers.Arc> arcs = new ArrayList<>();
        for (int i : PathFinder.NEIGHBOURS8){
            if (Level.passable[hero.pos+i]){
                candidates.add(hero.pos+i);
            }
        }
        while (!candidates.isEmpty()){
            Integer c = Random.element(candidates);
            arcs.add(new MoonPowers.Arc(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(new Ballistica(hero.pos, c, Ballistica.MAGIC_BOLT).collisionPos)));
            candidates.remove(c);
        }
        Buff.append(hero, MoonFury.class, time);
        hero.sprite.parent.addToFront( new MoonPowers( arcs, null, 1f ) );

    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
