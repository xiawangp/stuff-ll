package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Earthroot;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingFirebloomPlantSprite;

public class LivingPlantEarthRoot extends LivingPlant {

    {
        spriteClass = LivingFirebloomPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Earthroot.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, ParalyticGas.class));
    }
}
