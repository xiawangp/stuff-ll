package com.shatteredpixel.lovecraftpixeldungeon.items;

import com.shatteredpixel.lovecraftpixeldungeon.Dungeon;
import com.shatteredpixel.lovecraftpixeldungeon.actors.Actor;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.actors.mobs.Facehugger;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Pushing;
import com.shatteredpixel.lovecraftpixeldungeon.levels.Level;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

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
            ArrayList<Integer> spawnPoints = new ArrayList<>();

            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = hero.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
                    spawnPoints.add( p );
                }
            }

            if (spawnPoints.size() > 0) {
                this.detach(hero.belongings.backpack);
                hero.decreaseMentalHealth(Dungeon.depth+1*2);
                hero.damage(Dungeon.depth+1*2, Facehugger.class);
                Facehugger facehugger = new Facehugger();
                facehugger.pos = Random.element( spawnPoints );
                Buff.affect(facehugger, Corruption.class);

                GameScene.add( facehugger );
                Actor.addDelayed( new Pushing( facehugger, hero.pos, facehugger.pos ), -1 );
            } else {
                GLog.n(Messages.get(this, "nottherightplace"));
            }
        }
    }

    //TODO: CHECK IF WORKING

    @Override
    protected void onThrow(int cell) {
        ArrayList<Integer> spawnPoints = new ArrayList<>();

        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = cell + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
                spawnPoints.add( p );
            }
        }

        if (spawnPoints.size() > 0) {
            Facehugger facehugger = new Facehugger();
            facehugger.pos = Random.element( spawnPoints );

            GameScene.add( facehugger );
            Actor.addDelayed( new Pushing( facehugger, cell, facehugger.pos ), -1 );
        }
    }

    @Override
    public int price() {
        return 10000;
    }
}
