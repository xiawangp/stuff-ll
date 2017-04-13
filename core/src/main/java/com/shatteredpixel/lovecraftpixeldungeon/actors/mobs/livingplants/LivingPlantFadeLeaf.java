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
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.TeleportGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingFadeleafPlantSprite;
import com.watabou.utils.Random;

public class LivingPlantFadeLeaf extends LivingPlant {

    {
        spriteClass = LivingFadeleafPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Fadeleaf.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, TeleportGas.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if(Random.Int(2) == 2){
            teleport(enemy);
        }
        return super.attackProc(enemy, damage);
    }

    private static void teleport(Char enemy){
        Char ch = enemy;

        if (ch instanceof Hero) {

            ScrollOfTeleportation.teleportHero( (Hero)ch );
            ((Hero)ch).curAction = null;

        } else if (ch instanceof Mob&& !ch.properties().contains(Char.Property.IMMOVABLE)) {

            int count = 10;
            int newPos;
            do {
                newPos = Dungeon.level.randomRespawnCell();
                if (count-- <= 0) {
                    break;
                }
            } while (newPos == -1);

            if (newPos != -1 && !Dungeon.bossLevel()) {

                ch.pos = newPos;
                ch.sprite.place( ch.pos );
                ch.sprite.visible = Dungeon.visible[ch.pos];

            }

        }

        if (Dungeon.visible[enemy.pos]) {
            CellEmitter.get( enemy.pos ).start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
        }
    }
}
