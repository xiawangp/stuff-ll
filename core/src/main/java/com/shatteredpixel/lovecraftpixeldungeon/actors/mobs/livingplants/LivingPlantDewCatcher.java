package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Sunlight;
import com.shatteredpixel.lovecraftpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingDewCatcherPlantSprite;

public class LivingPlantDewCatcher extends LivingPlant {

    {
        spriteClass = LivingDewCatcherPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new WandOfRegrowth.Dewcatcher.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, Sunlight.class));
    }
}
