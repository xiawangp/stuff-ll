package com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.lovecraftpixeldungeon.plants.Earthroot;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.LivingEarthrootPlantSprite;

public class LivingPlantEarthRoot extends LivingPlant {

    {
        spriteClass = LivingEarthrootPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Earthroot.Seed(), pos);
        GameScene.add(Blob.seed(pos, 10, ParalyticGas.class));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Barkskin.class);
        Buff.affect(enemy, Roots.class);
        return super.attackProc(enemy, damage);
    }
}
