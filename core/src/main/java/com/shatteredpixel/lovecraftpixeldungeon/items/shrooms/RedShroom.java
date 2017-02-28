package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Beam;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RedShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_RED;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_EAT)) {

            int upgrade = Random.Int(hero.STR/2, hero.STR);

            opengate(hero, action, upgrade);

            hero.decreaseMentalHealth(upgrade);

            GLog.h(Messages.get(this, "opengate"));
        }
    }

    private void opengate(Hero hero, String action, int upgrade) {

        ArrayList<Char> affected = new ArrayList<>();

        for(int i = hero.visibleEnemies(); i > 0; i--){
            affected.add(hero.visibleEnemy(i));
        }

        for(int a = affected.size(); a > 0; a--){

            Char target = affected.get(a-1);

            hero.spend( 1f );

            curUser.sprite.parent.add(new Beam.ScottRay(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld( target.pos )));

            target.damage(upgrade*2, curUser);

            hero.decreaseMentalHealth(1);
        }

    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
