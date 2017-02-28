package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;

public class FlyAgaricShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_FLYAGARIC;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if(action.equals(AC_EAT)){
            Buff.affect(hero, MindVision.class, 25f);
            hero.damage(10, hero);
            hero.decreaseMentalHealth(1);
            GLog.n(Messages.get(this, "vision"));
        }
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
