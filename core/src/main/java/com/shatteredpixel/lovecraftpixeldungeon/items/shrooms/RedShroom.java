package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

public class RedShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_RED;
        energy = Hunger.HUNGRY;
        hornValue = 1;

        bones = false;
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
