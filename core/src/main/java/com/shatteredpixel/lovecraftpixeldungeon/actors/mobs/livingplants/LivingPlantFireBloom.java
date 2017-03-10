package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Firebloom;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingFirebloomPlantSprite;

public class LivingPlantFireBloom extends LivingPlant {

    {
        spriteClass = LivingFirebloomPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Firebloom.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, Fire.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Burning.class);
        Buff.affect(enemy, Light.class, 50f);
        return super.attackProc(enemy, damage);
    }
}
