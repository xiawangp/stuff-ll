package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Storm;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Stormvine;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingStormvinePlantSprite;
import com.watabou.utils.Random;

public class LivingPlantStormVine extends LivingPlant {

    {
        spriteClass = LivingStormvinePlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Stormvine.Seed(), pos);
        GameScene.add(Blob.seed(pos, 100, Storm.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if(Random.Int(10) == 5){
            Buff.affect(enemy, Paralysis.class);
        }
        return super.attackProc(enemy, damage);
    }
}
