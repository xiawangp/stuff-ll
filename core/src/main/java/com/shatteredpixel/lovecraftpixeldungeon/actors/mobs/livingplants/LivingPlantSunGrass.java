package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Regrowth;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Sungrass;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingSungrassPlantSprite;

public class LivingPlantSunGrass extends LivingPlant {

    {
        spriteClass = LivingSungrassPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Sungrass.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, Regrowth.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Slow.class);
        return super.attackProc(enemy, damage);
    }
}
