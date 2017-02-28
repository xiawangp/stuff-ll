package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Speck;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.typedscroll.randomer.Randomer;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;

public class EggShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_EGG;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if(action.equals(AC_EAT)){
            if(Randomer.randomBoolean()){
                hero.STR++;
                GLog.p(Messages.get(this, "plusstr"));
                hero.sprite.emitter().start( Speck.factory( Speck.UP ), 0.4f, 4 );
            } else {
                hero.STR--;
                GLog.n(Messages.get(this, "minusstr"));
                hero.sprite.emitter().start( Speck.factory( Speck.DOWN ), 0.4f, 4 );
            }
        }
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
