package com.shatteredpixel.lovecraftpixeldungeon.items;

import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class StatueOfPepe extends Item {

    private static final String AC_POST	= "POST";
    private static final String AC_SUMMON= "SUMMON";
    private static boolean kekspawnused = false;

    {
        image = ItemSpriteSheet.ARTIFACT_STATUEPEPE1;
        stackable = true;
        identify();
        weight = 20;
        defaultAction = AC_POST;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_POST );
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if(action.equals(AC_POST)){

        }
    }

    @Override
    public int price() {
        return 77777;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("kekspawnused", kekspawnused);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        kekspawnused = bundle.getBoolean("kekspawnused");
    }

}
