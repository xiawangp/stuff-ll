package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ConfusionGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingSorrowmossPlantSprite;

public class LivingPlantSorrowMoss extends LivingPlant {

    {
        spriteClass = LivingSorrowmossPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Sorrowmoss.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, ConfusionGas.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Vertigo.class);
        Buff.affect(enemy, Sleep.class);
        return super.attackProc(enemy, damage);
    }
}
