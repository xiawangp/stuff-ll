package com.shatteredpixel.lovecraftpixeldungeon.items.shrooms;

import com.shatteredpixel.lovecraftpixeldungeon.actors.Char;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.lovecraftpixeldungeon.effects.Hypno;
import com.shatteredpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.lovecraftpixeldungeon.messages.Messages;
import com.shatteredpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PurpleShroom extends Mushrooms{

    public static final float AMOK_DURATION	= 30f;
    public static final float VERTIGO_DURATION	= 15f;

    {
        image = ItemSpriteSheet.SHROOM_PURPLE;
        energy = Hunger.HUNGRY;
        hornValue = 1;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_EAT)) {

            crazytime(curUser);

            GLog.w(Messages.get(this, "crazy"));
        }
    }

    private void crazytime(Hero hero){
        ArrayList<Char> affected = new ArrayList<>();
        ArrayList<Hypno.Arc> arcs = new ArrayList<>();

        for(int i = hero.visibleEnemies(); i > 0; i--){
            affected.add(hero.visibleEnemy(i));
            arcs.add(new Hypno.Arc(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(new Ballistica(hero.pos, hero.visibleEnemy(i).pos, Ballistica.MAGIC_BOLT).collisionPos)));
        }

        for(int a = affected.size(); a > 0; a--) {
            Char target = affected.get(a - 1);
            Buff.affect( target, Amok.class, AMOK_DURATION );
            Buff.affect( target, Vertigo.class, AMOK_DURATION );
        }

        hero.sprite.parent.addToFront( new Hypno( arcs, null, Random.Float(0.5f, 1.5f) ) );

        Buff.affect( curUser, Vertigo.class, VERTIGO_DURATION );

        hero.decreaseMentalHealth(affected.size()*2);
    }

    @Override
    public int price() {
        return 100 * quantity;
    }
}
