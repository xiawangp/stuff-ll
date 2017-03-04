package com.shatteredpixel.lovecraftpixeldungeon.items;

import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class MiGoEgg extends Item {

    private static final String AC_BREAK	= "BREAK";

    {
        image = ItemSpriteSheet.MIGOEGG;
        stackable = false;
        defaultAction = AC_BREAK;
        identify();
        weight = 4;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_BREAK );
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if(action.equals(AC_BREAK)){
            this.detach(hero.belongings.backpack);

        }
    }

    @Override
    public int price() {
        return 10000;
    }
}
