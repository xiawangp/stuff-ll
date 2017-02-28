package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.WaterWave;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class BlueShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_BLUE;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_EAT)) {

            int amount = Random.Int(1, 3);

            hero.decreaseMentalHealth(amount);

            GameScene.add( Blob.seed( hero.pos, amount*10, WaterWave.class ) );

            GLog.h(Messages.get(this, "waterwave"));
        }
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
