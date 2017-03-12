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
