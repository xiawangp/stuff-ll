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

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Hypno;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PurpleShroom extends Mushrooms{

    public static final float AMOK_DURATION	= 30f;
    public static final float VERTIGO_DURATION	= 15f;

    {
        image = ItemSpriteSheet.SHROOM_PURPLE;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_EAT)) {

            crazytime(curUser);

            GLog.w(Messages.get(this, "crazy"));
        }
    }

    private void crazytime(Hero hero){
        ArrayList<Char> affected = new ArrayList<>();
        ArrayList<Hypno.Arc> arcs = new ArrayList<>();

        for(int i = hero.visibleEnemies(); i > 0; i--){
            affected.add(hero.visibleEnemy(i));
            arcs.add(new Hypno.Arc(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(new Ballistica(hero.pos, hero.visibleEnemy(i).pos, Ballistica.MAGIC_BOLT).collisionPos)));
        }

        for(int a = affected.size(); a > 0; a--) {
            Char target = affected.get(a - 1);
            Buff.affect( target, Amok.class, AMOK_DURATION );
            Buff.affect( target, Vertigo.class, AMOK_DURATION );
        }

        hero.sprite.parent.addToFront( new Hypno( arcs, null, Random.Float(0.5f, 1.5f) ) );

        Buff.affect( curUser, Vertigo.class, VERTIGO_DURATION );

        hero.decreaseMentalHealth(affected.size()*2);
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
