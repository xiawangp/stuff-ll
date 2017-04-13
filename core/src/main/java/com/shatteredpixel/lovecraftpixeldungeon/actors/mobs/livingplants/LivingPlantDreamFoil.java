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

package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Foliage;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Dreamfoil;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingDreamfoilPlantSprite;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;

public class LivingPlantDreamFoil extends LivingPlant {

    {
        spriteClass = LivingDreamfoilPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Dreamfoil.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, Foliage.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (enemy instanceof Mob)
            Buff.affect(enemy, MagicalSleep.class);
        else if (enemy instanceof Hero){
            GLog.i( Messages.get(Dreamfoil.class, "refreshed") );
            Buff.detach( enemy, Poison.class );
            Buff.detach( enemy, Cripple.class );
            Buff.detach( enemy, Weakness.class );
            Buff.detach( enemy, Bleeding.class );
            Buff.detach( enemy, Drowsy.class );
            Buff.detach( enemy, Slow.class );
            Buff.detach( enemy, Vertigo.class);
        }
        return super.attackProc(enemy, damage);
    }
}
