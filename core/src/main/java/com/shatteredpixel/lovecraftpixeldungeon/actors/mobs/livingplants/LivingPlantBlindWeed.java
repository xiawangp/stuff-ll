package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Smoke;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Blindweed;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingBlindweedPlantSprite;

public class LivingPlantBlindWeed extends LivingPlant {

    {
        spriteClass = LivingBlindweedPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Blindweed.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, Smoke.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Blindness.class);
        return super.attackProc(enemy, damage);
    }
}
