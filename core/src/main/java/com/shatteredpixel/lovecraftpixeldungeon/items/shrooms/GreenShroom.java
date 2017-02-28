package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.items.GreenDewdrop;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GreenShroom extends Mushrooms{
    {
        image = ItemSpriteSheet.SHROOM_GREENWEED;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals( AC_EAT )) {

            int nDrops = Random.NormalIntRange(2, 8);

            hero.decreaseMentalHealth(nDrops);

            ArrayList<Integer> candidates = new ArrayList<Integer>();
            for (int i : PathFinder.NEIGHBOURS8){
                if (Level.passable[hero.pos+i]){
                    candidates.add(hero.pos+i);
                }
            }

            for (int i = 0; i < nDrops && !candidates.isEmpty(); i++){
                Integer c = Random.element(candidates);
                Dungeon.level.drop(new GreenDewdrop(), c).sprite.drop(hero.pos);
                candidates.remove(c);
            }
        }
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
