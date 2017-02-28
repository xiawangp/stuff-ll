package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.lovecraftpixeldungeon.actors.blobs.CorruptionGas;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class GreenRedShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_GREENRED;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals( AC_EAT )) {

            int amount = Random.Int(3, 5);

            hero.decreaseMentalHealth(amount*2);

            GameScene.add( Blob.seed( hero.pos, (amount*2)*5, CorruptionGas.class ) );

            Messages.get(this, "corrupt");
        }
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
